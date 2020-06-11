package br.com.inaciojr9.businessapi.service.impl;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.inaciojr9.businessapi.model.Cliente;
import br.com.inaciojr9.businessapi.repository.ClienteRepository;
import br.com.inaciojr9.businessapi.service.ClienteService;

@Service
public class ClienteServiceImpl implements ClienteService {
	
	private static final Logger log = LoggerFactory.getLogger(ClienteServiceImpl.class);

	@Autowired
	private ClienteRepository clienteRepository;
	
	public Cliente persistir(Cliente cliente) {
		log.info("Persistindo funcionário: {}", cliente);
		return this.clienteRepository.save(cliente);
	}
	
	public Optional<Cliente> buscarPorCpf(String cpf) {
		log.info("Buscando funcionário pelo CPF {}", cpf);
		return Optional.ofNullable(this.clienteRepository.findByCpf(cpf));
	}
	
	public Optional<Cliente> buscarPorEmail(String email) {
		log.info("Buscando funcionário pelo email {}", email);
		return Optional.ofNullable(this.clienteRepository.findByEmail(email));
	}
	
	public Optional<Cliente> buscarPorId(Long id) {
		log.info("Buscando funcionário pelo IDl {}", id);
		return this.clienteRepository.findById(id);
	}

}
