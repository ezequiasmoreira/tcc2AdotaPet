package com.adotaPet.api.dto;
import com.adotaPet.api.domain.Raca;

public class RacaDTO {
	
	private Integer id;		
	private Integer codigo;
	private String descricao;
	
	public RacaDTO() {
	}
	
	public RacaDTO(Raca obj) {
		id = obj.getId();
		codigo = obj.getCodigo();
		descricao = obj.getDescricao();
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
