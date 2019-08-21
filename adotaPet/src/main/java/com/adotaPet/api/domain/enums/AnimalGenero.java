package com.adotaPet.api.domain.enums;

public enum AnimalGenero {
	MACHO(1, "Macho"),
	FEMEA(2, "Femea");
	
	private Integer cod;
	private String descricao;
	
	private AnimalGenero(Integer cod, String descricao) {
		this.cod = cod;
		this.descricao = descricao;
	}

	public Integer getCod() {
		return cod;
	}
	public String getDescricao() {
		return descricao;
	}
	
	public static AnimalGenero toEnum(Integer cod) {
		
		if (cod == null) {
			return null;
		}
		for(AnimalGenero x : AnimalGenero.values()) {
			if(cod.equals(x.getCod())) {
				return x;
			}		
		}
		throw new IllegalArgumentException("Id inválido " + cod);
	}
}
