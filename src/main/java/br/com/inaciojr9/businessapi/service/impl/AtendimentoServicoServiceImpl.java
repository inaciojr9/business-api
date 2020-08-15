package br.com.inaciojr9.businessapi.service.impl;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import br.com.inaciojr9.businessapi.model.AtendimentoServico;
import br.com.inaciojr9.businessapi.repository.AtendimentoServicoRepository;
import br.com.inaciojr9.businessapi.service.AtendimentoServicoService;

@Service
public class AtendimentoServicoServiceImpl implements AtendimentoServicoService {

	private static final Logger log = LoggerFactory.getLogger(AtendimentoServicoServiceImpl.class);

	@Autowired
	private AtendimentoServicoRepository atendimentoServicoRepository;

	public List<AtendimentoServico> buscarPorAtendimentoId(Long atendimentoId) {
		log.info("Buscando os serviços do atendimento ID {}", atendimentoId);
		return this.atendimentoServicoRepository.findByAtendimentoId(atendimentoId);
	}
	
	@Cacheable("atendimentoServicoPorId")
	public Optional<AtendimentoServico> buscarPorId(Long id) {
		log.info("Buscando um serviço de atendimento pelo ID {}", id);
		return this.atendimentoServicoRepository.findById(id);
	}
	
	@CachePut("atendimentoServicoPorId")
	public AtendimentoServico persistir(AtendimentoServico atendimentoServico) {
		log.info("Persistindo o serviço do atendimento: {}", atendimentoServico);
		return this.atendimentoServicoRepository.save(atendimentoServico);
	}
	
	public void remover(Long id) {
		log.info("Removendo o serviço do atendimento ID {}", id);
		this.atendimentoServicoRepository.deleteById(id);
	}

}
