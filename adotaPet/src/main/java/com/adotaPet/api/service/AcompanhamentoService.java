package com.adotaPet.api.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.adotaPet.api.domain.Acompanhamento;
import com.adotaPet.api.domain.Animal;
import com.adotaPet.api.domain.Ong;
import com.adotaPet.api.domain.enums.AcompanhamentoSituacao;
import com.adotaPet.api.domain.enums.AcompanhamentoStatus;
import com.adotaPet.api.dto.AcompanhamentoDTO;
import com.adotaPet.api.repository.AcompanhamentoRepository;
import com.adotaPet.api.service.exceptions.DataIntegrityException;
import com.adotaPet.api.service.exceptions.ObjectNotFoundException;


@Service
public class AcompanhamentoService {

	@Autowired
	private AcompanhamentoRepository repo;
	@Autowired
	private AnimalService animalService;

	public Acompanhamento find(Integer id) {
		Optional<Acompanhamento> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Acompanhamento.class.getName()));
	}
	
	public Acompanhamento insert(Acompanhamento obj) {
		obj.setId(null);
		return repo.save(obj);
	}
	
	public Acompanhamento update(Acompanhamento obj) {
		Acompanhamento newObj = find(obj.getId());
		updateData(newObj, obj);
		return repo.save(newObj);
	}
	
	public void delete(Integer id) {
		find(id);
		try {
			repo.deleteById(id);
		}
		catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir o Acompanhamento");
		}
	}
	
	public List<Acompanhamento> findAll() {
		return repo.findAll();
	}
	public List<Acompanhamento> getAcompamhamentosNaoFinalizado(){
		return repo.getAcompamhamentosNaoFinalizado();
	}
	public Page<Acompanhamento> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repo.findAll(pageRequest);
	}
	
	public Acompanhamento fromDTO(AcompanhamentoDTO obj,Animal animal) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		Date  dataAgendado = sdf.parse(obj.getDataAgendado().replaceAll("-", "/"));		
		return new Acompanhamento(
				obj.getId(),
				animal.getCodigo(),
				obj.getDescricao(),
				AcompanhamentoStatus.toEnum(obj.getStatus()),
				AcompanhamentoSituacao.toEnum(obj.getSituacao()),
				obj.getObservacao(),		
				new Date(),
				dataAgendado);
	}
	
	private void updateData(Acompanhamento newObj, Acompanhamento obj) {		
		newObj.setCodigo(obj.getCodigo());
	}
	public void vincularAcompanhamentoAnimal(Acompanhamento acompanhamento,Animal animal) {
		List<Acompanhamento> acompanhamentos = new ArrayList<Acompanhamento>();
		acompanhamentos.add(acompanhamento);
		animalService.adicionarAcompanhamento(animal, acompanhamentos);
	}
}