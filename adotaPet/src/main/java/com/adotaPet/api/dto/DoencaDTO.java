package com.adotaPet.api.dto;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import com.adotaPet.api.domain.Doenca;

public class DoencaDTO {
	
	private Integer id;	
	private Integer codigo;
	@NotEmpty(message="Preenchimento obrigatório")	
	@Length(min=5, max=80, message="O tamanho deve ser entre 5 e 80 caracteres")
	private String descricao;

	private Integer animal ;
	
	public DoencaDTO() {
	}
	
	public DoencaDTO(Doenca obj) {
		id = obj.getId();
		codigo = obj.getCodigo();
		descricao = obj.getDescricao();
	}

	public Integer getAnimal() {
		return animal;
	}

	public void setAnimal(Integer animal) {
		this.animal = animal;
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
