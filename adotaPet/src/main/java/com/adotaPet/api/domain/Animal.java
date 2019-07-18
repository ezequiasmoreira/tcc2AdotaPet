package com.adotaPet.api.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import com.adotaPet.api.domain.enums.AnimalGenero;
import com.adotaPet.api.domain.enums.AnimalStatus;
import com.adotaPet.api.domain.enums.Porte;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
public class Animal implements Serializable {
	
private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	private Integer codigo;
	private String nome;
	private Integer genero;
	private Integer porte;
	private boolean vermifugado;
	private boolean castrado;	
	private Integer status;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name="ong_id")
	private Ong ong;	
	
	@ManyToOne
	@JoinColumn(name="raca_id")
	private Raca raca;
		
	@ManyToMany
	@JoinTable(name = "DOENCA_ANIMAL",
		joinColumns = @JoinColumn(name = "animal_id"),
		inverseJoinColumns = @JoinColumn(name = "doenca_id")
	)
	private List<Doenca> doencas = new ArrayList<>();
	
	@ManyToMany
	@JoinTable(name = "VACINA_ANIMAL",
		joinColumns = @JoinColumn(name = "animal_id"),
		inverseJoinColumns = @JoinColumn(name = "vacinaItem_id")
	)
	private List<VacinaItem> vacinas = new ArrayList<>();
	
		
	
	public Animal() {
	}
	
	public Animal(Integer id, Integer codigo, String nome, AnimalGenero genero, Porte porte, boolean vermifugado,
			boolean castrado, Ong ong, AnimalStatus status, Raca raca) {
		super();
		this.id = id;
		this.codigo = codigo;
		this.nome = nome;
		this.genero = genero.getCod();
		this.porte = porte.getCod();
		this.vermifugado = vermifugado;
		this.castrado = castrado;
		this.ong = ong;
		this.status = status.getCod();
		this.raca = raca;
	}
	
	public List<VacinaItem> getVacinas() {
		return vacinas;
	}

	public void setVacinas(List<VacinaItem> vacinas) {
		this.vacinas = vacinas;
	}
	
	public List<Doenca> getDoencas() {
		return doencas;
	}

	public void setDoencas(List<Doenca> doencas) {
		this.doencas = doencas;
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

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Integer getGenero() {
		return genero;
	}

	public void setGenero(Integer genero) {
		this.genero = genero;
	}

	public Integer getPorte() {
		return porte;
	}

	public void setPorte(Integer porte) {
		this.porte = porte;
	}

	public boolean getVermifugado() {
		return vermifugado;
	}

	public void setVermifugado(boolean vermifugado) {
		this.vermifugado = vermifugado;
	}

	public boolean getCastrado() {
		return castrado;
	}

	public void setCastrado(boolean castrado) {
		this.castrado = castrado;
	}

	public Ong getOng() {
		return ong;
	}

	public void setOng(Ong ong) {
		this.ong = ong;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Raca getRaca() {
		return raca;
	}

	public void setRaca(Raca raca) {
		this.raca = raca;
	}
	
	@Override
	public String toString() {
		return this.nome;
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
		Animal other = (Animal) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	
		
}
