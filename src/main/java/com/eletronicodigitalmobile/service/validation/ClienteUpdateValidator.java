package com.eletronicodigitalmobile.service.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerMapping;

import com.eletronicodigitalmobile.domain.Cliente;
import com.eletronicodigitalmobile.dto.ClienteDTO;
import com.eletronicodigitalmobile.repositories.ClienteRepository;
import com.eletronicodigitalmobile.resources.exception.FieldMessage; 
 
public class ClienteUpdateValidator implements ConstraintValidator<ClienteUpdate, ClienteDTO> { 
 
	@Autowired
	private HttpServletRequest request;
	
    @Override 
    public void initialize(ClienteUpdate ann) {  
    }
    
    @Autowired
    private ClienteRepository repo;
    
    @Override   
    public boolean isValid(ClienteDTO objDto, ConstraintValidatorContext context) { 
    	
    	@SuppressWarnings("unchecked")
		Map<String, String> map =(Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
    	Integer uriId = Integer.parseInt(map.get("id"));
    	
        List<FieldMessage> list = new ArrayList<>();// inclua os testes aqui, inserindo erros na lista  
        
        Cliente aux = repo.findByEmail(objDto.getEmail());
        if (aux != null && !aux.getId().equals(uriId) ) {
			list.add(new FieldMessage("email","Email já existente"));
		}
        
        for (FieldMessage e : list) {  
        	context.disableDefaultConstraintViolation();          
        	context.buildConstraintViolationWithTemplate(e.getMessage())        
        	.addPropertyNode(e.getFieldName()).addConstraintViolation();      
        	}       
        return list.isEmpty();     
        } 
    } 
 
