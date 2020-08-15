package br.com.inaciojr9.businessapi.service.impl;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.inaciojr9.businessapi.model.FormaDeRecebimento;
import br.com.inaciojr9.businessapi.repository.FormaDeRecebimentoRepository;
import br.com.inaciojr9.businessapi.service.FormaDeRecebimentoService;

@Service
public class FormaDeRecebimentoServiceImpl implements FormaDeRecebimentoService {

	private static final Logger log = LoggerFactory.getLogger(FormaDeRecebimentoServiceImpl.class);

	@Autowired
	private FormaDeRecebimentoRepository formaDeRecebimentoRepository;
	
	@Override
	public Optional<FormaDeRecebimento> buscarPorId(Long id) {
		log.info("Buscando formaDeRecebimento pelo IDl {}", id);
		return this.formaDeRecebimentoRepository.findById(id);
	}

	@Override
	public FormaDeRecebimento persistir(FormaDeRecebimento formaDeRecebimento) {
		log.info("Persistindo formaDeRecebimento: {}", formaDeRecebimento);
		return this.formaDeRecebimentoRepository.save(formaDeRecebimento);
	}

	@Override
	public List<FormaDeRecebimento> buscarTodos() {
		return this.formaDeRecebimentoRepository.findAll();
	}

}
