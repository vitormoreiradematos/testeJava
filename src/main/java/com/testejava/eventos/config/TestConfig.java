package com.testejava.eventos.config;

import java.time.Instant;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.testejava.eventos.entities.Evento;
import com.testejava.eventos.entities.Notificacao;
import com.testejava.eventos.entities.Usuario;
import com.testejava.eventos.repositories.EventoRepository;
import com.testejava.eventos.repositories.NotificacaoRepository;
import com.testejava.eventos.repositories.UsuarioRepository;

@Configuration
@Profile("test")
public class TestConfig implements CommandLineRunner {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	@Autowired
	private EventoRepository eventoRepository;
	@Autowired
	private NotificacaoRepository notificacaoRepository;

	@Override
	public void run(String... args) throws Exception 
	{
				
		Usuario user1 = new Usuario(null, "Vitor", "vitor@gmail.com", "(16) 99600-5011");		
		Usuario user2 = new Usuario(null, "Joao", "joao@gmail.com", "(16) 99127-5011");
		Usuario user3 = new Usuario(null, "Ana", "ana@gmail.com", "(16) 99600-7070");	
		usuarioRepository.saveAll((Arrays.asList(user1, user2, user3)));
		
		Evento evento1 = new Evento(null, Instant.parse("2021-07-29T14:30:00-03:00"), "Titulo Evento 1", "Descricao Evento 1", user1);
		Evento evento2 = new Evento(null, Instant.parse("2021-07-31T15:30:00-03:00"), "Titulo Evento 2", "Descricao Evento 2", user3);
		
		evento1.getConvidados().add(user1);
		evento1.getConvidados().add(user3);
		evento1.getConvidados().add(user2);
		evento2.getConvidados().add(user3);
		evento2.getConvidados().add(user2);
		
		eventoRepository.saveAll(Arrays.asList(evento1, evento2));
		
		Notificacao notificacao1 = new Notificacao(null, Instant.parse("2021-07-29T14:30:00-03:00"), "Email", "Evento criado", evento1);
		Notificacao notificacao2 = new Notificacao(null, Instant.parse("2021-07-31T15:30:00-03:00"), "SMS", "Evento criado", evento2);
		notificacaoRepository.saveAll(Arrays.asList(notificacao1, notificacao2));		
	}
}
