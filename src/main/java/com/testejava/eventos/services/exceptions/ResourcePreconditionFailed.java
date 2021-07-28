package com.testejava.eventos.services.exceptions;

public class ResourcePreconditionFailed extends RuntimeException 
{
	private static final long serialVersionUID = 1L;
	
	public ResourcePreconditionFailed(Object id) 
	{
		super("O usuário não pode alterar eventos passados, cancelados ou que estejam a menos de 48hrs de ocorrerem. ID: " + id);
	}
}
