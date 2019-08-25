package com.adotaPet.api.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.adotaPet.api.domain.Adocao;
import com.adotaPet.api.domain.Animal;
import com.adotaPet.api.domain.Pessoa;
import com.adotaPet.api.domain.enums.AdocaoStatus;
import com.adotaPet.api.domain.enums.Perfil;
import com.adotaPet.api.dto.AdocaoDTO;
import com.adotaPet.api.repository.AdocaoRepository;
import com.adotaPet.api.repository.PessoaRepository;
import com.adotaPet.api.security.UserSS;
import com.adotaPet.api.service.exceptions.DataIntegrityException;
import com.adotaPet.api.service.exceptions.ObjectNotFoundException;


@Service
public class AdocaoService {

	@Autowired
	private AdocaoRepository repo;
	@Autowired
	private PessoaService pessoaService;
	@Autowired
	private AnimalService animalService;
	

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
		Pessoa usuarioLogado = pessoaService.getUserLogged();
		return repo.getAdocoes(usuarioLogado.getId());
	}
	public List<Adocao> solicitacaoAdocoes() {
		Pessoa usuarioLogado = pessoaService.getUserLogged();		
		if (usuarioLogado.getPerfil() == Perfil.MASTER.getCod() ) {
			return repo.solicitacaoAdocoes();
		}
		return repo.solicitacaoAdocoes(usuarioLogado.getOng().getId());
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
	public Adocao fromDTO(Integer animalId)  {
	
		Integer codigo = null;
		
		Pessoa usuariologado = pessoaService.getUserLogged();
		Animal animal = animalService.getAnimal(animalId);
		
		if (codigo == null && animal.getOng() != null) {
			codigo = 1;
		}
		
		codigo =  animal.getOng() == null ? null : repo.obterCodigo(animal.getOng().getId());
		return new Adocao(
				null,
				codigo,
				new Date(),
				null,
				AdocaoStatus.AGUARDANDO,
				usuariologado,
				null,
				"Aguardando reposta da ong",
				animal.getOng(),
				animal);
	
	}
	
	private void updateData(Adocao newObj, Adocao obj) {		
		newObj.setStatus(obj.getStatus());
	}
	public List<Adocao> obterAdocaoPorAnimaleStatus(Animal animal,int adocaoStatus) {		
		return repo.obterAdocaoPorAnimaleStatus(animal.getId(),adocaoStatus);
	}
}