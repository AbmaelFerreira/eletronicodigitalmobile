package com.eletronicodigitalmobile.repositories;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eletronicodigitalmobile.domain.Cliente;
import com.eletronicodigitalmobile.domain.Pedido;

@Repository
public interface PedidoRepository  extends JpaRepository<Pedido, Integer> {
	
	
	Page<Pedido>findByCliente(Cliente cliente, Pageable PageRequest);

}
