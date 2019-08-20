package com.adotaPet.api.service;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adotaPet.api.domain.Doenca;
import com.adotaPet.api.domain.Raca;
import com.adotaPet.api.domain.enums.Especie;
import com.adotaPet.api.repository.DoencaRepository;

@Service
public class InsereRaca {
	
	public List<Raca> insertCaes() throws IOException {		
		
		List<Raca> listaRaca = new ArrayList<Raca>();		
		
		File file = new File("C:\\Users\\ezequ\\Desktop\\caes.txt");
		BufferedReader br = new BufferedReader(new FileReader(file));
		String st;
		int cont = 1;
		while((st = br.readLine()) != null) {
			
			listaRaca.add(new Raca(null,cont++,st,Especie.CAO));
			
		}
		return listaRaca;
		
	}
	
public List<Raca> insertGatos() throws IOException {		
		
		List<Raca> listaRaca = new ArrayList<Raca>();
		
		File file = new File("C:\\Users\\ezequ\\Desktop\\gatos.txt");
		BufferedReader br = new BufferedReader(new FileReader(file));
		String st;
		int cont = 179;
		while((st = br.readLine()) != null) {			
			listaRaca.add(new Raca(null,cont++,st,Especie.GATO));			
		}
		return listaRaca;
		
	}

}
