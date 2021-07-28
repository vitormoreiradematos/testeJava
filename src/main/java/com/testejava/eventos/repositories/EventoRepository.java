package com.testejava.eventos.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.testejava.eventos.entities.Evento;

public interface EventoRepository extends JpaRepository<Evento, Long> 
{
}
