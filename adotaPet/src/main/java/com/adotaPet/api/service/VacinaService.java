package com.adotaPet.api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.adotaPet.api.domain.Vacina;
import com.adotaPet.api.domain.enums.Especie;
import com.adotaPet.api.dto.VacinaDTO;
import com.adotaPet.api.repository.VacinaRepository;
import com.adotaPet.api.service.exceptions.DataIntegrityException;
import com.adotaPet.api.service.exceptions.ObjectNotFoundException;


@Service
public class VacinaService {

	@Autowired
	private VacinaRepository repo;

	public Vacina find(Integer id) {
		Optional<Vacina> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Vacina.class.getName()));
	}
	
	public Vacina insert(Vacina obj) {
		obj.setId(null);
		return repo.save(obj);
	}
	
	public Vacina update(Vacina obj) {
		Vacina newObj = find(obj.getId());
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
	
	public List<Vacina> findAll() {
		return repo.findAll();
	}
	
	public Page<Vacina> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repo.findAll(pageRequest);
	}
	
	public Vacina fromDTO(VacinaDTO objDto) {
		return new Vacina(
                objDto.getId(), 
                objDto.getNome(),
				objDto.getFaixaIdade(),
                objDto.getPrevencao(),
                Especie.toEnum(objDto.getEspecie()));
	}
	
	private void updateData(Vacina newObj, Vacina obj) {
		newObj.setNome(obj.getNome());
		newObj.setFaixaIdade(obj.getFaixaIdade());
		newObj.setPrevencao(obj.getPrevencao());
		newObj.setEspecie(obj.getEspecie());
	}
}