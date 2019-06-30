package com.adotaPet.api.dto;

import com.adotaPet.api.domain.Estado;

public class EstadoDTO {
	
	private Integer id;	
	private Integer codigo;
	private String nome;
	
	
	public EstadoDTO() {
	}
	public EstadoDTO(Estado obj) {
		id = obj.getId();
		nome = obj.getNome();	
		codigo = obj.getCodigo();
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

	
	
}
