package br.com.inaciojr9.businessapi.service.impl;

import java.math.BigDecimal;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.inaciojr9.businessapi.model.Empresa;
import br.com.inaciojr9.businessapi.model.GestaoMensal;
import br.com.inaciojr9.businessapi.repository.GestaoMensalRepository;
import br.com.inaciojr9.businessapi.service.GestaoMensalService;

@Service
public class GestaoMensalServiceImpl implements GestaoMensalService {
	
	private static final Logger log = LoggerFactory.getLogger(GestaoMensalServiceImpl.class);

	@Autowired
	private GestaoMensalRepository gestaoMensalRepository;
	
	public GestaoMensal persistir(Empresa empresa, GestaoMensal gestaoMensal) {
		log.info("Persistindo gestao mensal: {}", gestaoMensal);
		return this.gestaoMensalRepository.save(gestaoMensal);
	}
	
	public Optional<GestaoMensal> buscarPorAnoMes(Empresa empresa, Integer ano, Integer mes) {
		log.info("Buscando gestão mensal por ano {} e mês {}", ano, mes);
		return Optional.ofNullable(this.gestaoMensalRepository.findByEmpresaAndAnoAndMes(empresa, ano, mes));
	}
	
	public Optional<GestaoMensal> buscarPorId(Empresa empresa, Long id) {
		log.info("Buscando gestão mensal pelo ID {}", id);
		return this.gestaoMensalRepository.findById(id);
	}
	
	public BigDecimal calcularReceitaServico(Empresa empresa, Integer ano, Integer mes) {
		// select atendimento_servico
		//select sum(valor) from atendimento where data >= '2020-08-01 00:00:00' and data <= '2020-08-30 23:59:59' ;
		// select sum(valor) from atendimento where data >= '1111-11-01 00:00:00' and data <= '1111-11-30 23:59:59' ;

		return new BigDecimal(0);
	}

}
