package com.adotaPet.api.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
public class Ong implements Serializable {
	
private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	private Integer codigo;
	private String razao_Social;
	private String nome_Fantasia;
	private String cnpj;
	private String rua;
	private Integer numero;
	private String bairro;	
	
	public Ong() {
	}
	
	public Ong(Integer id, Integer codigo, String razao_Social, String nome_Fantasia, String cnpj, String rua,
			Integer numero, String bairro) {
		super();
		this.id = id;
		this.codigo = codigo;
		this.razao_Social = razao_Social;
		this.nome_Fantasia = nome_Fantasia;
		this.cnpj = cnpj;
		this.rua = rua;
		this.numero = numero;
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
		Ong other = (Ong) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return  this.razao_Social;
	}
	
	

	
	
}
