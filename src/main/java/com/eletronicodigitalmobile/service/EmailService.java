package com.eletronicodigitalmobile.service;

import org.springframework.mail.SimpleMailMessage;

import com.eletronicodigitalmobile.domain.Pedido;

public interface EmailService {
	
	void sendOrderConfirmationEmail(Pedido obj);
	
	void sendEmail(SimpleMailMessage msg );
	
		
}
