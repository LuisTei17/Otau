package com.OtauBanco.models;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;


/**
 * The persistent class for the cliente database table.
 * 
 */
@Entity
@Table(name="cliente")
public class Cliente {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="codCliente")
	private Integer id;

	@Column(name="Cep")
	private String cep;

	@Column(name="Cidade")
	private String cidade;

	@Column(name="Nome")
	private String nome;

	@Column(name="Saldo")
	private double saldo;
	
	@JsonIgnore
	@OneToMany(mappedBy="cliente")
	private List<Investimentos> investimentos;
	
	@ManyToOne
	@JoinColumn(name="codBancario")
	private Bancario bancario;

	public Cliente() {
	}
	
	public Cliente(String cep, String cidade, String nome, double saldo, List<Investimentos> investimentos) {
		super();
		this.cep = cep;
		this.cidade = cidade;
		this.nome = nome;
		this.saldo = saldo;
		this.investimentos = investimentos;
	}



	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCep() {
		return this.cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getCidade() {
		return this.cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public double getSaldo() {
		return saldo;
	}
	public void setSaldo(double saldo) {
		this.saldo = saldo;
	}
	

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public List<Investimentos> getInvestimentos() {
		return investimentos;
	}
	
	public void setInvestimentos(List<Investimentos> investimentos) {
		this.investimentos = investimentos;
	}
	
	public Bancario getBancario() {
		return bancario;
	}
	
	public void setBancario(Bancario bancario) {
		this.bancario = bancario;
	}
	
	public Investimentos addInvestimento(Investimentos investimento) {
		getInvestimentos().add(investimento);
		investimento.setCliente(this);

		return investimento;
	}

	public Investimentos removeInvestimento(Investimentos investimentos) {
		getInvestimentos().remove(investimentos);
		investimentos.setCliente(null);

		return investimentos;
	}


}
