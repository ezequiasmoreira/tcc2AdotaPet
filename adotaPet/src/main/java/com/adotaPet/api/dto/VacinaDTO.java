package com.adotaPet.api.dto;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import com.adotaPet.api.domain.Vacina;

public class VacinaDTO {
	
	private Integer id;	
	private Integer especie;
	@NotEmpty(message="Preenchimento obrigatório")	
	@Length(min=5, max=80, message="O tamanho deve ser entre 5 e 80 caracteres")
	private String nome;
	@NotEmpty(message="Preenchimento obrigatório")	
	@Length(min=5, max=80, message="O tamanho deve ser entre 5 e 80 caracteres")
	private String faixaIdade;
	@NotEmpty(message="Preenchimento obrigatório")	
	@Length(min=5, max=80, message="O tamanho deve ser entre 5 e 80 caracteres")
	private String prevencao;
	
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
