package com.adotaPet.api.domain.enums;

public enum Porte {
	PEQUENO(1, "Pequeno"),
	MEDIO(2, "Medio"),
	GRANDE(3, "Grande");
	
	private int cod;
	private String descricao;
	
	private Porte(int cod, String descricao) {
		this.cod = cod;
		this.descricao = descricao;
	}

	public int getCod() {
		return cod;
	}
	public String getDescricao() {
		return descricao;
	}
	
	public static Porte toEnum(Integer cod) {
		
		if (cod == null) {
			return null;
		}
		for(Porte x : Porte.values()) {
			if(cod.equals(x.getCod())) {
				return x;
			}		
		}
		throw new IllegalArgumentException("Id inválido " + cod);
	}
}
