package com.adotaPet.api.service;

import java.util.Optional;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adotaPet.api.domain.Cidade;
import com.adotaPet.api.repository.CidadeRepository;

import com.adotaPet.api.service.exceptions.ObjectNotFoundException;

@Service
public class CidadeService {
	@Autowired
	private CidadeRepository repo;
	
	public Cidade find(Integer id) {
		Optional<Cidade> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
		"Objeto não encontrado! Id: " + id + ", Tipo: " + Cidade.class.getName()));
	}
	
	public List<Cidade> findByEstado(Integer estadoId) {
		return repo.findCidades(estadoId);
	}
	public Cidade getCidade(Integer cidadeId) {
		return repo.findCidadeById(cidadeId);
		
	}

}
