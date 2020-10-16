package com.eletronicodigitalmobile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.eletronicodigitalmobile.service.S3Service;

@SpringBootApplication
public class EletronicodigitalmobileApplication  implements CommandLineRunner{
	
	@Autowired
	private S3Service s3service;

	
	
	public static void main(String[] args) {
		SpringApplication.run(EletronicodigitalmobileApplication.class, args);
	}
	
	

	@Override
	public void run(String... args) throws Exception {
		
		
		s3service.upLoadFile("C:\\temp\\vialactea.jpg");
	}

}
