package com.eletronicodigitalmobile;

import java.util.ArrayList;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.eletronicodigitalmobile.domain.Categoria;
import com.eletronicodigitalmobile.domain.Cidade;
import com.eletronicodigitalmobile.domain.Cliente;
import com.eletronicodigitalmobile.domain.Endereco;
import com.eletronicodigitalmobile.domain.Estado;
import com.eletronicodigitalmobile.domain.Produto;
import com.eletronicodigitalmobile.domain.enums.TipoCliente;
import com.eletronicodigitalmobile.repositories.CategoriaRepository;
import com.eletronicodigitalmobile.repositories.CidadeRepository;
import com.eletronicodigitalmobile.repositories.ClienteRepository;
import com.eletronicodigitalmobile.repositories.EnderecoRepository;
import com.eletronicodigitalmobile.repositories.EstadoRepository;
import com.eletronicodigitalmobile.repositories.ProdutoRepository;

@SpringBootApplication
public class EletronicodigitalmobileApplication  implements CommandLineRunner{
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private EstadoRepository estadoRepository;
	
	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private EnderecoRepository enderecoRepository;
	

	public static void main(String[] args) {
		SpringApplication.run(EletronicodigitalmobileApplication.class, args);
	}
	
	

	@Override
	public void run(String... args) throws Exception {
		
		Categoria cat1 = new Categoria(null, "Informatica") ;
		Categoria cat2 = new Categoria(null, "Escritorio") ;
		
		Produto p1 = new Produto(null,"Computador", 2000.00);
		Produto p2 = new Produto(null,"Impressora", 800.00);
		Produto p3 = new Produto(null,"Mouse", 80.00);
		
		cat1.getProdutos().addAll(Arrays.asList(p1,p2,p3));
		cat2.getProdutos().addAll(Arrays.asList(p2));
		
		p1.getCategorias().addAll(Arrays.asList(cat1));
		p2.getCategorias().addAll(Arrays.asList(cat1, cat2));
		p3.getCategorias().addAll(Arrays.asList(cat1));
		
		Estado est1 = new Estado(null,"Minas Gerais");
		Estado est2 = new Estado(null,"São Paulo");
		
		Cidade c1 = new Cidade(null,"Uberlandia", est1);
		Cidade c2 = new Cidade(null,"São Paulo", est2);
		Cidade c3 = new Cidade(null,"Campinas", est2);
		
		est1.getCidades().addAll(Arrays.asList(c1));
		est2.getCidades().addAll(Arrays.asList(c2,c3));
		
		categoriaRepository.saveAll(Arrays.asList(cat1, cat2));
		produtoRepository.saveAll(Arrays.asList(p1,p2,p3));
		
		estadoRepository.saveAll(Arrays.asList(est1, est2));
		cidadeRepository.saveAll(Arrays.asList(c1,c2,c3));
		
		Cliente cli1 = new Cliente(null, "Maria da Silva", "maria@gmail.com", "123456789", TipoCliente.PESSOAFISICA);
		cli1.getTelefones().addAll(Arrays.asList("65-9955888", "65-965654520"));
		
		Endereco e1 = new Endereco(null, "Rua Flores", "3", "Quadra 1", "Jardim Maringa", "78120460", cli1, c1);
		Endereco e2 = new Endereco(null, "Av Matos", "105", "Sala 800", "Centro", "12345678", cli1, c2);
		
		
		clienteRepository.saveAll(Arrays.asList(cli1));
		enderecoRepository.saveAll(Arrays.asList(e1,e2));
	}

}
