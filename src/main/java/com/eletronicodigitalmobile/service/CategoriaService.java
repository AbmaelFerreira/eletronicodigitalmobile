package com.eletronicodigitalmobile.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.eletronicodigitalmobile.domain.Categoria;
import com.eletronicodigitalmobile.dto.CategoriaDTO;
import com.eletronicodigitalmobile.repositories.CategoriaRepository;
import com.eletronicodigitalmobile.service.exceptions.DateIntegratyException;
import com.eletronicodigitalmobile.service.exceptions.ObjectNotFoundException;

@Service
public class CategoriaService {
	
	@Autowired
	private CategoriaRepository repo;
	
	//Busca categoria por Id 
	public Categoria find(Integer id) { 
		Optional<Categoria> obj = repo.findById(id); 
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Categoria.class.getName()));
    } 
	
	
	//Insere uma nova categoria
	public Categoria insert(Categoria obj) {
		obj.setId(null);
		return repo.save(obj);
	}
	

	
	//Atualização
	public Categoria update(Categoria obj) {
		Categoria newObj = find(obj.getId());
		updateDate(newObj,obj);
		return repo.save(newObj);
	}
	
	
	//Exclui uma categoria
	public void delete(Integer id) {
		find(id);
		try {
			repo.deleteById(id); //Conforme atualização do material de apoio
		} catch (DataIntegrityViolationException e) {
			throw new  DateIntegratyException("Não é possível excluir uma categoria que possue produtos");
		}
	}
	
	
	//Busca todas as categorias
	public List<Categoria> findAll(){
		return repo.findAll();
	}
	
	
	//Busca as categorias por pagina
	public Page<Categoria> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest =  PageRequest.of(page, linesPerPage,Direction.valueOf(direction), orderBy );
		return repo.findAll(pageRequest);
	} 	
	
	
	//Metodo auxiliar que estancia uma Categoria apartir de um DTO
	public Categoria fromDTO(CategoriaDTO objDTO) {
		return new Categoria(objDTO.getId(), objDTO.getNome());
	}
	
	
	//Atualiza os dados de uma Categoria
	private void updateDate(Categoria newObj,Categoria obj) {
		newObj.setNome(obj.getNome());
	}

	
}
