package com.adotaPet.api.domain;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class Ong extends Endereco {
	
private static final long serialVersionUID = 1L;

	private String razao_Social;
	private String nome_Fantasia;
	@Column(unique=true)
	private String cnpj;
	
	public Ong() {
	}
	
	public Ong(Integer id, Integer codigo,String logradouro, String numero, String complemento, String bairro, String cep, 
			Cidade cidade,String razao_Social,String nome_Fantasia,String cnpj ) {
		super();
		
		setId(id);
		setCodigo(codigo);
		setLogradouro(logradouro);
		setNumero(numero);
		setComplemento(complemento);
		setBairro(bairro);
		setCep(cep);
		setCidade(cidade);
		setRazao_Social(razao_Social);
		setNome_Fantasia(nome_Fantasia);
		setCnpj(cnpj);		
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
	
	@Override
	public String toString() {
		return  this.razao_Social;
	}
	
}
