package com.adotaPet.api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.adotaPet.api.domain.Raca;
import com.adotaPet.api.domain.enums.Especie;
import com.adotaPet.api.dto.RacaDTO;
import com.adotaPet.api.repository.RacaRepository;
import com.adotaPet.api.service.exceptions.DataIntegrityException;
import com.adotaPet.api.service.exceptions.ObjectNotFoundException;


@Service
public class RacaService {

	@Autowired
	private RacaRepository repo;

	public Raca find(Integer id) {
		Optional<Raca> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Raca.class.getName()));
	}
	
	public Raca insert(Raca obj) {
		obj.setId(null);
		return repo.save(obj);
	}
	
	public Raca update(Raca obj) {
		Raca newObj = find(obj.getId());
		updateData(newObj, obj);
		return repo.save(newObj);
	}
	
	public void delete(Integer id) {
		find(id);
		try {
			repo.deleteById(id);
		}
		catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir a raça");
		}
	}
	
	public List<Raca> findAll() {
		return repo.findAll();
	}
	
	public Page<Raca> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repo.findAll(pageRequest);
	}
	
	public Raca fromDTO(RacaDTO objDto) {
		return new Raca(
				objDto.getId(), 
				objDto.getCodigo(),
				objDto.getDescricao(),
				Especie.toEnum(objDto.getEspecie())
				);
	}
	
	private void updateData(Raca newObj, Raca obj) {
		newObj.setCodigo(obj.getCodigo());
		newObj.setDescricao(obj.getDescricao());
		newObj.setEspecie(obj.getEspecie());		
	}
}