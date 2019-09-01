package com.adotaPet.api.resource;

import java.net.URI;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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

import com.adotaPet.api.domain.Adocao;
import com.adotaPet.api.domain.Animal;
import com.adotaPet.api.dto.AdocaoDTO;
import com.adotaPet.api.dto.AnimalDTO;
import com.adotaPet.api.resource.utils.URL;
import com.adotaPet.api.service.AdocaoService;
import com.adotaPet.api.service.AnimalService;

import javassist.tools.rmi.ObjectNotFoundException;


@RestController
@RequestMapping(value="/adocoes")
public class AdocaoResource {
	
	@Autowired
	private AdocaoService service;
	@Autowired
	private AnimalService animalService;
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<Adocao> find(@PathVariable Integer id) throws ObjectNotFoundException {
		Adocao obj = service.find(id);
		return ResponseEntity.ok().body(obj);
	}
	
	//@PreAuthorize("hasAnyRole('ADMIN')")
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<Void> insert(@Valid @RequestBody AdocaoDTO objDto) {
		Adocao obj = service.fromDTO(objDto);
		obj = service.insert(obj);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
			.path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	@RequestMapping(value="/{id}",method=RequestMethod.POST)
	public ResponseEntity<Void> insert(@PathVariable Integer id) {
		Adocao obj = service.fromDTO(id);
		obj = service.insert(obj);
		animalService.atualizaStatus(obj.getAnimal());
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
			.path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	//@PreAuthorize("hasAnyRole('ADMIN')")
	@RequestMapping(value="/{id}", method=RequestMethod.PUT)
	public ResponseEntity<Void> update(@Valid @RequestBody AdocaoDTO objDto, @PathVariable Integer id) throws ObjectNotFoundException {
		Adocao obj = service.fromDTO(objDto);
		obj.setId(id);
		obj = service.update(obj);		
		service.realizarProcessosObrigatorios(obj);		
		return ResponseEntity.noContent().build();
	}
	
	//@PreAuthorize("hasAnyRole('ADMIN')")
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Integer id) throws ObjectNotFoundException {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
	//@PreAuthorize("hasAnyRole('ADMIN')")
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<List<AdocaoDTO>> findAll() {
		List<Adocao> list = service.findAll();
		List<AdocaoDTO> listDto = list.stream().map(obj -> new AdocaoDTO(obj)).collect(Collectors.toList());  
		return ResponseEntity.ok().body(listDto);
	}
	//@PreAuthorize("hasAnyRole('ADMIN')")
	@RequestMapping(value="/solicitacao",method=RequestMethod.GET)
	public ResponseEntity<List<AdocaoDTO>> solicitacaoAdocoes() {
		List<Adocao> list = service.solicitacaoAdocoes();
		List<AdocaoDTO> listDto = list.stream().map(obj -> new AdocaoDTO(obj)).collect(Collectors.toList());  
		return ResponseEntity.ok().body(listDto);
	}
	
	@RequestMapping(value="/page", method=RequestMethod.GET)
	public ResponseEntity<Page<AdocaoDTO>> findPage(
			@RequestParam(value="page", defaultValue="0") Integer page, 
			@RequestParam(value="linesPerPage", defaultValue="24") Integer linesPerPage, 
			@RequestParam(value="orderBy", defaultValue="nome") String orderBy, 
			@RequestParam(value="direction", defaultValue="ASC") String direction) {
		Page<Adocao> list = service.findPage(page, linesPerPage, orderBy, direction);
		Page<AdocaoDTO> listDto = list.map(obj -> new AdocaoDTO(obj));  
		return ResponseEntity.ok().body(listDto);
	}
	@RequestMapping(value="pesquisar", method=RequestMethod.GET)
	public ResponseEntity<List<AdocaoDTO>> findPage(
			@RequestParam(value="codigo", defaultValue="") String codigo, 
			@RequestParam(value="status", defaultValue="") String status, 
			@RequestParam(value="periodoInical", defaultValue="") String periodoInical, 
			@RequestParam(value="periodoFinal", defaultValue="") String periodoFinal, 
			@RequestParam(value="page", defaultValue="0") Integer page, 
			@RequestParam(value="linesPerPage", defaultValue="24") Integer linesPerPage, 
			@RequestParam(value="orderBy", defaultValue="nome") String orderBy, 
			@RequestParam(value="direction", defaultValue="ASC") String direction) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		Integer codigoDecoded = Integer.parseInt(URL.decodeParam(codigo));
		Integer  statusDecoded = Integer.parseInt(URL.decodeParam(status));
		Date  dataInicialDecoded = sdf.parse(URL.decodeParam(periodoInical).replaceAll("-", "/"));
		Date  dataFinalDecoded = sdf.parse(URL.decodeParam(periodoFinal).replaceAll("-", "/"));
		
		List<Adocao> list = service.search(codigoDecoded, statusDecoded,dataInicialDecoded,dataFinalDecoded, page, linesPerPage, orderBy, direction);
		List<AdocaoDTO> listDto = list.stream().map(obj -> new AdocaoDTO(obj)).collect(Collectors.toList());  
		return ResponseEntity.ok().body(listDto);
	}
}