package com.adotaPet.api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adotaPet.api.domain.Estado;
import com.adotaPet.api.repository.EstadoRepository;

import com.adotaPet.api.service.exceptions.ObjectNotFoundException;

@Service
public class EstadoService {
	@Autowired
	private EstadoRepository repo;
	
	public Estado find(Integer id) {
		Optional<Estado> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
		"Objeto não encontrado! Id: " + id + ", Tipo: " + Estado.class.getName()));
	}

	public List<Estado> findAll() {
		return repo.findAll();
	}
}
