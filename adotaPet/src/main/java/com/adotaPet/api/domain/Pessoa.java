package com.adotaPet.api.domain;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.adotaPet.api.domain.enums.Perfil;
import com.adotaPet.api.domain.enums.Sexo;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;

@Entity
public class Pessoa extends Endereco {
	
private static final long serialVersionUID = 1L;

	private String nome;
	private Integer sexo;
	
	private Integer perfil;
	@ElementCollection(fetch=FetchType.EAGER)
	@CollectionTable(name="PERFIS")
	private Set<Integer> perfis = new HashSet<>();
	
	private String cpf;
	private String rg;
	private String email;
	private String senha;
	private String telefone;
	
	@ManyToOne
	@JoinColumn(name="ong_id")
	private Ong ong;
		
	public Pessoa() {
		addPerfil(Perfil.USUARIO);
	}
	
	public Pessoa(Integer id, Integer codigo, String logradouro, String numero, String complemento, String bairro, String cep, Cidade cidade,
			String nome, Sexo sexo, Perfil perfil, String cpf, String rg, String email, String senha, String telefone,Ong ong ) {
		super();
		
		setId(id);
		setCodigo(codigo);
		setLogradouro(logradouro);
		setNumero(numero);
		setComplemento(complemento);
		setBairro(bairro);
		setCep(cep);
		setCidade(cidade);
		this.nome = nome;
		this.sexo = sexo.getCod();
		this.perfil = perfil.getCod();
		this.cpf = cpf;
		this.rg= rg;
		this.email = email;
		this.senha = senha;
		this.telefone = telefone;
		this.ong = ong;
		addPerfil(Perfil.USUARIO);
	}
	
	public Set<Perfil> getPerfis() {
		return perfis.stream().map(x -> Perfil.toEnum(x)).collect(Collectors.toSet());
	}
	public void addPerfil(Perfil perfil) {
		perfis.add(perfil.getCod());
	}
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Integer getSexo() {
		return sexo;
	}

	public void setSexo(Integer sexo) {
		this.sexo = sexo;
	}

	public Integer getPerfil() {
		return perfil;
	}

	public void setPerfil(Integer perfil) {
		this.perfil = perfil;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getRg() {
		return rg;
	}

	public void setRg(String rg) {
		this.rg = rg;
	}

	public String getEmail() {
		return email;
	}

	public void setLogin(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public Ong getOng() {
		return ong;
	}

	public void setOng(Ong ong) {
		this.ong = ong;
	}

	@Override
	public String toString() {
		return this.nome;
	}
	
	
}
