package com.eletronicodigitalmobile.dto;

import java.io.Serializable;

import com.eletronicodigitalmobile.domain.Cidade;

public class CidadeDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	
	private String nome;

	public CidadeDTO() {
	}

	public CidadeDTO(Cidade obj) {
		setId(obj.getId());
		setNome(obj.getNome());
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

}
