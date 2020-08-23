package br.com.inaciojr9.businessapi.service;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import br.com.inaciojr9.businessapi.model.Atendimento;
import br.com.inaciojr9.businessapi.model.Empresa;

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
	Optional<Atendimento> buscarPorId(Empresa empresa, Long id);
	
	/**
	 * Buscar os atendimentos de um mes/ano
	 * @param ano
	 * @param mes
	 * @return
	 */
	List<Atendimento> buscarPorMesAno(Empresa empresa, Integer ano, Integer mes);
	
	/**
	 * Persiste um atendimento na base de dados.
	 * 
	 * @param atendimento
	 * @return Atendimento
	 */
	Atendimento persistir(Empresa empresa, Atendimento atendimento);
	
	/**
	 * Remove um atendimento da base de dados.
	 * 
	 * @param id
	 */
	void remover(Long id);

	/**
	 * Retorna a soma dos valores dos atendimentos do mes, o que equivale
	 * ao total de serviços do mês porque atualmente os atendimentos apenas
	 * contemplam serviços
	 * @param empresa
	 * @param ano
	 * @param mes
	 * @return
	 */
	BigDecimal getValorTotalDosAtendimentosDeUmAnoMes(Empresa empresa, Integer ano, Integer mes);
	
}
