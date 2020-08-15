package br.com.inaciojr9.businessapi.service;

import java.util.List;
import java.util.Optional;

import br.com.inaciojr9.businessapi.model.Servico;

public interface ServicoService {

	List<Servico> buscarTodos();
	
	Servico persistir(Servico servico);

	Optional<Servico> buscarPorId(Long id);
	
}
