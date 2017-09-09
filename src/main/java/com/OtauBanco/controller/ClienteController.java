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
import com.OtauBanco.repository.ClienteRepository;



@Controller    
@RequestMapping(path="") 
public class ClienteController {
	@Autowired
	private ClienteRepository repoCliente;
	@Autowired
	private BancarioRepository repoBancario;

    public static final Logger logger = LoggerFactory.getLogger(ClienteController.class);
    
    
    // Lista bancarios
    @RequestMapping(value = "/cliente/", method = RequestMethod.GET)
    public ResponseEntity<List<Cliente>> ListaTodosOsClientes() {
        logger.info("Buscando todos os clientes {}");
    	List<Cliente> bancarios = repoCliente.findAll();
        if (bancarios.isEmpty()) {
        	System.out.println("Erro");
        	return new ResponseEntity(HttpStatus.NO_CONTENT);
            // You many decide to return HttpStatus.NOT_FOUND
           
        }
        System.out.println("ok");
        return new ResponseEntity<List<Cliente>>(bancarios, HttpStatus.OK);
    }
    
    // Pega pelo ID
    @RequestMapping(value = "/cliente/{codCliente}", method = RequestMethod.GET)
    public ResponseEntity<?> getClienteById(@PathVariable("codCliente") Integer codCliente) {
        logger.info("Buscando Cliente com id {}", codCliente);
        Cliente Cliente = repoCliente.findOne(codCliente);
        if (Cliente == null) {
            logger.error("Cliente com codCliente {} nao encontrado.", codCliente);
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Cliente>(Cliente, HttpStatus.OK);
    }

    @RequestMapping(value = "/cliente", method = RequestMethod.GET)
    public ResponseEntity<?> getClienteByNome(@RequestParam(value="nome") String nome) {
        logger.info("Buscando Cliente pelo nome {} ", nome);
        Cliente Cliente = repoCliente.findByNomeLike(nome);
        if (Cliente == null) {
            logger.error("Cliente com o nome {} nao encontrado.", nome);
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Cliente>(Cliente, HttpStatus.OK);
    }
    
    
    // -------------------Cadastrar um Cliente-------------------------------------------
 
    @RequestMapping(value = "/cliente/", method = RequestMethod.POST)
    public ResponseEntity<?> cadastrarCliente(@RequestBody Cliente Cliente, UriComponentsBuilder ucBuilder) {
        logger.info("Cadastrando Cliente : {}", Cliente.getNome());
 
        if (repoCliente.findByNomeLike(Cliente.getNome()) != null) {
            logger.error("Nao foi possivel cadastrar. Um Cliente com a nome {} ja existe", Cliente.getNome());
            return new ResponseEntity(HttpStatus.CONFLICT);
        }
  
        Bancario banc = repoBancario.getOne(Cliente.getBancario().getId());
        Cliente.setBancario(banc);
        Cliente c = repoCliente.save(Cliente);
        
        
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/cliente/{codCliente}").buildAndExpand(c.getId()).toUri());
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }
 
    // ------------------- Atualizar um Cliente------------------------------------------------
 
    @RequestMapping(value = "/cliente/{codCliente}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateCliente(@PathVariable("codCliente") Integer codCliente, @RequestBody Cliente Cliente) {
        logger.info("Atualizando o Cliente com codCliente {}", codCliente);
 
        Cliente clienteAtual = repoCliente.findOne(codCliente);
 
        if (clienteAtual == null) {
            logger.error("Nao foi possivel atualizar. Cliente com codCliente {} nao encontrado.", codCliente);
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        
        clienteAtual.setNome(Cliente.getNome());
        clienteAtual.setCep(Cliente.getCep());
        clienteAtual.setCidade(Cliente.getCidade());
        clienteAtual.setSaldo(Cliente.getSaldo());
        Bancario banc = repoBancario.getOne(clienteAtual.getBancario().getId());
        clienteAtual.setBancario(banc);
        
        repoCliente.save(clienteAtual);
        return new ResponseEntity<Cliente>(clienteAtual, HttpStatus.OK);
    }
 
    // ------------------- Excluir um Cliente-----------------------------------------
 
    @RequestMapping(value = "/cliente/{codCliente}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteCliente(@PathVariable("codCliente") Integer codCliente) {
        logger.info("Fetching & Deleting Cliente with id {}", codCliente);
 
        Cliente Cliente = repoCliente.findOne(codCliente);
        if (Cliente == null) {
            logger.error("Nao foi possivel excluir. Cliente com codCliente {} nao encontrado.", codCliente);
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        repoCliente.delete(codCliente);
        return new ResponseEntity<Cliente>(HttpStatus.NO_CONTENT);
    }
 
    // ------------------- Excluir todos os clientes-----------------------------
 
    @RequestMapping(value = "/cliente/", method = RequestMethod.DELETE)
    public ResponseEntity<Cliente> deleteAllClientes() {
        logger.info("Excluindo todos os Clientes");
 
        repoCliente.deleteAll();
        return new ResponseEntity<Cliente>(HttpStatus.NO_CONTENT);
    }

}
