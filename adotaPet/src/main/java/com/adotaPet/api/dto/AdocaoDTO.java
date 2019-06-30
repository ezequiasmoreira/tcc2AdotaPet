package com.adotaPet.api.dto;

import java.util.Date;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import com.adotaPet.api.domain.Animal;
import com.adotaPet.api.domain.Ong;
import com.adotaPet.api.domain.Pessoa;
import com.adotaPet.api.domain.Adocao;
import com.fasterxml.jackson.annotation.JsonFormat;

public class AdocaoDTO {
	
	private Integer id;
	private Integer codigo;
	
	@JsonFormat(pattern="dd/MM/yyyy")
	private Date dataCadastro;
	
	@JsonFormat(pattern="dd/MM/yyyy")
	private Date dataFinalizacao;
	
	private Integer status;
	private Pessoa pessoaInterressado;
	private Pessoa pessoaIntermediador;
	private String observacao;
	private Ong ong;
	private Animal animal;
	
	public AdocaoDTO() {
	}
	
	public AdocaoDTO(Adocao obj) {		
		this.id = obj.getId();
		this.codigo = obj.getCodigo();
		this.dataCadastro = obj.getDataCadastro();
		this.dataFinalizacao = obj.getDataFinalizacao();
		this.status = obj.getStatus();
		this.pessoaInterressado = obj.getPessoaInterressado();
		this.pessoaIntermediador = obj.getPessoaIntermediador();
		this.observacao = obj.getObservacao();
		this.ong = obj.getOng();
		this.animal = obj.getAnimal();
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

	public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public Date getDataFinalizacao() {
		return dataFinalizacao;
	}

	public void setDataFinalizacao(Date dataFinalizacao) {
		this.dataFinalizacao = dataFinalizacao;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Pessoa getPessoaInterressado() {
		return pessoaInterressado;
	}

	public void setPessoaInterressado(Pessoa pessoaInterressado) {
		this.pessoaInterressado = pessoaInterressado;
	}

	public Pessoa getPessoaIntermediador() {
		return pessoaIntermediador;
	}

	public void setPessoaIntermediador(Pessoa pessoaIntermediador) {
		this.pessoaIntermediador = pessoaIntermediador;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public Ong getOng() {
		return ong;
	}

	public void setOng(Ong ong) {
		this.ong = ong;
	}

	public Animal getAnimal() {
		return animal;
	}

	public void setAnimal(Animal animal) {
		this.animal = animal;
	}
}
