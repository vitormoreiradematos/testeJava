package com.testejava.eventos.resources.exceptions;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.testejava.eventos.services.exceptions.ResourceNotFoundException;
import com.testejava.eventos.services.exceptions.ResourcePreconditionFailed;

@ControllerAdvice
public class ResourceExceptionHandler 
{
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<StandardError> ResourceNotFound(ResourceNotFoundException e, HttpServletRequest request)
	{
		String error = "Resource not found";
		HttpStatus status = HttpStatus.NOT_FOUND;
		StandardError standardError = new StandardError(new Date(), status.value(), error, e.getMessage(), request.getRequestURI());
		return ResponseEntity.status(status).body(standardError);
	}
	
	@ExceptionHandler(ResourcePreconditionFailed.class)
	public ResponseEntity<StandardError> ResourceNotPreconditionFailed(ResourcePreconditionFailed e, HttpServletRequest request)
	{
		String error = "Precondition Failed";
		HttpStatus status = HttpStatus.PRECONDITION_FAILED;
		StandardError standardError = new StandardError(new Date(), status.value(), error, e.getMessage(), request.getRequestURI());
		return ResponseEntity.status(status).body(standardError);
	}
}
