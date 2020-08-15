package br.com.inaciojr9.businessapi.service.impl;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.inaciojr9.businessapi.model.Cliente;
import br.com.inaciojr9.businessapi.model.Empresa;
import br.com.inaciojr9.businessapi.repository.ClienteRepository;
import br.com.inaciojr9.businessapi.service.ClienteService;

@Service
public class ClienteServiceImpl implements ClienteService {
	
	private static final Logger log = LoggerFactory.getLogger(ClienteServiceImpl.class);

	@Autowired
	private ClienteRepository clienteRepository;
	
	public Cliente persistir(Empresa empresa, Cliente cliente) {
		log.info("Persistindo funcionário: {}", cliente);
		return this.clienteRepository.save(cliente);
	}
	
	public Optional<Cliente> buscarPorCpf(Empresa empresa, String cpf) {
		log.info("Buscando cliente pelo CPF {}", cpf);
		return Optional.ofNullable(this.clienteRepository.findByCpfAndEmpresa(cpf, empresa));
	}
	
	public Optional<Cliente> buscarPorEmail(Empresa empresa, String email) {
		log.info("Buscando cliente pelo email {}", email);
		return Optional.ofNullable(this.clienteRepository.findByEmail(email));
	}
	
	public Optional<Cliente> buscarPorId(Empresa empresa, Long id) {
		log.info("Buscando cliente pelo IDl {}", id);
		return this.clienteRepository.findById(id);
	}

}
