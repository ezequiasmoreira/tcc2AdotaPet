package com.adotaPet.api.service;



import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.adotaPet.api.domain.Cidade;
import com.adotaPet.api.domain.Ong;
import com.adotaPet.api.domain.Pessoa;
import com.adotaPet.api.domain.enums.Perfil;
import com.adotaPet.api.domain.enums.Sexo;
import com.adotaPet.api.dto.PessoaDTO;
import com.adotaPet.api.dto.PessoaNewDTO;
import com.adotaPet.api.repository.PessoaRepository;
import com.adotaPet.api.security.UserSS;
import com.adotaPet.api.service.exceptions.AuthorizationException;
import com.adotaPet.api.service.exceptions.DataIntegrityException;
import com.adotaPet.api.service.exceptions.ObjectNotFoundException;


@Service
public class PessoaService {

	@Autowired
	private PessoaRepository repo;
	
	@Autowired
	private BCryptPasswordEncoder pe;
	
	@Autowired
	private UploadService uploadService;
	
	public Pessoa find(Integer id) {
		UserSS user = UserService.authenticated();
		if (user==null || !user.hasRole(Perfil.MASTER) && !id.equals(user.getId())) {
			if(user.hasRole(Perfil.ADMIN)) {				
				Pessoa usuarioAdmin = repo.findPessoaId(user.getId());
				Pessoa usuario = repo.findPessoaId(id);
				if ((usuario.getOng() == null) || (usuarioAdmin.getOng() == null)) {
					throw new AuthorizationException("Acesso negado");
				}
				if (usuarioAdmin.getOng().getId() != (usuario.getOng().getId())) {
					throw new AuthorizationException("Acesso negado");
				}
			}
			if((user.hasRole(Perfil.VOLUNTARIO) || (user.hasRole(Perfil.USUARIO))) &&(!user.hasRole(Perfil.ADMIN))) {
				if(!id.equals(user.getId())){
					throw new AuthorizationException("Não possui permissão");
				}
				
			}
		}
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
		return repo.save(obj);
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
		UserSS user = UserService.authenticated();
		if (user==null ) {
			throw new AuthorizationException("Usuario não identificado favor fazer o login");
		}
		if (!user.hasRole(Perfil.MASTER) && !user.hasRole(Perfil.ADMIN)) {
			throw new AuthorizationException("Acesso negado para o nivel de permissão");
		}
		if (!user.hasRole(Perfil.MASTER) && user.hasRole(Perfil.ADMIN)) {
			Pessoa usuarioAdmin = repo.findPessoaId(user.getId());
			if(usuarioAdmin.getOng() == null) {
				throw new AuthorizationException("Ong não indentificada para usuario admin");
			}
			return repo.listPessoasOng(usuarioAdmin.getOng().getId());
		}
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
		UserSS user = UserService.authenticated();		
		if (user==null ) {
			throw new AuthorizationException("Usuario não identificado favor fazer o login");
		}				
		Pessoa usuariologado = repo.findPessoaId(user.getId());		

		if ((Perfil.toEnum(usuariologado.getPerfil()) == Perfil.USUARIO) ||  (Perfil.toEnum(usuariologado.getPerfil()) == Perfil.VOLUNTARIO)) {
			throw new AuthorizationException("Não possui permissão para adicionar usuarios");
		}
		
		Cidade cidade = new Cidade(objDto.getCidadeId(), null, null);
		Integer codigo = objDto.getOngId() == null ? null : repo.obterCodigo(objDto.getOngId());
		if (codigo == null && objDto.getOngId() != null) {
			codigo = 1;
		}
		Pessoa pessoa = new Pessoa(
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
		if ((Perfil.toEnum(usuariologado.getPerfil()) == Perfil.MASTER) || (Perfil.toEnum(usuariologado.getPerfil()) == Perfil.ADMIN)){
			if(objDto.getOngId() == null) {
				throw new AuthorizationException("Usuario a ser cadastrado deve possuir ong");
			}
		}
		if (Perfil.toEnum(usuariologado.getPerfil()) == Perfil.MASTER) {			
			pessoa.addPerfil(Perfil.ADMIN);
			pessoa.addPerfil(Perfil.VOLUNTARIO);
			Integer ADMIN = 2;
			pessoa.setPerfil(ADMIN);			
		}
		if (Perfil.toEnum(usuariologado.getPerfil()) == Perfil.ADMIN) {
			pessoa.addPerfil(Perfil.VOLUNTARIO);
			Integer VOLUNTARIO = 3;
			pessoa.setPerfil(VOLUNTARIO);
		}
		return pessoa;
	}
	
	public Page<Pessoa> search(Integer ongId, Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);		
		return repo.obterPessoaPorOng(ongId,pageRequest);	
	}
	
		
	public Pessoa updateData (PessoaNewDTO obj,Integer id) {
		Pessoa pessoa = repo.findPessoaId(id);
		if (obj.getId() == null) {
			throw new AuthorizationException("Não foi possivel atualizar o usuário");
		}
		
		Cidade cidade = obj.getCidadeId()==null ? pessoa.getCidade() : new Cidade(obj.getCidadeId(), null, null);
		
		pessoa.setId(obj.getId());
		pessoa.setCpf( obj.getCpf()==null ? pessoa.getCpf() : obj.getCpf());
		pessoa.setSexo( obj.getSexo()==null ? pessoa.getSexo() : obj.getSexo());
		pessoa.setRg( obj.getRg()==null ? pessoa.getRg() : obj.getRg());
		pessoa.setNumero( obj.getNumero()==null ? pessoa.getNumero() : obj.getNumero());
		pessoa.setNome( obj.getNome()==null ? pessoa.getNome() : obj.getNome());
		pessoa.setLogradouro( obj.getLogradouro()==null ? pessoa.getLogradouro() : obj.getLogradouro());
		pessoa.setCpf( obj.getCpf()==null ? pessoa.getCpf() :  obj.getCpf() );
		pessoa.setComplemento( obj.getComplemento()==null ? pessoa.getComplemento() : obj.getComplemento());
		pessoa.setCidade(cidade);
		pessoa.setCep( obj.getCep()==null ? pessoa.getCep() : obj.getCep());
		pessoa.setBairro(obj.getBairro()==null ? pessoa.getBairro() : obj.getBairro());
		
		return pessoa;
	}
	public URI uploadProfilePicture(MultipartFile multipartFile) {		
		return uploadService.uploadFile(multipartFile);
	}
}