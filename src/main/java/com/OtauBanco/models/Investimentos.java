package com.OtauBanco.models;

import javax.persistence.*;


@Entity
@Table(name="investimentos")
public class Investimentos {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="codInvestimento")
	private Integer id;
	
	@Column(name="titulo")
	private String titulo;
	
	@Column(name="rendimento")
	private Double rendimento;
	
	@ManyToOne
	@JoinColumn(name="codCliente")
	private Cliente cliente;

	public Investimentos() {
		// TODO Auto-generated constructor stub
	}
		
	public Investimentos(String titulo, Double rendimento) {
		super();
		this.titulo = titulo;
		this.rendimento = rendimento;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public Double getRendimento() {
		return rendimento;
	}

	public void setRendimento(Double rendimento) {
		this.rendimento = rendimento;
	}
	
	public Cliente getCliente() {
		return cliente;
	}
	
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

}
