package com.testejava.eventos.entities;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import org.springframework.hateoas.RepresentationModel;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "tb_evento")
public class Evento extends RepresentationModel<Evento> implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = ("GMT-03:00"))
	private Instant data;
	
	@NotBlank(message = "Esse campo não pode ser vazio!")
	private String titulo;
	
	@NotBlank(message = "Esse campo não pode ser vazio!")
	private String descricao;
	
	private Boolean cancelado = false;

	@ManyToOne
	@JoinColumn(name = "organizador_id")
	private Usuario organizador;

	@NotEmpty(message = "Esse campo não pode ser vazio!")
	@ManyToMany
	@JoinTable(name = "tb_evento_usuario", joinColumns = @JoinColumn(name = "evento_id"), inverseJoinColumns = @JoinColumn(name = "usuario_id"))
	private Set<Usuario> convidados = new HashSet<>();

	@OneToMany(mappedBy = "evento")
	private Set<Notificacao> notificacao = new HashSet<>();

	public Evento() 
	{
	}

	public Evento(Long id, Instant data, String titulo, String descricao, Usuario organizador) 
	{
		super();
		this.id = id;
		this.data = data;
		this.titulo = titulo;
		this.descricao = descricao;
		this.organizador = organizador;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Instant getData() {
		return data;
	}

	public void setData(Instant data) {
		this.data = data;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Boolean getCancelado() {
		return cancelado;
	}

	public void setCancelado(Boolean cancelado) {
		this.cancelado = cancelado;
	}

	public Usuario getOrganizador() {
		return organizador;
	}

	public void setOrganizador(Usuario organizador) {
		this.organizador = organizador;
	}

	public Set<Usuario> getConvidados() {
		return convidados;
	}

	public Set<Notificacao> getNotificacao() {
		return notificacao;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Evento other = (Evento) obj;
		return Objects.equals(id, other.id);
	}
}
