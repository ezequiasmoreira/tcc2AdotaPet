package com.adotaPet.api.dto;

import com.adotaPet.api.domain.Cidade;
import com.adotaPet.api.domain.Ong;

public class OngDTO {
	
	private Integer id;
	private Integer codigo;
	private String logradouro;
	private String numero;
	private String complemento;
	private String bairro;
	private String cep;
	private String razao_Social;
	private String nome_Fantasia;
	private String cnpj;
	private Cidade cidade;
	
	public OngDTO() {
	}
	
	public OngDTO(Ong obj) {
		id = obj.getId();
		codigo = obj.getCodigo();
		logradouro = obj.getLogradouro();
		numero = obj.getNumero();
		complemento = obj.getComplemento();
		bairro = obj.getBairro();
		cep = obj.getCep();		
		razao_Social = obj.getRazao_Social();
		nome_Fantasia = obj.getNome_Fantasia();
		cnpj = obj.getCnpj();
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

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getRazao_Social() {
		return razao_Social;
	}

	public void setRazao_Social(String razao_Social) {
		this.razao_Social = razao_Social;
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
	
	public Cidade getCidade() {
		return cidade;
	}

	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
	}
	
	
}
