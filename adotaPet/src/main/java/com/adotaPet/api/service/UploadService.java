package com.adotaPet.api.service;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.adotaPet.api.security.UserSS;
import com.adotaPet.api.service.exceptions.AuthorizationException;
import com.adotaPet.api.service.exceptions.FileException;

@Service
public class UploadService {

	@Autowired
	private ImageService imageService;
		
	@Value("${img.prefix.pessoa.profile}")
	private String prefix;

	@Value("${img.prefix.animal}")
	private String prefixAnimal;
	
	@Value("${img.profile.size}")
	private Integer size;
	
	public URI uploadFile(MultipartFile multipartFile) {
		try {
			String caminho = "C:\\tccf\\adotapet\\src\\assets\\imgs\\";
			
			UserSS user = UserService.authenticated();
			if (user == null) {
				throw new AuthorizationException("Acesso negado");
			}
			
			BufferedImage jpgImage = imageService.getJpgImageFromFile(multipartFile);
			jpgImage = imageService.cropSquare(jpgImage);
			jpgImage = imageService.resize(jpgImage, size);
			
			String fileName = prefix + user.getId() + ".jpg";	
			
			byte[] bytes = imageService.toByteArray(jpgImage);			
			
            Path path = Paths.get(caminho + fileName);            
			return  Files.write(path, bytes).toUri();
		} catch (IOException e) {
			throw new FileException("Erro ao converter URL para URI");
		}
	}	

	public URI uploadAnimalFile(MultipartFile multipartFile, Integer id) {
		try {

			final String dir = System.getProperty("user.dir");
        	System.out.println("current dir = " + dir);
			String caminho = dir.replace("adotaPet", "img\\animal\\");
			
			UserSS user = UserService.authenticated();
			if (user == null) {
				throw new AuthorizationException("Acesso negado");
			}
			
			BufferedImage jpgImage = imageService.getJpgImageFromFile(multipartFile);
			jpgImage = imageService.cropSquare(jpgImage);
			jpgImage = imageService.resize(jpgImage, size);
			
			String fileName = prefixAnimal + id + ".jpg";	
			
			byte[] bytes = imageService.toByteArray(jpgImage);			
			
            Path path = Paths.get(caminho + fileName);            
			return  Files.write(path, bytes).toUri();
		} catch (IOException e) {
			throw new FileException("Erro ao converter URL para URI");
		}
	}

}
