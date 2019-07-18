package com.adotaPet.api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.adotaPet.api.domain.Animal;
import com.adotaPet.api.domain.Doenca;
import com.adotaPet.api.domain.enums.AnimalGenero;
import com.adotaPet.api.domain.enums.AnimalStatus;
import com.adotaPet.api.domain.enums.Porte;
import com.adotaPet.api.dto.AnimalDTO;
import com.adotaPet.api.repository.AnimalRepository;
import com.adotaPet.api.repository.DoencaRepository;
import com.adotaPet.api.service.exceptions.DataIntegrityException;
import com.adotaPet.api.service.exceptions.ObjectNotFoundException;


@Service
public class AnimalService {

	@Autowired
	private AnimalRepository repo;
	@Autowired
	private DoencaRepository doencaRepository;

	public Animal find(Integer id) {
		Optional<Animal> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Animal.class.getName()));
	}
	
	public Animal insert(Animal obj) {
		obj.setId(null);
		return repo.save(obj);
	}
	
	public Animal update(Animal obj) {
		Animal newObj = find(obj.getId());
		updateData(newObj, obj);
		return repo.save(newObj);
	}
	
	public void delete(Integer id) {
		find(id);
		try {
			repo.deleteById(id);
		}
		catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir o animal");
		}
	}
	
	public List<Animal> findAll() {
		return repo.findAll();
	}
	
	public List<Animal> findByOng(Integer ongId) {
		return repo.findOng(ongId);
	}
	
	public Page<Animal> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repo.findAll(pageRequest);
	}
	
	public Animal fromDTO(AnimalDTO objDto) {
		return new Animal(objDto.getId(),objDto.getCodigo(),objDto.getNome(),AnimalGenero.toEnum(objDto.getGenero()),
				Porte.toEnum(objDto.getPorte()),objDto.getVermifugado(),objDto.getCastrado(),objDto.getOng(),		
				AnimalStatus.toEnum(objDto.getStatus()),objDto.getRaca());
	}
	
	public Page<Animal> search(String nome, List<Integer> ids, Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		List<Doenca> doencas = doencaRepository.findAllById(ids);
		return repo.findDistinctByNomeContainingAndDoencasIn(nome, doencas, pageRequest);	
	}
	
	private void updateData(Animal newObj, Animal obj) {		
		newObj.setOng(obj.getOng());		
		newObj.setRaca(obj.getRaca());
		newObj.setNome(obj.getNome());
		newObj.setPorte(obj.getPorte());
		newObj.setCodigo(obj.getCodigo());		
		newObj.setStatus(obj.getStatus());
		newObj.setGenero(obj.getGenero());
		newObj.setCastrado(obj.getCastrado());
		newObj.setVermifugado(obj.getVermifugado());
	}
}