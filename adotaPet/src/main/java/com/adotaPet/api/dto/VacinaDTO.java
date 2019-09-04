package com.adotaPet.api.dto;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import com.adotaPet.api.domain.Vacina;

public class VacinaDTO {
	
	private Integer id;	
	private Integer especie;
	private String nome;
	private String faixaIdade;
	private String prevencao;
	private Integer vacinaId;
	private Integer animalId;
	
	public Integer getVacinaId() {
		return vacinaId;
	}

	public void setVacinaId(Integer vacinaId) {
		this.vacinaId = vacinaId;
	}

	public Integer getAnimalId() {
		return animalId;
	}

	public void setAnimalId(Integer animalId) {
		this.animalId = animalId;
	}

	public VacinaDTO() {
	}
	
	public VacinaDTO(Vacina obj) {
		id = obj.getId();
		faixaIdade = obj.getFaixaIdade();
        nome = obj.getNome();
        prevencao = obj.getPrevencao();
        especie = obj.getEspecie();
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getEspecie() {
		return especie;
	}

	public void setEspecie(Integer especie) {
		this.especie = especie;
	}

	public String getPrevencao() {
		return prevencao;
	}

	public void setPrevencao(String prevencao) {
		this.prevencao = prevencao;
    }
    
    public String getFaixaIdade() {
		return faixaIdade;
	}

	public void setFaixaIdade(String faixaIdade) {
		this.faixaIdade = faixaIdade;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	
}
