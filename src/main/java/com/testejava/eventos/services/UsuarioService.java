package com.testejava.eventos.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.testejava.eventos.entities.Usuario;
import com.testejava.eventos.repositories.UsuarioRepository;
import com.testejava.eventos.services.exceptions.ResourceNotFoundException;

@Service
public class UsuarioService 
{
	@Autowired
	private UsuarioRepository repository;
	
	public List<Usuario> findAll()
	{
		return repository.findAll();
	}
	
	public Usuario findByID(Long id) 
	{
		Optional<Usuario> obj =  repository.findById(id);
		return obj.orElseThrow(() -> new ResourceNotFoundException(id));
	}
	
	public Usuario insert(Usuario usuario) 
	{
		return repository.save(usuario);
	}
	
	public void delete(Long id) 
	{
		repository.deleteById(id);
	}
	
	public Usuario update(Long id, Usuario usuario) 
	{
		Usuario entity = repository.getById(id);
		updateData(entity, usuario);
		return repository.save(entity);
	}

	private void updateData(Usuario entity, Usuario usuario) 
	{
		entity.setNome(usuario.getNome());
		entity.setEmail(usuario.getEmail());
		entity.setTelefone(usuario.getTelefone());
	}
}
