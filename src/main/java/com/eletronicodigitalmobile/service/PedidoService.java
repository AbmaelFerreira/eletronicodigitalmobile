package com.eletronicodigitalmobile.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eletronicodigitalmobile.domain.Pedido;
import com.eletronicodigitalmobile.repositories.PedidoRepository;
import com.eletronicodigitalmobile.service.exceptions.ObjectNotFoundException;

@Service
public class PedidoService {
	
	@Autowired
	private PedidoRepository repo;
	
	
	
	public Pedido find(Integer id) { 
		Optional<Pedido> obj = repo.findById(id); 
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Pedido.class.getName()));
	} 
	
	
}
