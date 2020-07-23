package br.com.inaciojr9.businessapi.service;

import java.util.Optional;

import br.com.inaciojr9.businessapi.model.Usuario;

public interface UsuarioService {

	
	/**
	 * Busca e retorna um usuario dado um email.
	 * 
	 * @param email
	 * @return Optional<Usuario>
	 */
	Optional<Usuario> buscarPorEmail(String email);
	
}
