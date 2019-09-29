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

import com.adotaPet.api.domain.Cidade;
import com.adotaPet.api.repository.CidadeRepository;

@Service
public class InsereCidades {
	
	public List<String> buscaCidadesDataSet() throws IOException {		
		final String dir = System.getProperty("user.dir");
        
        List<String> linhasArquivo = new ArrayList<String>();
		
		File file = new File(dir+ "\\datasets\\cidades.txt");
		BufferedReader br = new BufferedReader(new FileReader(file));
        String st;
		while((st = br.readLine()) != null) {
            linhasArquivo.add(st);
		}
		return linhasArquivo;
	}

}
