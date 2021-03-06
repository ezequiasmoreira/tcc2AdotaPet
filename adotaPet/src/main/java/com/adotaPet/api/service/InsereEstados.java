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

import com.adotaPet.api.domain.Estado;

@Service
public class InsereEstados {
	
	public List<Estado> buscaEstadosDataSet() throws IOException {		
		final String dir = System.getProperty("user.dir");
        System.out.println("current dir = " + dir);
        
        List<Estado> listaEstados = new ArrayList<Estado>();
		
		File file = new File(dir+ "\\datasets\\estados.txt");
		BufferedReader br = new BufferedReader(new FileReader(file));
        String st;
        int cont = 1;
		while((st = br.readLine()) != null) {

            listaEstados.add(new Estado(null,st,cont++));

		}
		return listaEstados;
	}

}
