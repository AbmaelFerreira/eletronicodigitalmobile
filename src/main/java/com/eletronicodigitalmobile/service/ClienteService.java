package com.eletronicodigitalmobile.service;

import java.awt.image.BufferedImage;
import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.eletronicodigitalmobile.domain.Cidade;
import com.eletronicodigitalmobile.domain.Cliente;
import com.eletronicodigitalmobile.domain.Endereco;
import com.eletronicodigitalmobile.domain.enums.Perfil;
import com.eletronicodigitalmobile.domain.enums.TipoCliente;
import com.eletronicodigitalmobile.dto.ClienteDTO;
import com.eletronicodigitalmobile.dto.ClienteNewDTO;
import com.eletronicodigitalmobile.repositories.CidadeRepository;
import com.eletronicodigitalmobile.repositories.ClienteRepository;
import com.eletronicodigitalmobile.repositories.EnderecoRepository;
import com.eletronicodigitalmobile.security.UserSS;
import com.eletronicodigitalmobile.service.exceptions.AuthorizationException;
import com.eletronicodigitalmobile.service.exceptions.DateIntegratyException;
import com.eletronicodigitalmobile.service.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {

	@Autowired
	private BCryptPasswordEncoder pe;

	// Injeção de dependencia
	@Autowired
	private ClienteRepository repo;

	// Injeção de dependencia
	@Autowired
	private CidadeRepository cidaderepository;

	// Injeção de dependencia
	@Autowired
	private EnderecoRepository enderecoRepository;

	@Autowired
	private ImageService imageService;

	@Autowired
	private S3Service s3Service;

	@Value("${img.prefix.client.profile}")
	private String prefix;
	
	
	@Value("${img.profile.size}")
	private Integer size;


	// Busca por ID
	public Cliente find(Integer id) {

		UserSS user = UserService.authenticated();

		if (user == null || !user.hasRole(Perfil.ADMIN) && !id.equals(user.getId())) {
			throw new AuthorizationException("Acesso negado");
		}

		Optional<Cliente> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName()));
	}

	// Insere uma novo Cliente
	@Transactional
	public Cliente insert(Cliente obj) {
		obj.setId(null);
		obj = repo.save(obj);
		enderecoRepository.saveAll(obj.getEnderecos());
		return obj;
	}

	// Atualização
	public Cliente update(Cliente obj) {
		Cliente newObj = find(obj.getId());
		updateDate(newObj, obj);
		return repo.save(newObj);
	}

	// Exclusão
	public void delete(Integer id) {
		find(id);
		try {
			repo.deleteById(id); // Conforme atualização do material de apoio
		} catch (DataIntegrityViolationException e) {
			throw new DateIntegratyException("Não é possível excluir, pois ha entidades relacionadas");
		}
	}

	// Pesquisa TUDO
	public List<Cliente> findAll() {
		return repo.findAll();
	}

	// Pesquisa por pagina
	public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repo.findAll(pageRequest);
	}

	// Metodo auxiliar que estancia uma Cliente apartir de um DTO
	public Cliente fromDTO(ClienteDTO objDTO) {

		return new Cliente(objDTO.getId(), objDTO.getNome(), objDTO.getEmail(), null, null, null);
	}

	// Metodo auxiliar que estancia um NOVO Cliente apartir de um ClienteNewDTO
	public Cliente fromDTO(ClienteNewDTO objDTO) {

		Cliente cli = new Cliente(null, objDTO.getNome(), objDTO.getEmail(), objDTO.getCpfOuCnpj(),
				TipoCliente.toEnum(objDTO.getTipo()), pe.encode(objDTO.getSenha()));

		Cidade cid = new Cidade(objDTO.getCidadeId(), null, null);

		Endereco end = new Endereco(null, objDTO.getLogradouro(), objDTO.getNumero(), objDTO.getComplemento(),
				objDTO.getBairro(), objDTO.getCep(), cli, cid);

		cli.getEnderecos().add(end);

		cli.getTelefones().add(objDTO.getTelefone1());

		if (objDTO.getTelefone2() != null) {
			cli.getTelefones().add(objDTO.getTelefone2());
		}
		if (objDTO.getTelefone3() != null) {
			cli.getTelefones().add(objDTO.getTelefone3());
		}
		return cli;
	}

	// Atualiza os dados de um cliente
	private void updateDate(Cliente newObj, Cliente obj) {
		newObj.setNome(obj.getNome());
		newObj.setEmail(obj.getEmail());
	}

	public URI uploadProfilePincture(MultipartFile multipartFile) {

		UserSS user = UserService.authenticated();

		if (user == null) {
			throw new AuthorizationException("Acesso negado");
		}

		BufferedImage jpgImage = imageService.getJpgImageFromFile(multipartFile);
		
		jpgImage = imageService.cropSquare(jpgImage);
		jpgImage = imageService.resize(jpgImage, size);

		String fileName = prefix + user.getId() + "jpg";

		return s3Service.upLoadFile(imageService.getInputStream(jpgImage, "jpg"), fileName, "image");

	}

}
