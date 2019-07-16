package com.adotaPet.api.dto;
import com.adotaPet.api.domain.Raca;

public class RacaDTO {
	
	private Integer id;		
	private Integer codigo;
	private String descricao;
	private Integer especie;
	
	public RacaDTO() {
	}
	
	public RacaDTO(Raca obj) {
		id = obj.getId();
		codigo = obj.getCodigo();
		descricao = obj.getDescricao();
		especie = obj.getEspecie();
	}

	public Integer getEspecie() {
		return especie;
	}

	public void setEspecie(Integer especie) {
		this.especie = especie;
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

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	
}
