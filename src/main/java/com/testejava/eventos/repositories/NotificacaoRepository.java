package com.testejava.eventos.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.testejava.eventos.entities.Notificacao;

public interface NotificacaoRepository extends JpaRepository<Notificacao, Long> 
{
}
