package com.adotaPet.api.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
public class VacinaItem implements Serializable  {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;	

	@ManyToOne
	@JoinColumn(name="vacina_id")
	private Vacina vacina;
	
	@JsonFormat(pattern="dd/MM/yyyy")
	private Date dataVascinado;

	public VacinaItem() {
		
	}

	public VacinaItem(Integer id, Vacina vacina, Date dataVascinado) {
		super();
		this.id = id;
		this.vacina = vacina;
		this.dataVascinado = dataVascinado;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Vacina getVacina() {
		return vacina;
	}

	public void setVacina(Vacina vacina) {
		this.vacina = vacina;
	}

	public Date getDataVascinado() {
		return dataVascinado;
	}

	public void setDataVascinado(Date dataVascinado) {
		this.dataVascinado = dataVascinado;
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
		VacinaItem other = (VacinaItem) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	

}