package com.eletronicodigitalmobile.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.eletronicodigitalmobile.domain.Categoria;
import com.eletronicodigitalmobile.repositories.CategoriaRepository;
import com.eletronicodigitalmobile.service.exceptions.DateIntegratyException;
import com.eletronicodigitalmobile.service.exceptions.ObjectNotFoundException;

@Service
public class CategoriaService {
	
	@Autowired
	private CategoriaRepository repo;
	
	
	
	public Categoria find(Integer id) { 
		Optional<Categoria> obj = repo.findById(id); 
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Categoria.class.getName()));
    } 
	
	
	
	public Categoria insert(Categoria obj) {
		obj.setId(null);
		return repo.save(obj);
	}
	
	
	
	public Categoria update(Categoria obj) {
		find(obj.getId());
		return repo.save(obj);
	}
	
	
	
	public void delete(Integer id) {
		find(id);
		try {
			repo.deleteById(id); //Conforme atualização do material de apoio
		} catch (DataIntegrityViolationException e) {
			throw new  DateIntegratyException("Não é possível excluir uma categoria que possue produtos");
		}
	}
	
	public List<Categoria> findAll(){
		return repo.findAll();
	}
}
