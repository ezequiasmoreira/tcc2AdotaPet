package com.adotaPet.api.domain;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.adotaPet.api.domain.enums.AdocaoStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.util.Date;

@Entity
public class Adocao implements Serializable {
	
private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	private Integer codigo;
	
	@JsonFormat(pattern="dd/MM/yyyy")
	private Date dataCadastro;
	
	@JsonFormat(pattern="dd/MM/yyyy")
	private Date dataFinalizacao;
	
	private Integer status;
	
	@ManyToOne
	@JoinColumn(name="pessoaInterressado_id")
	private Pessoa pessoaInterressado;
	
	@ManyToOne
	@JoinColumn(name="pessoaIntermediador_id")
	private Pessoa pessoaIntermediador;
	
	private String observacao;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name="ong_id")
	private Ong ong;
	
	@ManyToOne
	@JoinColumn(name="animal_id")
	private Animal animal;
	
	public Adocao() {
	}

	public Adocao(Integer id, Integer codigo, Date dataCadastro, Date dataFinalizacao, AdocaoStatus status,
			Pessoa pessoaInterressado, Pessoa pessoaIntermediador, String observacao, Ong ong, Animal animal) {
		super();
		this.id = id;
		this.codigo = codigo;
		this.dataCadastro = dataCadastro;
		this.dataFinalizacao = dataFinalizacao;
		this.status = status.getCod();
		this.pessoaInterressado = pessoaInterressado;
		this.pessoaIntermediador = pessoaIntermediador;
		this.observacao = observacao;
		this.ong = ong;
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

	@Override
	public String toString() {
		return this.codigo + "";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Adocao other = (Adocao) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	

	
}