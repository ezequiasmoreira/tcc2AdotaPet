package com.adotaPet.api.domain.enums;

public enum AdocaoStatus {
	AGUARDANDO(1, "Aguardando"),
	ANALISE(2, "Analise"),
	REJEITADO(3, "Rejeitado"),
	APROVADO(4, "Aprovado");
	
	private int cod;
	private String descricao;
	
	private AdocaoStatus(int cod, String descricao) {
		this.cod = cod;
		this.descricao = descricao;
	}

	public int getCod() {
		return cod;
	}
	public String getDescricao() {
		return descricao;
	}
	
	public static AdocaoStatus toEnum(Integer cod) {
		
		if (cod == null) {
			return null;
		}
		for(AdocaoStatus x : AdocaoStatus.values()) {
			if(cod.equals(x.getCod())) {
				return x;
			}		
		}
		throw new IllegalArgumentException("Id inválido " + cod);
	}
}
