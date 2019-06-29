package com.adotaPet.api.dto;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import com.adotaPet.api.domain.Ong;

public class OngDTO {
	
	private Integer id;	
	@NotEmpty(message="Preenchimento obrigatório")	
	private Integer codigo;
	@NotEmpty(message="Preenchimento obrigatório")	
	@Length(min=5, max=80, message="O tamanho deve ser entre 5 e 80 caracteres")
	private String razao_Social;
	private String nome_Fantasia;
	@NotEmpty(message="Preenchimento obrigatório")
	private String cnpj;
	private String rua;
	private Integer numero;
	private String bairro;	
	
	public OngDTO() {
	}
	
	public OngDTO(Ong obj) {
		id = obj.getId();
		codigo = obj.getCodigo();
		razao_Social = obj.getRazao_Social();
		cnpj = obj.getCnpj();
	}
	
	public String getNome_Fantasia() {
		return nome_Fantasia;
	}

	public void setNome_Fantasia(String nome_Fantasia) {
		this.nome_Fantasia = nome_Fantasia;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public String getRua() {
		return rua;
	}

	public void setRua(String rua) {
		this.rua = rua;
	}

	public Integer getNumero() {
		return numero;
	}

	public void setNumero(Integer numero) {
		this.numero = numero;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
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
	public String getRazao_Social() {
		return razao_Social;
	}
	public void setRazao_Social(String razao_Social) {
		this.razao_Social = razao_Social;
	}
	
	
}
