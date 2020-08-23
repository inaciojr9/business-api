package br.com.inaciojr9.businessapi.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
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
import br.com.inaciojr9.businessapi.model.AtendimentoServico;
import br.com.inaciojr9.businessapi.model.Empresa;
import br.com.inaciojr9.businessapi.repository.AtendimentoRepository;
import br.com.inaciojr9.businessapi.service.AtendimentoService;
import br.com.inaciojr9.businessapi.service.AtendimentoServicoService;

@Service
public class AtendimentoServiceImpl implements AtendimentoService {

	private static final Logger log = LoggerFactory.getLogger(AtendimentoServiceImpl.class);

	@Autowired
	private AtendimentoRepository atendimentoRepository;
	
	@Autowired
	private AtendimentoServicoService atendimentoServicoService;

	public Page<Atendimento> buscarPorClienteId(Long clienteId, PageRequest pageRequest) {
		log.info("Buscando atendimentos para o cliente ID {}", clienteId);
		return this.atendimentoRepository.findByClienteId(clienteId, pageRequest);
	}
	
	@Cacheable("atendimentoPorId")
	public Optional<Atendimento> buscarPorId(Empresa empresa, Long id) {
		log.info("Buscando um atendimento pelo ID {}", id);
		Optional<Atendimento> atendimento = this.atendimentoRepository.findById(id);
		List<AtendimentoServico> servicos = this.atendimentoServicoService.buscarPorAtendimentoId(id);
		atendimento.get().setServicos(servicos);
		return atendimento;
	}
	
	@Override
	public List<Atendimento> buscarPorMesAno(Empresa empresa, Integer ano, Integer mes) {
		log.info("Buscando os atendimentos por ano {} e mes {}", ano, mes);
		return this.atendimentoRepository.getAllPorAnoEMes(ano, mes, empresa.getId());
	}
	
	@Override
	public BigDecimal getValorTotalDosAtendimentosDeUmAnoMes(Empresa empresa, Integer ano, Integer mes) {
		BigDecimal valorTotal = new BigDecimal(0);
		List<Atendimento> atendimentos = buscarPorMesAno(empresa, ano, mes);
		if(atendimentos != null && !atendimentos.isEmpty()) {
			for(Atendimento atendimento:atendimentos) {
				valorTotal = valorTotal.add(atendimento.getValor());
			}
		}
		return valorTotal;
	}
	
	@CachePut("atendimentoPorId")
	public Atendimento persistir(Empresa empresa, Atendimento atendimento) {
		log.info("Persistindo o atendimento: {}", atendimento);
		BigDecimal valorTotal = new BigDecimal(0);
		atendimento.setValor(valorTotal);
		Atendimento atendimentoPersistido = this.atendimentoRepository.save(atendimento);
		List<AtendimentoServico> servicos = atendimentoPersistido.getServicos();
		if(servicos != null && !servicos.isEmpty()) {
			List<AtendimentoServico> servicosPersistidos = new ArrayList<>();
			
			for(AtendimentoServico servico : servicos) {
				servico.setAtendimentoId(atendimento.getId());
				AtendimentoServico servicoPersistido = atendimentoServicoService.persistir(servico);
				valorTotal = valorTotal.add(servicoPersistido.getValor());
				servicosPersistidos.add(servicoPersistido);
			}
			atendimentoPersistido.setServicos(servicosPersistidos);
			atendimentoPersistido.setValor(valorTotal);
			this.atendimentoRepository.save(atendimentoPersistido);
		}
		return atendimentoPersistido;
	}
		 
	
	public void remover(Long id) {
		log.info("Removendo o atendimento ID {}", id);
		List<AtendimentoServico> servicos = this.atendimentoServicoService.buscarPorAtendimentoId(id);
		for(AtendimentoServico servico:servicos) {
			this.atendimentoServicoService.remover(servico.getId());
		}
		this.atendimentoRepository.deleteById(id);
		
	}
	
	
	 

}
