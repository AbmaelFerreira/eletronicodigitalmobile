package com.eletronicodigitalmobile.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.eletronicodigitalmobile.domain.Cliente;
import com.eletronicodigitalmobile.dto.ClienteDTO;
import com.eletronicodigitalmobile.repositories.ClienteRepository;
import com.eletronicodigitalmobile.service.exceptions.DateIntegratyException;
import com.eletronicodigitalmobile.service.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {
	
	//Injeção de dependencia
	@Autowired
	private ClienteRepository repo;
	
	
	//Pesquisa por ID
	public Cliente find(Integer id) { 
		Optional<Cliente> obj = repo.findById(id); 
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName()));
    } 
	

	//Atualização
	public Cliente update(Cliente obj) {
		Cliente newObj = find(obj.getId());
		updateDate(newObj,obj);
		return repo.save(newObj);
	}
	
	
	//Exclusão
	public void delete(Integer id) {
		find(id);
		try {
			repo.deleteById(id); //Conforme atualização do material de apoio
		} catch (DataIntegrityViolationException e) {
			throw new  DateIntegratyException("Não é possível excluir, pois ha entidades relacionadas");
		}
	}
	
	
	//Pesquisa TUDO
	public List<Cliente> findAll(){
		return repo.findAll();
	}
	
	
	//Pesquisa por pagina
	public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest =  PageRequest.of(page, linesPerPage,Direction.valueOf(direction), orderBy );
		return repo.findAll(pageRequest);
	} 	
	
	
	
	//Metodo auxiliar que estancia uma Cliente apartir de um DTO  
	public Cliente fromDTO(ClienteDTO objDTO) {
		
		return new Cliente(objDTO.getId(), objDTO.getNome(), objDTO.getEmail(), null, null);
	}
	
	private void updateDate(Cliente newObj,Cliente obj) {
		newObj.setNome(obj.getNome());
		newObj.setEmail(obj.getEmail());;
	}

}
