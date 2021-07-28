package com.testejava.eventos.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.testejava.eventos.entities.Notificacao;
import com.testejava.eventos.repositories.NotificacaoRepository;

@Service
public class NotificacaoService 
{
	
	@Autowired
	private NotificacaoRepository repository;
	
	public List<Notificacao> findAll()
	{
		return repository.findAll();
	}
	
	public Notificacao findByID(Long id) 
	{
		Optional<Notificacao> obj =  repository.findById(id);
		return obj.get();
	}
	
	public Notificacao insert(Notificacao notificacao) 
	{
		return repository.save(notificacao);
	}
}
