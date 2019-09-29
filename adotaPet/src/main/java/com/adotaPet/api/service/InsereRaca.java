package com.adotaPet.api.service;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

import com.adotaPet.api.domain.Raca;
import com.adotaPet.api.domain.enums.Especie;

@Service
public class InsereRaca {
	
	public List<Raca> insertCaes() throws IOException {		
		final String dir = System.getProperty("user.dir");
		List<Raca> listaRaca = new ArrayList<Raca>();		
		
		File file = new File(dir+ "\\datasets\\caes.txt");
		BufferedReader br = new BufferedReader(new FileReader(file));
		String st;
		int cont = 1;
		while((st = br.readLine()) != null) {
			
			listaRaca.add(new Raca(null,cont++,st,Especie.CAO));
			
		}
		return listaRaca;
		
	}
	
public List<Raca> insertGatos() throws IOException {		
	final String dir = System.getProperty("user.dir");
		List<Raca> listaRaca = new ArrayList<Raca>();
		
		File file = new File(dir+ "\\datasets\\gatos.txt");
		BufferedReader br = new BufferedReader(new FileReader(file));
		String st;
		int cont = 179;
		while((st = br.readLine()) != null) {			
			listaRaca.add(new Raca(null,cont++,st,Especie.GATO));			
		}
		return listaRaca;
		
	}

}
