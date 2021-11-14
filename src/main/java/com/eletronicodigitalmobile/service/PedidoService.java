package com.eletronicodigitalmobile.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.eletronicodigitalmobile.domain.Cliente;
import com.eletronicodigitalmobile.domain.ItemPedido;
import com.eletronicodigitalmobile.domain.PagamentoComBoleto;
import com.eletronicodigitalmobile.domain.Pedido;
import com.eletronicodigitalmobile.domain.enums.EstadoPagamento;
import com.eletronicodigitalmobile.repositories.ItemPedidoRepository;
import com.eletronicodigitalmobile.repositories.PagamentoRepository;
import com.eletronicodigitalmobile.repositories.PedidoRepository;
import com.eletronicodigitalmobile.security.UserSS;
import com.eletronicodigitalmobile.service.exceptions.AuthorizationException;
import com.eletronicodigitalmobile.service.exceptions.ObjectNotFoundException;

@Service
public class PedidoService {
	
	//
	@Autowired
	private PedidoRepository repo;
	
	@Autowired
	private BoletoService boletoService;
	
	@Autowired
	private PagamentoRepository pagamentoRepository;
	
	@Autowired
	private ProdutoService produtoservice;
	
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;
	
	@Autowired
	private ClienteService clienteservice;
	
	@Autowired
	private EmailService emailService;
	
	
	//Metodo que busca por id
	public Pedido find(Integer id) { 
		Optional<Pedido> obj = repo.findById(id); 
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Pedido.class.getName()));
	} 
	
	//Metodo que busca todos os pedidos
	public List<Pedido> findAll() { 
		List<Pedido> lista = repo.findAll(); 
		return lista;
	} 
	
	
	//Metodo que insere um novo pedido
	public Pedido insert(Pedido obj) {
		obj.setId(null);
		obj.setInstante(new Date());
		obj.setCliente(clienteservice.find(obj.getCliente().getId()));
		obj.getPagamento().setEstado(EstadoPagamento.PENDENTE);
		obj.getPagamento().setPedido(obj);
		
			if(obj.getPagamento() instanceof PagamentoComBoleto) {
				PagamentoComBoleto pagto =  (PagamentoComBoleto) obj.getPagamento();
				boletoService.preencherPagamentoComBoleto(pagto, obj.getInstante());
			}
		
		
		
		obj = repo.save(obj);
		pagamentoRepository.save(obj.getPagamento());
			for(ItemPedido ip : obj.getItens()) {
				ip.setDesconto(0.0);
				ip.setProduto(produtoservice.find(ip.getProduto().getId()));
				ip.setPreco(produtoservice.find(ip.getProduto().getId()).getPreco());
				ip.setPedido(obj);
			}
		itemPedidoRepository.saveAll(obj.getItens());
		emailService.sendOrderConfirmationHtmlEmail(obj);
		return obj;
	}
	
	
	//Busca as Pedidos por pagina
		public Page<Pedido> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
			
			UserSS user = UserService.authenticated();
			
			if(user == null) {
				throw new AuthorizationException("Acesso Negado");
			}
			
			PageRequest pageRequest =  PageRequest.of(page, linesPerPage,Direction.valueOf(direction), orderBy );
			
			Cliente cliente = clienteservice.find(user.getId());
			
			
			
			return repo.findByCliente(cliente, pageRequest);
		} 	
}
