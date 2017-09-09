package com.OtauBanco.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.OtauBanco.models.Bancario;
import com.OtauBanco.models.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {

	Cliente findByNomeLike(String nome);
	
}
