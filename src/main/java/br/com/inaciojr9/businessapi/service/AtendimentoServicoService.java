package br.com.inaciojr9.businessapi.service;
import java.util.List;
import java.util.Optional;

import br.com.inaciojr9.businessapi.model.AtendimentoServico;

public interface AtendimentoServicoService {

	List<AtendimentoServico> buscarPorAtendimentoId(Long atendimentoId);
	
	Optional<AtendimentoServico> buscarPorId(Long id);
	
	AtendimentoServico persistir(AtendimentoServico atendimentoServico);
	
	void remover(Long id);
	
}
