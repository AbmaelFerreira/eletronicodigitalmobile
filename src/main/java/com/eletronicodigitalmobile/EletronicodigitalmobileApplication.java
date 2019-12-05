package com.eletronicodigitalmobile;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.eletronicodigitalmobile.domain.Categoria;
import com.eletronicodigitalmobile.repositories.CategoriaRepository;

@SpringBootApplication
public class EletronicodigitalmobileApplication  implements CommandLineRunner{
	
	@Autowired
	private CategoriaRepository categoriaRepository;

	public static void main(String[] args) {
		SpringApplication.run(EletronicodigitalmobileApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Categoria cat1 = new Categoria(null, "Informatica") ;
		Categoria cat2 = new Categoria(null, "Escritorio") ;
		
		categoriaRepository.saveAll(Arrays.asList(cat1, cat2));
		
	}

}
