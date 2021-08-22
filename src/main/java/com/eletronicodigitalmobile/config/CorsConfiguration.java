package com.eletronicodigitalmobile.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfiguration implements WebMvcConfigurer  { 

	
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
		.allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS", "HEAD","TRACE", "CONNECT")
		.allowedOrigins("http://localhost:8100", "http://localhost:8000");  //.allowedHeaders("Access-Control-Allow-Origin", "Content-type")
			
	}

}
