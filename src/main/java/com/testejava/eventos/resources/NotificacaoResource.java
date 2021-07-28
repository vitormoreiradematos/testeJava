package com.testejava.eventos.resources;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.testejava.eventos.entities.Notificacao;
import com.testejava.eventos.services.NotificacaoService;

@RestController
@RequestMapping(value = "/notificacoes")
public class NotificacaoResource 
{
	@Autowired
	private NotificacaoService service;
	
	@GetMapping
	public ResponseEntity<List<Notificacao>> findAll()
	{
		List<Notificacao> list = service.findAll();
		
		list
		.stream()
		.map(x -> x.add(linkTo(methodOn(NotificacaoResource.class).findById(x.getId())).withSelfRel()))
		.collect(Collectors.toList());
		
		return ResponseEntity.ok().body(list);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<Notificacao> findById(@PathVariable Long id)
	{
		Notificacao notificacao = service.findByID(id);
		notificacao.add(linkTo(methodOn(NotificacaoResource.class).findAll()).withRel("Lista de Notificações"));	
		return ResponseEntity.ok().body(notificacao);
	}
}
