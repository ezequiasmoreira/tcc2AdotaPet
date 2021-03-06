package com.adotaPet.api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.adotaPet.api.domain.Cidade;
import com.adotaPet.api.domain.Ong;
import com.adotaPet.api.dto.OngDTO;
import com.adotaPet.api.repository.OngRepository;
import com.adotaPet.api.service.exceptions.DataIntegrityException;
import com.adotaPet.api.service.exceptions.ObjectNotFoundException;


@Service
public class OngService {

	@Autowired
	private OngRepository repo;
	@Autowired
	private CidadeService cidadeService;

	public Ong find(Integer id) {
		Optional<Ong> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Ong.class.getName()));
	}
	public Ong findOngId(Integer ongId) {		
		return repo.findOngId(ongId);
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
			throw new DataIntegrityException("Não é possível excluir a ong");
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
		Cidade cidade = cidadeService.getCidade(objDto.getCidadeId());
		return new Ong(
				objDto.getId(), 
				repo.obterCodigo(),
				objDto.getLogradouro(),
				objDto.getNumero(),
				objDto.getComplemento(),
				objDto.getBairro(),
				objDto.getCep(),
				cidade,
				objDto.getRazao_Social(),
				objDto.getNome_Fantasia(),
				objDto.getCnpj()
				);
	}
	
	private void updateData(Ong newObj, Ong obj) {
		newObj.setCodigo(obj.getCodigo());
		newObj.setCnpj(obj.getCnpj());
		newObj.setRazao_Social(obj.getRazao_Social());
		newObj.setNome_Fantasia(obj.getNome_Fantasia());
		newObj.setNumero(obj.getNumero());
		newObj.setBairro(obj.getBairro());
		newObj.setLogradouro(obj.getLogradouro());
		newObj.setComplemento(obj.getComplemento());
		newObj.setCep(obj.getCep());
		newObj.setCidade(obj.getCidade());
	}
}