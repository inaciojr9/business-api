package br.com.inaciojr9.businessapi.service.impl;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import br.com.inaciojr9.businessapi.model.Atendimento;
import br.com.inaciojr9.businessapi.repository.AtendimentoRepository;
import br.com.inaciojr9.businessapi.service.AtendimentoService;

@Service
public class AtendimentoServiceImpl implements AtendimentoService {

	private static final Logger log = LoggerFactory.getLogger(AtendimentoServiceImpl.class);

	@Autowired
	private AtendimentoRepository atendimentoRepository;

	public Page<Atendimento> buscarPorClienteId(Long clienteId, PageRequest pageRequest) {
		log.info("Buscando atendimentos para o cliente ID {}", clienteId);
		return this.atendimentoRepository.findByClienteId(clienteId, pageRequest);
	}
	
	@Cacheable("atendimentoPorId")
	public Optional<Atendimento> buscarPorId(Long id) {
		log.info("Buscando um atendimento pelo ID {}", id);
		return this.atendimentoRepository.findById(id);
	}
	
	@CachePut("atendimentoPorId")
	public Atendimento persistir(Atendimento atendimento) {
		log.info("Persistindo o atendimento: {}", atendimento);
		return this.atendimentoRepository.save(atendimento);
	}
	
	public void remover(Long id) {
		log.info("Removendo o atendimento ID {}", id);
		this.atendimentoRepository.deleteById(id);
	}

}
