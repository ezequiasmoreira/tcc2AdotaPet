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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.adotaPet.api.domain.Adocao;
import com.adotaPet.api.domain.Animal;
import com.adotaPet.api.dto.AdocaoDTO;
import com.adotaPet.api.dto.AnimalDTO;
import com.adotaPet.api.resource.utils.URL;
import com.adotaPet.api.service.AnimalService;

import javassist.tools.rmi.ObjectNotFoundException;


@RestController
@RequestMapping(value="/animais")
public class AnimalResource {
	
	@Autowired
	private AnimalService service;
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<Animal> find(@PathVariable Integer id) throws ObjectNotFoundException {
		Animal obj = service.find(id);
		return ResponseEntity.ok().body(obj);
	}

	@RequestMapping(value="/picture/{id}", method=RequestMethod.POST)
	public ResponseEntity<Void> uploadAnimalPicture(@PathVariable Integer id,@RequestParam(name="file") MultipartFile file) {
		URI uri = service.uploadPicture(file, id);
		return ResponseEntity.created(uri).build();
	}
	
	//@PreAuthorize("hasAnyRole('ADMIN')")
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<Animal> insert(@Valid @RequestBody AnimalDTO objDto) {
		Animal obj = service.fromDTO(objDto);
		obj = service.insert(obj);
		return ResponseEntity.ok().body(obj);
	}
	
	//@PreAuthorize("hasAnyRole('ADMIN')")
	@RequestMapping(value="/{id}", method=RequestMethod.PUT)
	public ResponseEntity<Void> update(@Valid @RequestBody AnimalDTO objDto, @PathVariable Integer id) throws ObjectNotFoundException {
		Animal animal = service.getAnimal(id);
		objDto = service.validaCampos(objDto,animal);
		
		Animal obj = service.fromDTO(objDto);
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
	
	@RequestMapping(value="/ong/{id}",method=RequestMethod.GET)
	public ResponseEntity<List<AnimalDTO>> findAll(@PathVariable Integer id) {
		List<Animal> list = service.findByOng(id);
		List<AnimalDTO> listDto = list.stream().map(obj -> new AnimalDTO(obj)).collect(Collectors.toList());  
		return ResponseEntity.ok().body(listDto);
	}
	
	@RequestMapping(value="doencas", method=RequestMethod.GET)
	public ResponseEntity<Page<AnimalDTO>> findPage(
			@RequestParam(value="nome", defaultValue="") String nome, 
			@RequestParam(value="doencas", defaultValue="") String doencas, 
			@RequestParam(value="page", defaultValue="0") Integer page, 
			@RequestParam(value="linesPerPage", defaultValue="24") Integer linesPerPage, 
			@RequestParam(value="orderBy", defaultValue="nome") String orderBy, 
			@RequestParam(value="direction", defaultValue="ASC") String direction) {
		String nomeDecoded = URL.decodeParam(nome);
		List<Integer> ids = URL.decodeIntList(doencas);
		Page<Animal> list = service.search(nomeDecoded, ids, page, linesPerPage, orderBy, direction);
		Page<AnimalDTO> listDto = list.map(obj -> new AnimalDTO(obj));  
		return ResponseEntity.ok().body(listDto);
	}
	
	@RequestMapping(value="raca/{id}", method=RequestMethod.GET)
	public ResponseEntity<Page<AnimalDTO>> findPage(
			@PathVariable Integer id, 
			@RequestParam(value="page", defaultValue="0") Integer page, 
			@RequestParam(value="linesPerPage", defaultValue="24") Integer linesPerPage, 
			@RequestParam(value="orderBy", defaultValue="nome") String orderBy, 
			@RequestParam(value="direction", defaultValue="ASC") String direction) {		
		Page<Animal> list = service.findRacas(id, page, linesPerPage, orderBy, direction);
		Page<AnimalDTO> listDto = list.map(obj -> new AnimalDTO(obj));  
		return ResponseEntity.ok().body(listDto);
	}
	
	@RequestMapping(value="pesquisar", method=RequestMethod.GET)
	public ResponseEntity<List<AnimalDTO>> findPage(
			@RequestParam(value="nome", defaultValue="") String nome, 
			@RequestParam(value="genero", defaultValue="") String genero, 
			@RequestParam(value="porte", defaultValue="") String porte, 
			@RequestParam(value="castrado", defaultValue="") String castrado,
			@RequestParam(value="estadoId", defaultValue="") String estadoId,
			@RequestParam(value="cidadeId", defaultValue="") String cidadeId,
			@RequestParam(value="racaId", defaultValue="") String racaId) {
		String nomeDecoded =nome;
		Integer generoDecoded = Integer.parseInt(URL.decodeParam(genero));
		Integer porteDecoded = Integer.parseInt(URL.decodeParam(porte));
		Integer castradoDecoded = Integer.parseInt(URL.decodeParam(castrado));
		Integer estadoIdDecoded = Integer.parseInt(URL.decodeParam(estadoId));
		Integer cidadeIdDecoded = Integer.parseInt(URL.decodeParam(cidadeId));
		Integer racaIdDecoded = Integer.parseInt(URL.decodeParam(racaId));
		
		List<Animal> list = service.search(nomeDecoded, generoDecoded,porteDecoded,castradoDecoded,
				estadoIdDecoded, cidadeIdDecoded, racaIdDecoded);
		List<AnimalDTO> listDto = list.stream().map(obj -> new AnimalDTO(obj)).collect(Collectors.toList());  
		return ResponseEntity.ok().body(listDto);
	}
}