package com.testejava.eventos.entities;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import org.springframework.hateoas.RepresentationModel;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "tb_notificacao")
public class Notificacao extends RepresentationModel<Notificacao> implements Serializable 
{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = ("GMT-03:00"))
	private Instant data;
	@NotBlank(message = "Esse campo não pode ser vazio!")
	private String tipo;
	@NotBlank(message = "Esse campo não pode ser vazio!")
	private String descricao;
	
	@ManyToOne
	@JoinColumn(name = "notificacao_id")
	private Evento evento = new Evento();
	
	public Notificacao() 
	{
	}
	
	public Notificacao(Long id, Instant data, String tipo, String descricao, Evento evento) 
	{
		super();
		this.id = id;
		this.data = data;
		this.tipo = tipo;
		this.descricao = descricao;
		this.evento = evento;
	}

	public Long getId() 
	{
		return id;
	}

	public void setId(Long id) 
	{
		this.id = id;
	}

	public Instant getData() 
	{
		return data;
	}

	public void setData(Instant data) 
	{
		this.data = data;
	}

	public String getTipo() 
	{
		return tipo;
	}

	public void setTipo(String tipo) 
	{
		this.tipo = tipo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	@Override
	public int hashCode() 
	{
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(id);
		return result;
	}

	@Override
	public boolean equals(Object obj) 
	{
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Notificacao other = (Notificacao) obj;
		return Objects.equals(id, other.id);
	}
}
