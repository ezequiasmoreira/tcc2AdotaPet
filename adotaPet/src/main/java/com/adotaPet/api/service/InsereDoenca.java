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
import com.adotaPet.api.repository.DoencaRepository;

@Service
public class InsereDoenca {
	
	@Autowired
	private DoencaRepository doencaRepository;
	
	public List<Doenca> insert() throws IOException {		
		
		List<Doenca> listaDoenca = new ArrayList<Doenca>();
		
		//Doenca doenca1 = new Doenca(null,1,"Alergia alimentar");
		
		File file = new File("C:\\Users\\ezequ\\Desktop\\doecas.txt");
		BufferedReader br = new BufferedReader(new FileReader(file));
		String st;
		int cont = 1;
		while((st = br.readLine()) != null) {
			
			listaDoenca.add(new Doenca(null,cont++,st));
			
		}
		return listaDoenca;
		
	}

}
