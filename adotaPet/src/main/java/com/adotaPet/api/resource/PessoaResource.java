package com.adotaPet.api.resource;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.adotaPet.api.domain.Pessoa;
import com.adotaPet.api.dto.PessoaDTO;
import com.adotaPet.api.dto.PessoaNewDTO;
import com.adotaPet.api.service.PessoaService;

import javassist.tools.rmi.ObjectNotFoundException;


@RestController
@RequestMapping(value="/pessoas")
public class PessoaResource {
	
	@Autowired
	private PessoaService service;
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<Pessoa> find(@PathVariable Integer id) throws ObjectNotFoundException {
		Pessoa obj = service.find(id);
		return ResponseEntity.ok().body(obj);
	}
	
	//@PreAuthorize("hasAnyRole('ADMIN')")
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<Void> insert(@Valid @RequestBody PessoaNewDTO objDto) {
		Pessoa obj = service.fromDTO(objDto);
		obj = service.insert(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
			.path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	//@PreAuthorize("hasAnyRole('ADMIN')")
	@RequestMapping(value="/{id}", method=RequestMethod.PUT)
	public ResponseEntity<Void> update(@Valid @RequestBody PessoaDTO objDto, @PathVariable Integer id) throws ObjectNotFoundException {
		Pessoa obj = service.fromDTO(objDto);
		obj.setId(id);
		obj = service.update(obj);
		return ResponseEntity.noContent().build();
	}
	
	//@PreAuthorize("hasAnyRole('ADMIN')")
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Integer id) throws ObjectNotFoundException {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<List<PessoaDTO>> findAll() {
		List<Pessoa> list = service.findAll();
		List<PessoaDTO> listDto = list.stream().map(obj -> new PessoaDTO(obj)).collect(Collectors.toList());  
		return ResponseEntity.ok().body(listDto);
	}
	
	
	@RequestMapping(value="/ong/{id}",method=RequestMethod.GET)
	public ResponseEntity<Page<PessoaDTO>> findPage(
			@PathVariable Integer id, 
			@RequestParam(value="page", defaultValue="0") Integer page, 
			@RequestParam(value="linesPerPage", defaultValue="24") Integer linesPerPage, 
			@RequestParam(value="orderBy", defaultValue="nome") String orderBy, 
			@RequestParam(value="direction", defaultValue="ASC") String direction) {
		
		Page<Pessoa> list = service.search(id, page, linesPerPage, orderBy, direction);
		Page<PessoaDTO> listDto = list.map(obj -> new PessoaDTO(obj));  
		return ResponseEntity.ok().body(listDto);
	}
}