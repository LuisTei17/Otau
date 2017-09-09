package com.OtauBanco.models;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@Table(name="bancario")
public class Bancario implements Serializable{
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="codBancario")
	private Integer id;
	
	@Column(name="nome")
	private String nome;
	
	@JsonIgnore
	@OneToMany(mappedBy="bancario")
	private List<Cliente> clientes;

	public Bancario() {
		// TODO Auto-generated constructor stub
	}
	
	public Bancario(String nome, List<Cliente> clientes) {
		super();
		this.nome = nome;
		this.clientes = clientes;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public List<Cliente> getClientes() {
		return clientes;
	}

	public void setClientes(List<Cliente> clientes) {
		this.clientes = clientes;
	}
		
	public Cliente addCliente(Cliente cliente) {
		getClientes().add(cliente);
		cliente.setBancario(this);

		return cliente;
	}

	public Cliente removeCliente(Cliente cliente) {
		getClientes().remove(cliente);
		cliente.setBancario(null);

		return cliente;
	}

	
	
	
}
