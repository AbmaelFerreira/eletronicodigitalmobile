package com.eletronicodigitalmobile.service;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.eletronicodigitalmobile.domain.Cliente;
import com.eletronicodigitalmobile.repositories.ClienteRepository;
import com.eletronicodigitalmobile.service.exceptions.ObjectNotFoundException;

@Service
public class AuthService {
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private BCryptPasswordEncoder pe;
	
	@Autowired
	private EmailService emailService;
	
	private Random rand = new Random();
	
	
	
	public void sendNewPassword(String email) {
		
		Cliente cliente = clienteRepository.findByEmail(email);
		
		if(cliente == null) {
			throw new ObjectNotFoundException("Email não encontrado");
		}
		
		String newPass = newPassword();
		
		cliente.setSenha(pe.encode(newPass));
		clienteRepository.save(cliente);
		
		emailService.sendNewPasswordEmail(cliente, newPass);
		
	}


	private String newPassword() {
		
		char[] vet = new char[10];
		
		for(int i = 0; i < 10;i++) {
			
			vet[i] = rondomChar();
		}
		return new String (vet);

	}


	private char rondomChar() {
		
int opt = rand.nextInt(3);
		
		if(opt == 0) {
			
			return (char) (rand.nextInt(10) + 48);
		}
		else if(opt == 1){
			
			
			return (char) (rand.nextInt(26) + 65);
			
		}
		else {
			
			return (char) (rand.nextInt(26) + 65);
			
		}

	}

}
