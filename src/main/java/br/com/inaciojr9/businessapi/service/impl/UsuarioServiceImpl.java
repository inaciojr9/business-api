package br.com.inaciojr9.businessapi.service.impl;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.inaciojr9.businessapi.model.Usuario;
import br.com.inaciojr9.businessapi.repository.UsuarioRepository;
import br.com.inaciojr9.businessapi.service.UsuarioService;

@Service
public class UsuarioServiceImpl implements UsuarioService {

	private static final Logger log = LoggerFactory.getLogger(UsuarioServiceImpl.class);

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Override
	public Optional<Usuario> buscarPorEmail(String email) {
		log.info("Buscando um usuario para o email {}", email);
		return Optional.ofNullable(this.usuarioRepository.findByEmail(email));
	}

}
