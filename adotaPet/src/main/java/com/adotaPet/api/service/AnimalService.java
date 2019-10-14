package com.adotaPet.api.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.net.URI;

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
import com.adotaPet.api.domain.Cidade;
import com.adotaPet.api.domain.Doenca;
import com.adotaPet.api.domain.Ong;
import com.adotaPet.api.domain.Pessoa;
import com.adotaPet.api.domain.Raca;
import com.adotaPet.api.domain.Vacina;
import com.adotaPet.api.domain.VacinaItem;
import com.adotaPet.api.domain.enums.AdocaoStatus;
import com.adotaPet.api.domain.enums.AnimalGenero;
import com.adotaPet.api.domain.enums.AnimalStatus;
import com.adotaPet.api.domain.enums.Perfil;
import com.adotaPet.api.domain.enums.Porte;
import com.adotaPet.api.dto.AnimalDTO;
import com.adotaPet.api.repository.AnimalRepository;
import com.adotaPet.api.repository.DoencaRepository;
import com.adotaPet.api.service.exceptions.DataIntegrityException;
import com.adotaPet.api.service.exceptions.ObjectNotFoundException;


@Service
public class AnimalService {

	@Autowired
	private AnimalRepository repo;
	@Autowired
	private DoencaRepository doencaRepository;

	@Autowired
	private RacaService racaService;
	@Autowired
	private AdocaoService adocaoService;
	@Autowired
	private PessoaService pessoaService;
	@Autowired
	private UploadService uploadService;

