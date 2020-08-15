package br.com.inaciojr9.businessapi.service;
import java.util.Optional;

import br.com.inaciojr9.businessapi.model.Cliente;
import br.com.inaciojr9.businessapi.model.Empresa;

public interface ClienteService {
	
	/**
	 * Persiste um cliente na base de dados.
	 * 
	 * @param cliente
	 * @return Cliente
	 */
	Cliente persistir(Empresa empresa, Cliente cliente);
	
	/**
	 * Busca e retorna um funcionário dado um CPF.
	 * 
	 * @param cpf
	 * @return Optional<Cliente>
	 */
	Optional<Cliente> buscarPorCpf(Empresa empresa, String cpf);
	
	/**
	 * Busca e retorna um funcionário dado um email.
	 * 
	 * @param email
	 * @return Optional<Cliente>
	 */
	Optional<Cliente> buscarPorEmail(Empresa empresa, String email);
	
	/**
	 * Busca e retorna um funcionário por ID.
	 * 
	 * @param id
	 * @return Optional<Cliente>
	 */
	Optional<Cliente> buscarPorId(Empresa empresa, Long id);

}
