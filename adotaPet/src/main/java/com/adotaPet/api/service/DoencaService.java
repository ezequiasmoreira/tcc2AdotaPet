package com.adotaPet.api.service;

import java.util.List;
import java.util.Optional;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.adotaPet.api.domain.Doenca;
import com.adotaPet.api.domain.Animal;
import com.adotaPet.api.dto.DoencaDTO;
import com.adotaPet.api.repository.DoencaRepository;
import com.adotaPet.api.service.exceptions.DataIntegrityException;
import com.adotaPet.api.service.exceptions.ObjectNotFoundException;


@Service
public class DoencaService {

	@Autowired
	private DoencaRepository repo;

	@Autowired
	private AnimalService animalService;

	public Doenca find(Integer id) {
		Optional<Doenca> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Doenca.class.getName()));
	}
	
	public Doenca insert(Doenca obj) {
		obj.setId(null);
		return repo.save(obj);
	}
	
	public Doenca update(Doenca obj) {
		Doenca newObj = find(obj.getId());
		updateData(newObj, obj);
		return repo.save(newObj);
	}
	
	public void delete(Integer id) {
		find(id);
		try {
			repo.deleteById(id);
		}
		catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir a doença");
		}
	}
	
	public List<Doenca> findAll() {
		return repo.findAll();
	}
	
	public Page<Doenca> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repo.findAll(pageRequest);
	}
	
	public Doenca fromDTO(DoencaDTO objDto) {
		return new Doenca(
				objDto.getId(), 
				objDto.getCodigo(),
				objDto.getDescricao()
				);
	}
	
	private void updateData(Doenca newObj, Doenca obj) {
		newObj.setCodigo(obj.getCodigo());
		newObj.setDescricao(obj.getDescricao());
	}

	public void vincularDoencaAnimal(Doenca doenca,Animal animal) {
		List<Doenca> doencas = new ArrayList<Doenca>();
		doencas.add(doenca);
		animalService.adicionarDoenca(animal, doencas);
	}
}