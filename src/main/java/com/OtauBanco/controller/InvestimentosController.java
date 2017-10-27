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

import com.OtauBanco.models.Cliente;
import com.OtauBanco.models.Investimentos;
import com.OtauBanco.repository.ClienteRepository;
import com.OtauBanco.repository.InvestimentosRepository;



	@Controller    
	@RequestMapping(path="/v1/") 
	public class InvestimentosController {
		@Autowired
		private InvestimentosRepository repoInvestimento;
		@Autowired
		private ClienteRepository repoCliente;

	    public static final Logger logger = LoggerFactory.getLogger(InvestimentosController.class);
	    
	    
	    // Lista bancarios
	    @RequestMapping(value = "/investimento/", method = RequestMethod.GET)
	    public ResponseEntity<List<Investimentos>> ListaTodosOsInvestimentos() {
	        logger.info("Buscando todos os investimentos {}");
	    	List<Investimentos> bancarios = repoInvestimento.findAll();
	        if (bancarios.isEmpty()) {
	        	System.out.println("Erro");
	        	return new ResponseEntity(HttpStatus.NO_CONTENT);
	            // You many decide to return HttpStatus.NOT_FOUND
	           
	        }
	        System.out.println("ok");
	        return new ResponseEntity<List<Investimentos>>(bancarios, HttpStatus.OK);
	    }
	    
	    // Pega pelo ID
	    @RequestMapping(value = "/investimento/{codInvestimento}", method = RequestMethod.GET)
	    public ResponseEntity<?> getInvestimentoById(@PathVariable("codInvestimento") Integer codInvestimento) {
	        logger.info("Buscando Investimentos com id {}", codInvestimento);
	        Investimentos Investimentos = repoInvestimento.findOne(codInvestimento);
	        if (Investimentos == null) {
	            logger.error("Investimentos com codInvestimento {} nao encontrado.", codInvestimento);
	            return new ResponseEntity(HttpStatus.NOT_FOUND);
	        }
	        return new ResponseEntity<Investimentos>(Investimentos, HttpStatus.OK);
	    }

		// Pega pelo nome
	    @RequestMapping(value = "/investimento", method = RequestMethod.GET)
	    public ResponseEntity<?> getInvestimentoByTitulo(@RequestParam(value="descricao") String descricao) {
	        logger.info("Buscando Investimentos com a descricao {}", descricao);
	        Investimentos Investimentos = repoInvestimento.findByTituloLike(descricao);
	        if (Investimentos == null) {
	            logger.error("Investimentos com a descricao {} nao encontrado.", descricao);
	            return new ResponseEntity(HttpStatus.NOT_FOUND);
	        }
	        return new ResponseEntity<Investimentos>(Investimentos, HttpStatus.OK);
	    }
	    
	    
	    // -------------------Cadastrar um Investimentos-------------------------------------------
	 
	    @RequestMapping(value = "/investimento/", method = RequestMethod.POST)
	    public ResponseEntity<?> cadastrarInvestimento(@RequestBody Investimentos Investimentos, UriComponentsBuilder ucBuilder) {
	        logger.info("Cadastrando Investimentos : {}", Investimentos.getTitulo());
	 
	        if (repoInvestimento.findByTituloLike(Investimentos.getTitulo()) != null) {
	            logger.error("Nao foi possivel cadastrar. Um Investimentos com a descricao {} ja existe", Investimentos.getTitulo());
	            return new ResponseEntity(HttpStatus.CONFLICT);
	        }
	        
	        Cliente c = repoCliente.getOne(Investimentos.getCliente().getId());
	        Investimentos.setCliente(c);
	        Investimentos b = repoInvestimento.save(Investimentos);
	 
	        HttpHeaders headers = new HttpHeaders();
	        headers.setLocation(ucBuilder.path("/investimento/{codInvestimento}").buildAndExpand(b.getId()).toUri());
	        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
	    }
	 
	    // ------------------- Atualizar um Investimentos------------------------------------------------
	 
	    @RequestMapping(value = "/investimento/{codInvestimento}", method = RequestMethod.PUT)
	    public ResponseEntity<?> updateInvestimento(@PathVariable("codInvestimento") Integer codInvestimento, @RequestBody Investimentos Investimentos) {
	        logger.info("Atualizando o Investimentos com codInvestimento {}", codInvestimento);
	 
	        Investimentos investimentoAtual = repoInvestimento.findOne(codInvestimento);
	 
	        if (investimentoAtual == null) {
	            logger.error("Nao foi possivel atualizar. Investimentos com codInvestimento {} nao encontrado.", codInvestimento);
	            return new ResponseEntity(HttpStatus.NOT_FOUND);
	        }

	       
	        investimentoAtual.setTitulo(Investimentos.getTitulo());
	        investimentoAtual.setRendimento(Investimentos.getRendimento());
	        Cliente c = repoCliente.getOne(investimentoAtual.getCliente().getId());
	        investimentoAtual.setCliente(c);
	        
	        repoInvestimento.save(investimentoAtual);
	        return new ResponseEntity<Investimentos>(investimentoAtual, HttpStatus.OK);
	    }
	 
	    // ------------------- Excluir um Investimento-----------------------------------------
	 
	    @RequestMapping(value = "/investimento/{codInvestimento}", method = RequestMethod.DELETE)
	    public ResponseEntity<?> deleteInvestimento(@PathVariable("codInvestimento") Integer codInvestimento) {
	        logger.info("Fetching & Deleting Investimentos with id {}", codInvestimento);
	 
	        Investimentos Investimentos = repoInvestimento.findOne(codInvestimento);
	        if (Investimentos == null) {
	            logger.error("Nao foi possivel excluir. Investimentos com codInvestimento {} nao encontrado.", codInvestimento);
	            return new ResponseEntity(HttpStatus.NOT_FOUND);
	        }
	        repoInvestimento.delete(codInvestimento);
	        return new ResponseEntity<Investimentos>(HttpStatus.NO_CONTENT);
	    }
	 
	    // ------------------- Excluir todos os investimentos-----------------------------
	 
	    @RequestMapping(value = "/investimento/", method = RequestMethod.DELETE)
	    public ResponseEntity<Investimentos> deleteAllInvestimentos() {
	        logger.info("Excluindo todos os Investimentoss");
	 
	        repoInvestimento.deleteAll();
	        return new ResponseEntity<Investimentos>(HttpStatus.NO_CONTENT);
	    }

	
}
