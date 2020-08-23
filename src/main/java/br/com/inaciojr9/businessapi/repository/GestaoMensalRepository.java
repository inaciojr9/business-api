package br.com.inaciojr9.businessapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import br.com.inaciojr9.businessapi.model.Empresa;
import br.com.inaciojr9.businessapi.model.GestaoMensal;

@Transactional(readOnly = true)
public interface GestaoMensalRepository extends JpaRepository<GestaoMensal, Long> {

	GestaoMensal findByEmpresaAndAnoAndMes(Empresa empresa, Integer ano, Integer mes);
	
	
}
