package com.adotaPet.api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.adotaPet.api.domain.Adocao;
import com.adotaPet.api.domain.enums.AdocaoStatus;
import com.adotaPet.api.dto.AdocaoDTO;
import com.adotaPet.api.repository.AdocaoRepository;
import com.adotaPet.api.service.exceptions.DataIntegrityException;
import com.adotaPet.api.service.exceptions.ObjectNotFoundException;


@Service
public class AdocaoService {

	@Autowired
	private AdocaoRepository repo;

	public Adocao find(Integer id) {
		Optional<Adocao> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Adocao.class.getName()));
	}
	
	public Adocao insert(Adocao obj) {
		obj.setId(null);
		return repo.save(obj);
	}
	
	public Adocao update(Adocao obj) {
		Adocao newObj = find(obj.getId());
		updateData(newObj, obj);
		return repo.save(newObj);
	}
	
	public void delete(Integer id) {
		find(id);
		try {
			repo.deleteById(id);
		}
		catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir o Adocao");
		}
	}
	
	public List<Adocao> findAll() {
		return repo.findAll();
	}
	
	public Page<Adocao> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repo.findAll(pageRequest);
	}
	
	public Adocao fromDTO(AdocaoDTO obj) {
		return new Adocao(
				obj.getId(),
				obj.getCodigo(),
				obj.getDataCadastro(),
				obj.getDataFinalizacao(),
				AdocaoStatus.toEnum(obj.getStatus()),
				obj.getPessoaInterressado(),
				obj.getPessoaIntermediador(),
				obj.getObservacao(),
				obj.getOng(),
				obj.getAnimal());
	}
	
	private void updateData(Adocao newObj, Adocao obj) {		
		newObj.setOng(obj.getOng());
	}
}