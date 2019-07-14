package com.adotaPet.api.service;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.adotaPet.api.service.exceptions.FileException;

@Service
public class UploadService {


	public URI uploadFile(MultipartFile multipartFile) {
		try {
			String caminho = "C:\\tcc2\\ong\\img\\";
			
			byte[] bytes = multipartFile.getBytes();
            Path path = Paths.get(caminho + multipartFile.getOriginalFilename());            
			return  Files.write(path, bytes).toUri();
		} catch (IOException e) {
			throw new FileException("Erro ao converter URL para URI");
		}
	}

}
