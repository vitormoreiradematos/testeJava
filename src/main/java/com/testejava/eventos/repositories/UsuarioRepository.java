package com.testejava.eventos.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.testejava.eventos.entities.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> 
{
}
