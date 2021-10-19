package com.eletronicodigitalmobile.config;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.eletronicodigitalmobile.service.DbService;
import com.eletronicodigitalmobile.service.EmailService;
import com.eletronicodigitalmobile.service.SmtpEmailService;

@Configuration
@Profile("prod")
public class ProdConfig {
	@Autowired
	private DbService dbservice;
	
	@Value("${spring.jpa.hibernate.ddl-auto}")
	private String strategy;
	
	@Bean
	public boolean instantiateDatabase() throws ParseException {
		if (!"create".equals(strategy)) {
			return false;
		}
		dbservice.instantiateDatabase();
		return true;
	}
	
	@Bean
	public EmailService emailservice() {
		return new SmtpEmailService();
	}

}
