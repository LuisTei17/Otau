package com.OtauBanco.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.OtauBanco.models.Bancario;
import com.OtauBanco.models.Investimentos;

public interface InvestimentosRepository extends JpaRepository<Investimentos, Integer> {
	Investimentos findByTituloLike(String nome);
}