package com.adotaPet.api.service;

import java.net.URI;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.adotaPet.api.domain.Acompanhamento;
import com.adotaPet.api.domain.Adocao;
import com.adotaPet.api.domain.Animal;
import com.adotaPet.api.domain.Pessoa;
import com.adotaPet.api.domain.enums.AcompanhamentoSituacao;
import com.adotaPet.api.domain.enums.AcompanhamentoStatus;
import com.adotaPet.api.domain.enums.AdocaoStatus;
import com.adotaPet.api.domain.enums.Perfil;
import com.adotaPet.api.dto.AcompanhamentoDTO;
import com.adotaPet.api.repository.AcompanhamentoRepository;
import com.adotaPet.api.service.exceptions.AuthorizationException;
import com.adotaPet.api.service.exceptions.DataIntegrityException;
import com.adotaPet.api.service.exceptions.ObjectNotFoundException;


@Service
public class AcompanhamentoService {

	@Autowired
	private AcompanhamentoRepository repo;
	@Autowired
	private AnimalService animalService;
	@Autowired
	private PessoaService pessoaService;
	@Autowired
	private AdocaoService adocaoService;
	@Autowired
	private UploadService uploadService;

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
		if (newObj.getStatus() == AcompanhamentoStatus.FINALIZADO.getCod()) {
			throw new AuthorizationException("Status do acompanhamento não permite alteração.");
		}
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
	
	public List<Acompanhamento> getAcompamhamentosAtendido(){
		Pessoa pessoa = pessoaService.getUserLogged();
		
		if (pessoa.getPerfil() == Perfil.MASTER.getCod()) {
			return repo.getAcompamhamentosAtendido();
		}
		
		List<Adocao> adocoes = adocaoService.getAdocoesPorOng(pessoa.getOng());
		
		List<Acompanhamento> acompanhamentos = new ArrayList<Acompanhamento>();				
			for (Adocao adocao : adocoes) {
				if (adocao.getStatus() == AdocaoStatus.APROVADO.getCod()) {
					for (Acompanhamento acompanhamento : adocao.getAnimal().getAcompanhamentos()) {
						if(acompanhamento.getStatus() == AcompanhamentoStatus.ATENDIDO.getCod()) {
							acompanhamentos.add(acompanhamento);
						}
					}
				}
			}
		
		return acompanhamentos;
	}
	public List<Acompanhamento> getAcompamhamentosSolicitado(){
		
		Pessoa pessoa = pessoaService.getUserLogged();
		List<Adocao> adocoes = adocaoService.getAdocoesPorPessoa(pessoa);
		List<Acompanhamento> acompanhamentos = new ArrayList<Acompanhamento>();
		
		for (Adocao adocao : adocoes) {
			if (adocao.getStatus() == AdocaoStatus.APROVADO.getCod()) {
				for (Acompanhamento acompanhamento : adocao.getAnimal().getAcompanhamentos()) {
					if(acompanhamento.getStatus() == AcompanhamentoStatus.SOLICITADO.getCod() && (acompanhamento.getDescricao()==null) ) {
						acompanhamentos.add(acompanhamento);
					}
				}
			}
		}
		return acompanhamentos;
	}
	public Page<Acompanhamento> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repo.findAll(pageRequest);
	}
	
	public Acompanhamento fromDTO(AcompanhamentoDTO obj,Animal animal) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		Date  dataAgendado = new Date();
		if (obj.getDataAgendado() != null) {
			dataAgendado = obj.getDataAgendado();	
		}else {
			dataAgendado = null;
		}
		Pessoa pessoa = pessoaService.getUserLogged();
		if (pessoa.getPerfil() == Perfil.USUARIO.getCod()) {
			
		}
		return new Acompanhamento(
				obj.getId(),
				animal.getCodigo(),
				obj.getDescricao(),
				AcompanhamentoStatus.toEnum(pessoa.getPerfil() == Perfil.USUARIO.getCod()?5:obj.getStatus()),
				AcompanhamentoSituacao.toEnum(obj.getSituacao()),
				obj.getObservacao(),		
				new Date(),
				dataAgendado);
	}
	public Acompanhamento fromDTO(Adocao adocao) throws ParseException {		
		return new Acompanhamento(
				null,
				adocao.getAnimal().getCodigo(),
				null,
				AcompanhamentoStatus.SOLICITADO,
				AcompanhamentoSituacao.PROCESSOS,
				null,		
				new Date(),
				null);
	}
	
	private void updateData(Acompanhamento newObj, Acompanhamento obj) {	
		newObj.setStatus(obj.getStatus());
		newObj.setSituacao(obj.getSituacao());
		newObj.setDataAgendado(obj.getDataAgendado());
		newObj.setDescricao(obj.getDescricao());
		newObj.setObservacao(obj.getObservacao());
	}
	public void vincularAcompanhamentoAnimal(Acompanhamento acompanhamento,Animal animal) {
		List<Acompanhamento> acompanhamentos = new ArrayList<Acompanhamento>();
		acompanhamentos.add(acompanhamento);
		animalService.adicionarAcompanhamento(animal, acompanhamentos);
	}
	
	public List<Acompanhamento> search(Integer status, Integer animalId) {
		
		Pessoa usuarioLogado = pessoaService.getUserLogged();
		List<Animal> listaAnimais = new ArrayList<Animal>();
		List<Acompanhamento> listaAcompanhamentos = new ArrayList<Acompanhamento>();
		List<Acompanhamento> listaAcompanhamentosRemover = new ArrayList<Acompanhamento>();
		
		if (usuarioLogado.getPerfil() != Perfil.MASTER.getCod()) {
			if (animalId == 0) {
				listaAnimais = animalService.findByOng(usuarioLogado.getOng().getId());
			}
		}
		
		if (usuarioLogado.getPerfil() == Perfil.MASTER.getCod()) {
			if (animalId == 0) {
				listaAnimais = animalService.findAll();
			}
		}
		
		if (animalId != 0) {
			listaAnimais.add(animalService.getAnimal(animalId));
		}
		
		if(!listaAnimais.isEmpty()) {
			for (Animal animal : listaAnimais) {
				List<Acompanhamento> listaAcompanhamentosAnimal = animal.getAcompanhamentos();
				for (Acompanhamento acompanhamentoAnimal : listaAcompanhamentosAnimal) {
					listaAcompanhamentos.add(acompanhamentoAnimal);
					if ((acompanhamentoAnimal.getStatus() != status) &&(status != 0)) {
						listaAcompanhamentosRemover.add(acompanhamentoAnimal);
					}
				}
						
			}
			listaAcompanhamentos.removeAll(listaAcompanhamentosRemover);
		}		
		return listaAcompanhamentos;
	}
	public URI uploadPicture(MultipartFile multipartFile, Integer id) {		
		return uploadService.uploadACompanhamentoFile(multipartFile, id);
	}

}