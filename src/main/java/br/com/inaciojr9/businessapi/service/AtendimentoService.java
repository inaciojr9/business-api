package br.com.inaciojr9.businessapi.service;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import br.com.inaciojr9.businessapi.model.Atendimento;

public interface AtendimentoService {

	/**
	 * Retorna uma lista paginada de atendimentos de um determinado cliente.
	 * 
	 * @param clienteId
	 * @param pageRequest
	 * @return Page<Atendimento>
	 */
	Page<Atendimento> buscarPorClienteId(Long clienteId, PageRequest pageRequest);
	
	/**
	 * Retorna um atendimento por ID.
	 * 
	 * @param id
	 * @return Optional<Atendimento>
	 */
	Optional<Atendimento> buscarPorId(Long id);
	
	/**
	 * Persiste um atendimento na base de dados.
	 * 
	 * @param atendimento
	 * @return Atendimento
	 */
	Atendimento persistir(Atendimento atendimento);
	
	/**
	 * Remove um atendimento da base de dados.
	 * 
	 * @param id
	 */
	void remover(Long id);
	
}
