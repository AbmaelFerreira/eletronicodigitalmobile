package com.eletronicodigitalmobile.config;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.eletronicodigitalmobile.service.DbService;
import com.eletronicodigitalmobile.service.EmailService;
import com.eletronicodigitalmobile.service.MockEmailService;

@Configuration
@Profile("test")
public class TesteConfig {
	
	@Autowired
	private DbService dbservice;
	
	@Bean
	public boolean instantiateDatabase() throws ParseException {
		dbservice.instantiateDatabase();
		return true;
	}
	
	@Bean
	public EmailService emailservice() {
		return new MockEmailService();
	}

}
