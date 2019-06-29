package com.adotaPet.api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.adotaPet.api.domain.Ong;
import com.adotaPet.api.dto.OngDTO;
import com.adotaPet.api.repository.OngRepository;
import com.adotaPet.api.service.exceptions.DataIntegrityException;
import com.adotaPet.api.service.exceptions.ObjectNotFoundException;


@Service
public class OngService {

	@Autowired
	private OngRepository repo;

	public Ong find(Integer id) {
		Optional<Ong> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Ong.class.getName()));
	}
	
	public Ong insert(Ong obj) {
		obj.setId(null);
		return repo.save(obj);
	}
	
	public Ong update(Ong obj) {
		Ong newObj = find(obj.getId());
		updateData(newObj, obj);
		return repo.save(newObj);
	}
	
	public void delete(Integer id) {
		find(id);
		try {
			repo.deleteById(id);
		}
		catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir uma categoria que possui produtos");
		}
	}
	
	public List<Ong> findAll() {
		return repo.findAll();
	}
	
	public Page<Ong> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repo.findAll(pageRequest);
	}
	
	public Ong fromDTO(OngDTO objDto) {
		return new Ong(
				objDto.getId(), 
				objDto.getCodigo(),
				objDto.getRazao_Social(),
				objDto.getNome_Fantasia(),
				objDto.getCnpj(),
				objDto.getRua(),
				objDto.getNumero(),
				objDto.getBairro()
				);
	}
	
	private void updateData(Ong newObj, Ong obj) {
		newObj.setCodigo(obj.getCodigo());
		newObj.setCnpj(obj.getCnpj());
	}
}