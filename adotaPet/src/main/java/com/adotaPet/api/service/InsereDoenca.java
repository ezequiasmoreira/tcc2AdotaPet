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

import com.adotaPet.api.domain.Doenca;

@Service
public class InsereDoenca {
	
	public List<Doenca> insert() throws IOException {		
		final String dir = System.getProperty("user.dir");
		List<Doenca> listaDoenca = new ArrayList<Doenca>();
		
		File file = new File(dir+ "\\datasets\\doencas.txt");
		BufferedReader br = new BufferedReader(new FileReader(file));
		String st;
		int cont = 1;
		while((st = br.readLine()) != null) {
			
			listaDoenca.add(new Doenca(null,cont++,st));
			
		}
		return listaDoenca;
		
	}

}
