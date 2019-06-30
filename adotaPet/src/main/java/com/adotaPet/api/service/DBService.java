package com.adotaPet.api.service;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adotaPet.api.domain.Animal;
import com.adotaPet.api.domain.Doenca;
import com.adotaPet.api.domain.Ong;
import com.adotaPet.api.domain.Raca;
import com.adotaPet.api.domain.enums.AnimalGenero;
import com.adotaPet.api.domain.enums.AnimalStatus;
import com.adotaPet.api.domain.enums.Porte;
import com.adotaPet.api.repository.AnimalRepository;
import com.adotaPet.api.repository.DoencaRepository;
import com.adotaPet.api.repository.OngRepository;
import com.adotaPet.api.repository.RacaRepository;

@Service
public class DBService {
	
	@Autowired
	private OngRepository ongRepository;
	@Autowired
	private RacaRepository racaRepository;
	@Autowired
	private DoencaRepository doencaRepository;
	@Autowired
	private AnimalRepository animalRepository;
	@Autowired
	private OngService ongService;
	@Autowired
	private DoencaService doencaService;
	
	
	public void instanciateTestDataBase() {
		Ong ong1 = new Ong(null, 10,"Ong Cocal", "Cocal LTDA","62.769.648/0001-84",
				"Avenida Brasil",500,"Centro");
		Ong ong2 = new Ong(null, 10,"Ong pata branca", "Pata branca LTDA","98.013.581/0001-09",
				"Avenida 7 de setembro",21,"Vila nova");
				
		ongRepository.saveAll(Arrays.asList(ong1,ong2));
		
		Ong ong10 =  ongService.find(1);
		Ong ong11 =  ongService.find(2);
		Raca raca1 = new Raca(null,1,"Pastor Alemão",ong10);
		Raca raca2 = new Raca(null,4,"Buldogue",ong10);
		Raca raca3 = new Raca(null,12,"Chihuahua",ong10);
		Raca raca4 = new Raca(null,20,"Dalmata",ong11);
		Raca raca5 = new Raca(null,22,"Pinscher",ong11);
		
		racaRepository.saveAll(Arrays.asList(raca1,raca2,raca3,raca4,raca5));
		
		Doenca doenca1 = new Doenca(null,1,"Alergia alimentar",ong10);
		Doenca doenca2 = new Doenca(null,2,"Depressão",ong10);
		Doenca doenca3 = new Doenca(null,3,"Erlichiose",ong10);
		Doenca doenca4 = new Doenca(null,4,"Insuficiência renal",ong11);
		Doenca doenca5 = new Doenca(null,5,"Obesidade",ong11);
		Doenca doenca6 = new Doenca(null,6,"Otite",ong11);
		
		doencaRepository.saveAll(Arrays.asList(doenca1,doenca2,doenca3,doenca4,doenca5,doenca6));
		
		Animal animal1 = new Animal(null, 5,"Belinha", AnimalGenero.FEMEA, Porte.PEQUENO, true, false, true, ong10, AnimalStatus.DISPONIVEL, raca1);
		animal1.getDoencas().addAll(Arrays.asList(doenca1,doenca4));
		Animal animal2 = new Animal(null, 8,"Charopinho", AnimalGenero.MACHO, Porte.PEQUENO, true, true, false, ong10, AnimalStatus.PROCESSAMENTO, raca1);
		Animal animal3 = new Animal(null, 9,"Chulinho", AnimalGenero.MACHO, Porte.MEDIO, false, false, true, ong11, AnimalStatus.ADOTADO, raca2);
		animal3.getDoencas().addAll(Arrays.asList(doenca2,doenca3,doenca5,doenca6));
		Animal animal4 = new Animal(null, 10,"Lindinha", AnimalGenero.FEMEA, Porte.MEDIO, false, true, false, ong11, AnimalStatus.ADOTADO, raca4);
		animal4.getDoencas().addAll(Arrays.asList(doenca1,doenca2,doenca3,doenca4,doenca5));
		Animal animal5 = new Animal(null, 15,"Chulinho", AnimalGenero.FEMEA, Porte.GRANDE, false, true, true, ong11, AnimalStatus.DISPONIVEL, raca5);
		animal5.getDoencas().addAll(Arrays.asList(doenca6));
		animalRepository.saveAll(Arrays.asList(animal1,animal2,animal3,animal4,animal5));
		
	}

}
