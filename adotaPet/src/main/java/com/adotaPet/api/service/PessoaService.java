package com.adotaPet.api.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.adotaPet.api.domain.Cidade;
import com.adotaPet.api.domain.Ong;
import com.adotaPet.api.domain.Pessoa;
import com.adotaPet.api.domain.enums.Perfil;
import com.adotaPet.api.domain.enums.Sexo;
import com.adotaPet.api.dto.PessoaDTO;
import com.adotaPet.api.dto.PessoaNewDTO;
import com.adotaPet.api.repository.PessoaRepository;
import com.adotaPet.api.service.exceptions.DataIntegrityException;
import com.adotaPet.api.service.exceptions.ObjectNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@Service
public class PessoaService {

	@Autowired
	private PessoaRepository repo;
	@Autowired
	private BCryptPasswordEncoder pe;

	public Pessoa find(Integer id) {
		Optional<Pessoa> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Pessoa.class.getName()));
	}
	
	@Transactional
	public Pessoa insert(Pessoa obj) {
		obj.setId(null);
		return repo.save(obj);
	}
	
	public Pessoa update(Pessoa obj) {
		Pessoa newObj = find(obj.getId());
		updateData(newObj, obj);
		return repo.save(newObj);
	}
	
	public void delete(Integer id) {
		find(id);
		try {
			repo.deleteById(id);
		}
		catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir a Pessoa");
		}
	}
	
	public List<Pessoa> findAll() {
		return repo.findAll();
	}
	
	public Page<Pessoa> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repo.findAll(pageRequest);
	}
	
	public Pessoa fromDTO(PessoaDTO objDto) {
		return new Pessoa(
				objDto.getId(), 
				objDto.getCodigo(),
				objDto.getLogradouro(),
				objDto.getNumero(),
				objDto.getComplemento(),
				objDto.getBairro(),
				objDto.getCep(),
				objDto.getCidade(),
				objDto.getNome(),
				Sexo.toEnum(objDto.getSexo()),
				Perfil.toEnum(objDto.getPerfil()),
				objDto.getCpf(),
				objDto.getRg(),
				null,
				null,
				objDto.getTelefone(),
				null
				);
	}
	public Pessoa fromDTO(PessoaNewDTO objDto) {
		Cidade cidade = new Cidade(objDto.getCidadeId(), null, null);
		Integer codigo = objDto.getOngId() == null ? null : repo.obterCodigo(objDto.getOngId());
		if (codigo == null && objDto.getOngId() != null) {
			codigo = 1;
		}
		return new Pessoa(
				null,
				codigo,
				objDto.getLogradouro(),
				objDto.getNumero(),
				objDto.getComplemento(),
				objDto.getBairro(),
				objDto.getCep(),
				cidade,
				objDto.getNome(),
				Sexo.toEnum(objDto.getSexo()),
				objDto.getOngId() == null ? Perfil.USUARIO : Perfil.VOLUNTARIO,
				objDto.getCpf(),
				objDto.getRg(),
				objDto.getEmail(),
				pe.encode(objDto.getSenha()),
				objDto.getTelefone(),
				objDto.getOngId() == null ? null : new Ong (objDto.getOngId(),null, null, null, null, null, null, null, null, null, null)
				);
	}
	public Page<Pessoa> search(Integer ongId, Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);		
		return repo.obterPessoaPorOng(ongId,pageRequest);	
	}
	
	private void updateData(Pessoa newObj, Pessoa obj) {
		newObj.setCodigo(obj.getCodigo());
		newObj.setCpf(obj.getCpf());
	}
}