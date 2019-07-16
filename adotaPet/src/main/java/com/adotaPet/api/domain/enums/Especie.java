package com.adotaPet.api.domain.enums;

public enum Especie {
	CAO(1, "Cão", "Mamíferos"),
	GATO(2, "Gato", "Mamíferos"),
	FURAO(3, "Furão", "Mamíferos"),
	MICO(4, "Mico", "Mamíferos"),
	CAVALO(5, "Cavalo", "Mamíferos"),
	PETAURO(6, "Petauro de Açúcar", "Mamíferos"),
	OURICO(7, "Ouriço", "Mamíferos"),
	COELHO(8, "Coelho", "Mamíferos"),
	HAMSTER(9, "Hamster", "Mamíferos"),
	RATO(10, "Rato", "Mamíferos"),
	CAMUNDONGO(11, "Camundongo", "Mamíferos"),
	PORCOINDIA(12, "Porco da Índia", "Mamíferos"),
	PORCO(13, "Porco doméstico", "Mamíferos"), 
	CHINCHILA(14, "Chinchila", "Mamíferos"),
	GERBIL(15, "Gerbil", "Mamíferos"),
	ESQUILO(16, "Gerbil", "Mamíferos"),

	PIRIQUITOS(17, "Gerbil", "Aves"),
	CANARIO(18, "Canário", "Aves"),
	CATURRA(19, "Caturra", "Aves"),
	CACATUAS(20, "Cacatuas", "Aves"),
	PAPAGAIOS(21, "Papagaios", "Aves"),
	GALINHA(22, "Galinha", "Aves"),
	GALO(23, "Galo", "Aves"),
	PERU(24, "Peru", "Aves"),
	ARARA(25, "Arara", "Aves"),
	MANDARIM(26, "Mandarim", "Aves"),
	AGAPORNIS(27, "Agapornis", "Aves"),
	GAPORNIS(28, "Agapornis", "Aves"),
	PARDAL(29, "Pardal", "Aves"),
	GALAH(30, "Galah", "Aves"),
	CALAFATE(31, "Calafate", "Aves"),
	CARDEAL(32, "Cardeal", "Aves"),
	CURIO(33, "Curió", "Aves"),
	CANARIOTERRA(34, "Canário-da-terra", "Aves"),

	CAGADOS(35, "CÁGADOS", "Répteis"),
	TARTARUGAS(36, "Tartarugas", "Répteis"),
	JABUTIS(37, "Jabutis", "Répteis"),
	TEIU(38, "Teiú", "Répteis"),
	IGUANA(39, "Iguana", "Répteis"),
	LAGARTOS(40, "Lagartos", "Répteis"),
	COBRAS(41, "Cobras", "Répteis"),

	SAPOS(42, "Sapos", "Anfíbios"),
	PERERECA(43, "Perereca", "Anfíbios"),
	SALAMANDRAS(44, "Salamandras", "Anfíbios"),

	POECILIDEOS(45, "Poecilídeos", "Peixes"),
	BETTA(46, "Betta", "Peixes"),
	KINGUIO(47, "Kinguio", "Peixes"),
	CARPA(48, "Carpa", "Peixes"),
	BARBOS(49, "Barbos", "Peixes"),
	PEIXEPALHACO(50, "Peixe-palhaço", "Peixes"),
	TETRAS(51, "Tetras", "Peixes"),
	ACARAS(52, "Acarás", "Peixes"),
	OSCAR(53, "Oscar", "Peixes"),
	CIRURGIOES(54, "Cirurgiões", "Peixes"),
	CASCUDOS(55, "Cascudos", "Peixes"),
	CORIDORAS(56, "Coridoras", "Peixes"),

	TARANTULAS(57, "Tarântulas", "Invertebrados"),
	CARAMUJOS(57, "Caramujos", "Invertebrados"),
	CARANGUEJOS(58, "Caranguejos", "Invertebrados");
	
	private int cod;
	private String descricao;
	private String classificacao;
	
	private Especie(int cod, String descricao,String classificacao) {
		this.cod = cod;
		this.descricao = descricao;
		this.classificacao = classificacao;
	}

	public int getCod() {
		return cod;
	}
	public String getDescricao() {
		return descricao;
	}
	public String getClassificacao() {
		return classificacao;
	}
	
	public static Especie toEnum(Integer cod) {
		
		if (cod == null) {
			return null;
		}
		for(Especie x : Especie.values()) {
			if(cod.equals(x.getCod())) {
				return x;
			}		
		}
		throw new IllegalArgumentException("Id inválido " + cod);
	}
}
