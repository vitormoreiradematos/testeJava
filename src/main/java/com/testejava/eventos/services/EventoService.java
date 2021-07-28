package com.testejava.eventos.services;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.testejava.eventos.entities.Evento;
import com.testejava.eventos.entities.Notificacao;
import com.testejava.eventos.entities.Usuario;
import com.testejava.eventos.repositories.EventoRepository;
import com.testejava.eventos.services.exceptions.ResourceNotFoundException;
import com.testejava.eventos.services.exceptions.ResourcePreconditionFailed;

@Service
public class EventoService 
{

	@Autowired
	private EventoRepository repository;
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private NotificacaoService notificacaoService;

	public List<Evento> findAll() 
	{
		return repository.findAll();
	}

	public Evento findByID(Long id) 
	{
		Optional<Evento> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ResourceNotFoundException(id));
	}

	public Evento insert(Evento evento) 
	{
		Set<Usuario> convidados = new HashSet<>();
		
		if (evento.getOrganizador().getId() == null)
			usuarioService.insert(evento.getOrganizador());
		else
			evento.setOrganizador(usuarioService.findByID(evento.getOrganizador().getId())); 
		
		for (Usuario convidado : evento.getConvidados()) 
		{
			if (convidado.getId() == null) 
				usuarioService.insert(convidado);	
			else
				convidado = usuarioService.findByID(convidado.getId());		
			
			convidados.add(convidado);
		}
		
		evento.getConvidados().clear();
		evento.getConvidados().addAll(convidados);
		
		Evento eventoResponse = repository.save(evento);
		eventoResponse.getNotificacao().add(notificacaoService.insert(new Notificacao(null, Instant.now(), "Email", "Evento criado", eventoResponse)));
		
		return eventoResponse;
	}

	public void delete(Long id) 
	{
		repository.deleteById(id);
	}
	
	public Evento update(Long id, Evento evento) 
	{
		Optional<Evento> obj = repository.findById(id);
		Evento entity = obj.orElseThrow(() -> new ResourceNotFoundException(id));
		
		if (getDiffTwoDatesInHoursAndMinutes(Instant.now(), entity.getData()) > 48)
		{
			updateData(entity, evento);
			Evento eventoResponse = repository.save(entity);
			notificacaoService.insert(new Notificacao(null, Instant.now(), "Email", "Evento alterado", eventoResponse));
			return eventoResponse;
		} 
		else 
		{
			throw new ResourcePreconditionFailed(id);
		}
	}

	private void updateData(Evento entity, Evento evento) 
	{
		entity.setData(evento.getData());
		entity.setTitulo(evento.getTitulo());
		entity.setDescricao(evento.getDescricao());
		entity.setOrganizador(evento.getOrganizador());
		entity.getConvidados().addAll(evento.getConvidados());
	}
	
	public Evento cancelar(Long id) 
	{
		Optional<Evento> obj = repository.findById(id);
		Evento entity = obj.orElseThrow(() -> new ResourceNotFoundException(id));

		if (getDiffTwoDatesInHoursAndMinutes(Instant.now(), entity.getData()) > 48 && entity.getCancelado() == false)
		{
			entity.setCancelado(true);
			Evento eventoResponse = repository.save(entity);
			
			notificacaoService.insert(new Notificacao(null, Instant.now(), "Email", "Evento cancelado", eventoResponse));
			return eventoResponse;
		} 
		else 
		{
			throw new ResourcePreconditionFailed(id);
		}
	}
	
	private double getDiffTwoDatesInHoursAndMinutes(Instant now, Instant data) 
	{
		double horas = ChronoUnit.MINUTES.between(now, data);
		return horas /= 60;
	}
}