	public Animal find(Integer id) {
		Optional<Animal> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Animal.class.getName()));
	}
	public Animal getAnimal(Integer animalId) {		
		return repo.findAnimalId(animalId);
	}
	public Animal insert(Animal obj) {
		obj.setId(null);
		return repo.save(obj);
	}
	
	public Animal update(Animal obj) {		
		Animal newObj = find(obj.getId());
		updateData(newObj,obj);
		return repo.save(newObj);
	}
	
	public void delete(Integer id) {
		find(id);
		try {
			repo.deleteById(id);
		}
		catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir o animal");
		}
	}
	
	public List<Animal> findAll() {
		return repo.findAll();
	}
	
	public List<Animal> findByOng(Integer ongId) {
		Pessoa  pessoa = pessoaService.getUserLogged();
		if (pessoa.getPerfil() != Perfil.MASTER.getCod()) {
			ongId = pessoa.getOng().getId();
			return repo.findOng(ongId);
		}
		return findAll();
	}
	
	public Page<Animal> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repo.findAll(pageRequest);
	}
	
	public Animal fromDTO(AnimalDTO objDto) {
		Raca raca = racaService.findRacaId(objDto.getRacaId());
		Pessoa  pessoa = pessoaService.getUserLogged();
		if (pessoa.getPerfil() != Perfil.MASTER.getCod()) {
			objDto.setOngId(pessoa.getOng().getId());			
		}else {
			objDto.setOngId(1);
		}
				
		Integer codigo = objDto.getOngId() == null ? null : repo.obterCodigo(objDto.getOngId());
		if ((objDto.getOngId() != null) && (codigo == null)) {
			codigo = 1;
		}
		
		return new Animal(objDto.getId(),codigo,objDto.getNome(),AnimalGenero.toEnum(objDto.getGenero()),
				Porte.toEnum(objDto.getPorte()),objDto.getVermifugado(),objDto.getCastrado(),
				new Ong (objDto.getOngId(),null, null, null, null, null, null, null, null, null, null),		
				AnimalStatus.toEnum(objDto.getStatus()),
				raca
				); 
	}
		
	public Page<Animal> search(String nome, List<Integer> ids, Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		List<Doenca> doencas = doencaRepository.findAllById(ids);
		return repo.findDistinctByNomeContainingAndDoencasIn(nome, doencas, pageRequest);	
	}
	public Page<Animal> findRacas(Integer id, Integer page, Integer linesPerPage, String orderBy, String direction) {
		Pessoa  pessoa = pessoaService.getUserLogged();
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);		
		if ((pessoa.getPerfil() == Perfil.MASTER.getCod())|| (pessoa.getPerfil() == Perfil.USUARIO.getCod())) {
			return repo.findByRacaIdAndStatus(id,AnimalStatus.DISPONIVEL.getCod(),pageRequest);
		}
		return repo.findByRacaIdAndStatusAndOngId(id,AnimalStatus.DISPONIVEL.getCod(),pessoa.getOng().getId(),pageRequest);
	}
	public AnimalDTO validaCampos(AnimalDTO animalDTO,Animal animal) {
		if(animalDTO.getPorte() == null) {
			animalDTO.setPorte(animal.getPorte());
		}
		if(animalDTO.getStatus() == null) {
			animalDTO.setStatus(animal.getStatus());
		}
		if(animalDTO.getGenero() == null) {
			animalDTO.setGenero(animal.getGenero());
		}
		if(animalDTO.getNome() == null) {
			animalDTO.setNome(animal.getNome());
		}
		if(animalDTO.getOngId() == null) {
			animalDTO.setOngId(animal.getOng().getId());
		}
		return animalDTO;
	}
	private void updateData(Animal newObj,Animal obj ) {		
		newObj.setOng(obj.getOng());		
		newObj.setRaca(obj.getRaca());
		newObj.setNome(obj.getNome());
		newObj.setPorte(obj.getPorte());
		newObj.setCodigo(obj.getCodigo());		
		newObj.setStatus(obj.getStatus());
		newObj.setGenero(obj.getGenero());
		newObj.setCastrado(obj.getCastrado());
		newObj.setVermifugado(obj.getVermifugado());
	}
	public void atualizaStatus(Animal animal) {	
		int status = AnimalStatus.DISPONIVEL.getCod();
		int EMuANALISE = AdocaoStatus.ANALISE.getCod();
		int APROVADO = AdocaoStatus.APROVADO.getCod();
		
		animal.setStatus(status);
		
		List<Adocao> listaAdocoesEmAnalise = adocaoService.obterAdocaoPorAnimaleStatus(animal,EMuANALISE);
		if (!listaAdocoesEmAnalise.isEmpty()) {
			System.out.println("entrou em processamento");
			animal.setStatus( AnimalStatus.PROCESSAMENTO.getCod());
		}
		
		List<Adocao> listaAdocoesAprovado = adocaoService.obterAdocaoPorAnimaleStatus(animal,APROVADO);
		if (!listaAdocoesAprovado.isEmpty()) {
			System.out.println("entrou em adotado");
			animal.setStatus( AnimalStatus.ADOTADO.getCod());
		}
		repo.save(animal);
	}
	public void adicionarAcompanhamento(Animal animal,List<Acompanhamento> acompanhamentos) {
		for (Acompanhamento acompanhamento : acompanhamentos) {
			animal.getAcompanhamentos().addAll(Arrays.asList(acompanhamento));			
		}
		repo.saveAll(Arrays.asList(animal));	
	}
	public void adicionarDoenca(Animal animal,List<Doenca> doencas) {
		for (Doenca doenca : doencas) {
			animal.getDoencas().addAll(Arrays.asList(doenca));			
		}
		repo.saveAll(Arrays.asList(animal));	
	}

	public void adicionarVacina(Animal animal,VacinaItem vacinaItem) {
		animal.getVacinas().addAll(Arrays.asList(vacinaItem));		
		repo.saveAll(Arrays.asList(animal));
	}
	
	public List<Animal> search(
			String nome, Integer genero, Integer porte, Integer castrado, Integer estadoId, Integer cidadeId, Integer racaId) 
	{
		Boolean isCastrado = true;
		if (castrado != 0) {
			isCastrado = castrado.equals(0) ? false : true;
		}
		
		Pessoa  pessoa = pessoaService.getUserLogged();
		List<Animal> listaAnimais = new ArrayList<Animal>();	
		if ((pessoa.getPerfil() == Perfil.MASTER.getCod())||(pessoa.getPerfil() == Perfil.USUARIO.getCod())) {
			listaAnimais = repo.obterAnimaisPorFiltro();
		}else {		
			listaAnimais = repo.obterAnimaisPorFiltro(pessoa.getOng().getId());
		}
		List<Animal> AnimaisRemover =  new ArrayList<Animal>();		
		if(!listaAnimais.isEmpty()) {
			for (Animal animal : listaAnimais) {
				if ((!animal.getNome().equals(nome)) &&(!nome.isEmpty())) {					
					AnimaisRemover.add(animal);
				}else 
				if ((animal.getGenero() != genero)&&(genero != 0)){
					AnimaisRemover.add(animal);
				}else
				if((animal.getPorte() != porte)&&(porte != 0)) {
					AnimaisRemover.add(animal);
				}else
				if ((animal.getCastrado() != isCastrado) && (castrado != 0)){
					AnimaisRemover.add(animal);
				}else
				if ((animal.getOng().getCidade().getId() != cidadeId) && (cidadeId != 0)) {
					AnimaisRemover.add(animal);
				}else
				if ((animal.getRaca().getId() != racaId) && (racaId != 0)) {
					AnimaisRemover.add(animal);
				}else
				if ((animal.getOng().getCidade().getEstado().getId() != estadoId) && (estadoId != 0)) {
					AnimaisRemover.add(animal);
				}	
			}
			listaAnimais.removeAll(AnimaisRemover);
		}
		return listaAnimais;
	}

	public URI uploadPicture(MultipartFile multipartFile, Integer id) {		
		return uploadService.uploadAnimalFile(multipartFile, id);
	}
	
}