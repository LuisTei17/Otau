package com.OtauBanco.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriComponentsBuilder;

import com.OtauBanco.models.Bancario;
import com.OtauBanco.models.Cliente;
import com.OtauBanco.repository.BancarioRepository;



@Controller    
@RequestMapping(path="/v1/") 
public class BancarioController {
	@Autowired
	private BancarioRepository repoBancario;

    public static final Logger logger = LoggerFactory.getLogger(BancarioController.class);
    
    // Lista bancarios
    // Requisita o mapeamento na url /bancario com o método GET
    @RequestMapping(value = "/bancario/", method = RequestMethod.GET)
    // Cria o método listaTodosBancarios que retorna uma ResponseEntity de uma List de Bancarios
    public ResponseEntity<List<Bancario>> listaTodosBancarios() {
        logger.info("Buscando todos os bancarios {}");
    	List<Bancario> bancarios = repoBancario.findAll();
    	// Verifica se a List de bancarios está vazia
    	if (bancarios.isEmpty()) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<Bancario>>(bancarios, HttpStatus.OK);
    }
    
    // Pega pelo ID
    @RequestMapping(value = "/bancario/{codBancario}", method = RequestMethod.GET)
    public ResponseEntity<?> getBancarioById(@PathVariable("codBancario") Integer codBancario) {
        logger.info("Buscando Bancario com id {}", codBancario);
        Bancario Bancario = repoBancario.findOne(codBancario);
        if (Bancario == null) {
            logger.error("Bancario com codBancario {} nao encontrado.", codBancario);
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Bancario>(Bancario, HttpStatus.OK);
    }

    @RequestMapping(value = "/bancario", method = RequestMethod.GET)
    public ResponseEntity<?> getBancarioBynome(@RequestParam(value="nome") String nome) {
        logger.info("Buscando Bancario com a nome {}", nome);
        Bancario Bancario = repoBancario.findByNomeLike(nome);
        if (Bancario == null) {
            logger.error("Bancario com a nome {} nao encontrado.", nome);
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Bancario>(Bancario, HttpStatus.OK);
    }
    
    
    // -------------------Cadastrar um Bancario-------------------------------------------
 
    @RequestMapping(value = "/bancario/", method = RequestMethod.POST)
    public ResponseEntity<?> cadastrarBancario(@RequestBody Bancario Bancario, UriComponentsBuilder ucBuilder) {
        logger.info("Cadastrando Bancario : {}", Bancario.getNome());
 
        if (repoBancario.findByNomeLike(Bancario.getNome()) != null) {
            logger.error("Nao foi possivel cadastrar. Um Bancario com a nome {} ja existe", Bancario.getNome());
            return new ResponseEntity(HttpStatus.CONFLICT);
        }
        Bancario b = repoBancario.save(Bancario);
 
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/bancario/{codBancario}").buildAndExpand(b.getId()).toUri());
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }
 
    // ------------------- Atualizar um Bancario------------------------------------------------
 
    @RequestMapping(value = "/bancario/{codBancario}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateBancario(@PathVariable("codBancario") Integer codBancario, @RequestBody Bancario Bancario) {
        logger.info("Atualizando o Bancario com codBancario {}", codBancario);
 
        Bancario bancarioAtual = repoBancario.findOne(codBancario);
 
        if (bancarioAtual == null) {
            logger.error("Nao foi possivel atualizar. Bancario com codBancario {} nao encontrado.", codBancario);
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
 
        bancarioAtual.setNome(Bancario.getNome());
 
        repoBancario.save(bancarioAtual);
        return new ResponseEntity<Bancario>(bancarioAtual, HttpStatus.OK);
    }
 
    // ------------------- Excluir um Bancario-----------------------------------------
 
    @RequestMapping(value = "/bancario/{codBancario}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteBancario(@PathVariable("codBancario") Integer codBancario) {
        logger.info("Buscando e deletando Bancario comid {}", codBancario);
 
        Bancario Bancario = repoBancario.findOne(codBancario);
        if (Bancario == null) {
            logger.error("Nao foi possivel excluir. Bancario com codBancario {} nao encontrado.", codBancario);
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        repoBancario.delete(codBancario);
        return new ResponseEntity<Bancario>(HttpStatus.NO_CONTENT);
    }
 
    // ------------------- Excluir todos os bancarios-----------------------------
 
    @RequestMapping(value = "/bancario/", method = RequestMethod.DELETE)
    public ResponseEntity<Bancario> deleteAllBancarios() {
        logger.info("Excluindo todos os Bancarios");
 
        repoBancario.deleteAll();
        return new ResponseEntity<Bancario>(HttpStatus.NO_CONTENT);
    }

}
