package com.adotaPet.api.dto;


import com.adotaPet.api.domain.Animal;

public class AnimalDTO {
	
	private Integer id;	
	private Integer codigo;
	private String nome;
	private Integer genero;
	private Integer porte;
	private boolean vermifugado;
	private boolean castrado;	
	private Integer ongId;
	private Integer status;
	private Integer racaId;
	private String cidade;
	
	public AnimalDTO() {
	}
	
	public AnimalDTO(Animal obj) {
		id = obj.getId();
		ongId = obj.getOng().getId();		
		racaId = obj.getRaca().getId();
		nome = obj.getNome();	
		porte = obj.getPorte();
		status = obj.getStatus();
		codigo = obj.getCodigo();
		genero = obj.getGenero();				
		castrado = obj.getCastrado();		
		vermifugado = obj.getVermifugado();
		cidade = obj.getOng().getCidade().getNome();
	}
	
	public String getCidade() {
		return cidade;
	}
	public void setCidade(String cidade) {
		this.cidade = cidade;
	}
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Integer getGenero() {
		return genero;
	}

	public void setGenero(Integer genero) {
		this.genero = genero;
	}

	public Integer getPorte() {
		return porte;
	}

	public void setPorte(Integer porte) {
		this.porte = porte;
	}

	public boolean getVermifugado() {
		return vermifugado;
	}

	public void setVermifugado(boolean vermifugado) {
		this.vermifugado = vermifugado;
	}

	public boolean getCastrado() {
		return castrado;
	}

	public void setCastrado(boolean castrado) {
		this.castrado = castrado;
	}

	public Integer getOngId() {
		return ongId;
	}

	public void setOngId(Integer ongId) {
		this.ongId = ongId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getRacaId() {
		return racaId;
	}

	public void setRacaId(Integer racaId) {
		this.racaId = racaId;
	}
	
	
	
	
}
