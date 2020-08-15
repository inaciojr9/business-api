package br.com.inaciojr9.businessapi.service;

import java.util.List;
import java.util.Optional;

import br.com.inaciojr9.businessapi.model.FormaDeRecebimento;

public interface FormaDeRecebimentoService {

	List<FormaDeRecebimento> buscarTodos();
	
	FormaDeRecebimento persistir(FormaDeRecebimento formaDeRecebimento);

	Optional<FormaDeRecebimento> buscarPorId(Long id);
	
}
