package com.adotaPet.api.domain.enums;

public enum AnimalStatus {
	DISPONIVEL(1, "Disponivel"),
	PROCESSAMENTO(2, "Processamento"),
	ADOTADO(4, "Adotado");
	
	private int cod;
	private String descricao;
	
	private AnimalStatus(int cod, String descricao) {
		this.cod = cod;
		this.descricao = descricao;
	}

	public int getCod() {
		return cod;
	}
	public String getDescricao() {
		return descricao;
	}
	
	public static AnimalStatus toEnum(Integer cod) {
		
		if (cod == null) {
			return null;
		}
		for(AnimalStatus x : AnimalStatus.values()) {
			if(cod.equals(x.getCod())) {
				return x;
			}		
		}
		throw new IllegalArgumentException("Id inválido " + cod);
	}
}
