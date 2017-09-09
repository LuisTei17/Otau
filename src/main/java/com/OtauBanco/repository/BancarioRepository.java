package com.OtauBanco.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.OtauBanco.models.Bancario;


public interface BancarioRepository extends JpaRepository<Bancario, Integer> {

	Bancario findByNomeLike(String nome);
	
}
