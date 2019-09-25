package com.adotaPet.api.domain.enums;

public enum AcompanhamentoStatus {
	ABERTO(1, "Aberto"),
	AGENDADO(2, "Agendado"),
	SOLICITADO(3, "Solicitado"),
	FINALIZADO(4, "Finalizado");
	
	private int cod;
	private String descricao;
	
	private AcompanhamentoStatus(int cod, String descricao) {
		this.cod = cod;
		this.descricao = descricao;
	}

	public int getCod() {
		return cod;
	}
	public String getDescricao() {
		return descricao;
	}
	
	public static AcompanhamentoStatus toEnum(Integer cod) {
		
		if (cod == null) {
			return null;
		}
		for(AcompanhamentoStatus x : AcompanhamentoStatus.values()) {
			if(cod.equals(x.getCod())) {
				return x;
			}		
		}
		throw new IllegalArgumentException("Id inválido " + cod);
	}
}
