package com.adotaPet.api.domain.enums;

public enum AcompanhamentoSituacao {
	OTIMO(1, "Otimo"),
	BOM(2, "Bom"),
	REGULAR(3, "Regular"),
	RUIM(4, "Ruim"),
	ANALISE(5, "Analise"),
	PROCESSOS(6, "Processos"),
	GERADOAUTO(7, "Gerado automaticamente");
	
	private int cod;
	private String descricao;
	
	private AcompanhamentoSituacao(int cod, String descricao) {
		this.cod = cod;
		this.descricao = descricao;
	}

	public int getCod() {
		return cod;
	}
	public String getDescricao() {
		return descricao;
	}
	
	public static AcompanhamentoSituacao toEnum(Integer cod) {
		
		if (cod == null) {
			return null;
		}
		for(AcompanhamentoSituacao x : AcompanhamentoSituacao.values()) {
			if(cod.equals(x.getCod())) {
				return x;
			}		
		}
		throw new IllegalArgumentException("Id inválido " + cod);
	}
}
