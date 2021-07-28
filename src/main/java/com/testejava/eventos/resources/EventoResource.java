package com.testejava.eventos.resources;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.net.URI;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.testejava.eventos.entities.Evento;
import com.testejava.eventos.entities.Usuario;
import com.testejava.eventos.services.EventoService;

@RestController
@RequestMapping(value = "/eventos")
public class EventoResource 
{
	@Autowired
	private EventoService service;
	
	@GetMapping
	public ResponseEntity<List<Evento>> findAll()
	{
		List<Evento> list = service.findAll();
		
		list
		.stream()
		.map(x -> x.add(linkTo(methodOn(EventoResource.class).findById(x.getId())).withSelfRel()))
		.collect(Collectors.toList());
		
		Set<Usuario> usuarios = new HashSet<>();
		
		for (Evento evento : list) 
		{
			evento.add(linkTo(methodOn(EventoResource.class).cancelar(evento.getId())).withRel("Cancelar o evento"));
			
			usuarios.add(evento.getOrganizador());
			
			usuarios.addAll(evento.getConvidados()
			.stream()
			.collect(Collectors.toList()));

			evento.getNotificacao()
			.stream()
			.map(x -> x.add(linkTo(methodOn(NotificacaoResource.class).findById(x.getId())).withSelfRel()))
			.collect(Collectors.toList());
		}
		
		usuarios
		.stream()
		.distinct()
		.map(x -> x.add(linkTo(methodOn(UsuarioResource.class).findById(x.getId())).withSelfRel()))
		.collect(Collectors.toList());
		
		return ResponseEntity.ok().body(list);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<Evento> findById(@PathVariable Long id)
	{
		Evento evento = service.findByID(id);
		
		evento.getOrganizador().add(linkTo(methodOn(UsuarioResource.class).findById(evento.getOrganizador().getId())).withSelfRel());
		evento.add(linkTo(methodOn(EventoResource.class).cancelar(id)).withRel("Cancelar o evento"));
		
		evento.getConvidados()
		.stream()
		.filter(x -> x != evento.getOrganizador())
		.map(x -> x.add(linkTo(methodOn(EventoResource.class).findById(x.getId())).withSelfRel()))
		.collect(Collectors.toList());
		
		evento.getNotificacao()
		.stream()
		.map(x -> x.add(linkTo(methodOn(EventoResource.class).findById(x.getId())).withSelfRel()))
		.collect(Collectors.toList());
				
		evento.add(linkTo(methodOn(EventoResource.class).findAll()).withRel("Lista de eventos"));
		return ResponseEntity.ok().body(evento);
	}
	
	@PostMapping()
	public ResponseEntity<Evento> insert(@RequestBody @Valid Evento evento)
	{
		evento = service.insert(evento);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(evento.getId()).toUri();
		return ResponseEntity.created(uri).body(evento);
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id)
	{
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<Evento> update(@PathVariable Long id, @RequestBody Evento evento)
	{
		return ResponseEntity.ok().body(service.update(id, evento));
	}
	
	@PutMapping(value = "/{id}/cancelar")
	public ResponseEntity<Evento> cancelar(@PathVariable Long id)
	{
		return ResponseEntity.ok().body(service.cancelar(id));
	}
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Map<String, String> handleValidataionException(MethodArgumentNotValidException ex)
	{
		Map<String, String> errors = new HashMap<>();
		
		ex.getBindingResult().getAllErrors().forEach((error) ->
			{
				String fieldName = ((FieldError) error).getField();
				String errorMessage = error.getDefaultMessage();
				errors.put(fieldName, errorMessage);
			});
		
		return errors;
	}
}
