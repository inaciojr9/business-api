package br.com.inaciojr9.businessapi.service.impl;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.inaciojr9.businessapi.model.Servico;
import br.com.inaciojr9.businessapi.repository.ServicoRepository;
import br.com.inaciojr9.businessapi.service.ServicoService;

@Service
public class ServicoServiceImpl implements ServicoService {

	private static final Logger log = LoggerFactory.getLogger(ServicoServiceImpl.class);

	@Autowired
	private ServicoRepository servicoRepository;
	
	@Override
	public Optional<Servico> buscarPorId(Long id) {
		log.info("Buscando servico pelo IDl {}", id);
		return this.servicoRepository.findById(id);
	}

	@Override
	public Servico persistir(Servico servico) {
		log.info("Persistindo servico: {}", servico);
		return this.servicoRepository.save(servico);
	}

	@Override
	public List<Servico> buscarTodos() {
		return this.servicoRepository.findAll();
	}

}
