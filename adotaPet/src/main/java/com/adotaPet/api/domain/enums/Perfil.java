package com.adotaPet.api.domain.enums;

public enum Perfil {
	MASTER(1, "ROLE_MASTER"),
	ADMIN(2, "ROLE_ADMN"),
	VOLUNTARIO(3, "ROLE_VOLUNTARIO"),
	USUARIO(4, "ROLE_USUARIO");
	
	private int cod;
	private String descricao;
	
	private Perfil(int cod, String descricao) {
		this.cod = cod;
		this.descricao = descricao;
	}

	public int getCod() {
		return cod;
	}
	public String getDescricao() {
		return descricao;
	}
	
	public static Perfil toEnum(Integer cod) {
		
		if (cod == null) {
			return null;
		}
		for(Perfil x : Perfil.values()) {
			if(cod.equals(x.getCod())) {
				return x;
			}		
		}
		throw new IllegalArgumentException("Id inválido " + cod);
	}
}
