package com.adotaPet.api;

import java.io.File;
import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AdotaPetApplication {

	public static void main(String[] args) {
		SpringApplication.run(AdotaPetApplication.class, args);
		final String dirAtual = System.getProperty("user.dir");
		ProcessBuilder pb = new ProcessBuilder("cmd", "/c", "servidor.cmd");
		File dir = new File(dirAtual);
		pb.directory(dir);
		try {
			Process p = pb.start();
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}
	public void run(String... args) throws Exception {
		
	}

}
