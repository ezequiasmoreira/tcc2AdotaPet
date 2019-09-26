package com.adotaPet.api.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.adotaPet.api.domain.Acompanhamento;
import com.adotaPet.api.domain.Adocao;
import com.adotaPet.api.domain.Animal;
import com.fasterxml.jackson.annotation.JsonFormat;

public class AcompanhamentoDTO {
	
	private Integer id;
	private Integer codigo;
	private String descricao;
	private Integer status;
	private Integer situacao;
	private String observacao;
	
	@JsonFormat(pattern="dd/MM/yyyy")
	private Date dataCadastro;
	
	private String dataAgendado;
	private Integer animal ;
	private Integer animalCodigo;
	private String animalNome;
	
	private Adocao adocao;
	
	public AcompanhamentoDTO() {
	}
	
	public AcompanhamentoDTO(Acompanhamento obj) {		
		this.id = obj.getId();
		this.codigo = obj.getCodigo();
		this.descricao = obj.getDescricao();
		this.status = obj.getStatus();
		this.situacao = obj.getSituacao();
		this.observacao = obj.getObservacao();		
		this.dataCadastro = obj.getDataCadastro();
		this.dataAgendado = obj.getDataAgendado()+"";	
		for (Animal animal : obj.getAnimais()) {
			this.animal = animal.getId();
			this.animalCodigo = animal.getCodigo();
			this.animalNome = animal.getNome();
		}
	}

	public Integer getAnimal() {
		return animal;
	}
	
	public Integer getAnimalCodigo() {
		return animalCodigo;
	}

	public void setAnimalCodigo(Integer animalCodigo) {
		this.animalCodigo = animalCodigo;
	}

	public String getAnimalNome() {
		return animalNome;
	}

	public void setAnimalNome(String animalNome) {
		this.animalNome = animalNome;
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

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getSituacao() {
		return situacao;
	}

	public void setSituacao(Integer situacao) {
		this.situacao = situacao;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public String getDataAgendado() {
		return dataAgendado;
	}

	public void setDataAgendado(String dataAgendado) {
		this.dataAgendado = dataAgendado;
	}

	public Adocao getAdocao() {
		return adocao;
	}

	public void setAdocao(Adocao adocao) {
		this.adocao = adocao;
	}

}
