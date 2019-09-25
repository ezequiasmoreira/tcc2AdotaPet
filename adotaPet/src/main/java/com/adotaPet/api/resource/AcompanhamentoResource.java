package com.adotaPet.api.resource;

import java.net.URI;
import java.text.ParseException;
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

import com.adotaPet.api.domain.Acompanhamento;
import com.adotaPet.api.domain.Animal;
import com.adotaPet.api.dto.AcompanhamentoDTO;
import com.adotaPet.api.service.AcompanhamentoService;
import com.adotaPet.api.service.AnimalService;

import javassist.tools.rmi.ObjectNotFoundException;


@RestController
@RequestMapping(value="/acompanhamentos")
public class AcompanhamentoResource {
	
	@Autowired
	private AcompanhamentoService service;
	@Autowired
	private AnimalService animalService;
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<Acompanhamento> find(@PathVariable Integer id) throws ObjectNotFoundException {
		Acompanhamento obj = service.find(id);
		return ResponseEntity.ok().body(obj);
	}
	
	//@PreAuthorize("hasAnyRole('ADMIN')")
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<Void> insert(@Valid @RequestBody AcompanhamentoDTO objDto) throws ParseException {
		Animal animal = animalService.getAnimal(objDto.getAnimal());
		Acompanhamento obj = service.fromDTO(objDto,animal);
		obj = service.insert(obj);
		service.vincularAcompanhamentoAnimal(obj,animal);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
			.path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	//@PreAuthorize("hasAnyRole('ADMIN')")
	@RequestMapping(value="/{id}", method=RequestMethod.PUT)
	public ResponseEntity<Void> update(@Valid @RequestBody AcompanhamentoDTO objDto, @PathVariable Integer id) throws ObjectNotFoundException, ParseException {
		Animal animal = animalService.getAnimal(objDto.getAnimal());
		Acompanhamento obj = service.fromDTO(objDto,animal);
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
	public ResponseEntity<List<AcompanhamentoDTO>> findAll() {
		List<Acompanhamento> list = service.findAll();
		List<AcompanhamentoDTO> listDto = list.stream().map(obj -> new AcompanhamentoDTO(obj)).collect(Collectors.toList());  
		return ResponseEntity.ok().body(listDto);
	}
	
	@RequestMapping(value="/aberto",method=RequestMethod.GET)
	public ResponseEntity<List<AcompanhamentoDTO>> getAcompamhamentosNaoFinalizado() {
		List<Acompanhamento> list = service.getAcompamhamentosNaoFinalizado();
		List<AcompanhamentoDTO> listDto = list.stream().map(obj -> new AcompanhamentoDTO(obj)).collect(Collectors.toList());  
		return ResponseEntity.ok().body(listDto);
	}
	
	@RequestMapping(value="/page", method=RequestMethod.GET)
	public ResponseEntity<Page<AcompanhamentoDTO>> findPage(
			@RequestParam(value="page", defaultValue="0") Integer page, 
			@RequestParam(value="linesPerPage", defaultValue="24") Integer linesPerPage, 
			@RequestParam(value="orderBy", defaultValue="nome") String orderBy, 
			@RequestParam(value="direction", defaultValue="ASC") String direction) {
		Page<Acompanhamento> list = service.findPage(page, linesPerPage, orderBy, direction);
		Page<AcompanhamentoDTO> listDto = list.map(obj -> new AcompanhamentoDTO(obj));  
		return ResponseEntity.ok().body(listDto);
	}
}