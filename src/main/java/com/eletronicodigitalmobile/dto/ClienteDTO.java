package com.eletronicodigitalmobile.dto;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import com.eletronicodigitalmobile.domain.Cliente;
import com.eletronicodigitalmobile.service.validation.ClienteUpdate;

@ClienteUpdate
public class ClienteDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	
	@NotEmpty(message = " Preenchimento obrigatorio")
	@Length(min = 5, max = 120, message = " Otamanho deve ser entre 5 e 120 caracteres ")
	private String nome;
	
	@NotEmpty(message = " Preenchimento obrigatorio")
	@Email(message = " Email inválido")
	private String email;
	
	public ClienteDTO() {
	}
	
	
	
	public ClienteDTO(Cliente obj) {
		id = obj.getId();
		nome = obj.getNome();
		email = obj.getEmail();
	}



	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
}
