package br.com.inaciojr9.businessapi.service;

import java.util.Optional;

import br.com.inaciojr9.businessapi.model.Empresa;
import br.com.inaciojr9.businessapi.model.GestaoMensal;

public interface GestaoMensalService {
	
	GestaoMensal persistir(Empresa empresa, GestaoMensal gestaoMensal);	
	
	Optional<GestaoMensal> buscarPorAnoMes(Empresa empresa, Integer ano, Integer mes);
	
	Optional<GestaoMensal> buscarPorId(Empresa empresa, Long id);

}
