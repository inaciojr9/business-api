package br.com.inaciojr9.businessapi.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.inaciojr9.businessapi.model.Servico;

public interface ServicoRepository extends JpaRepository<Servico, Long> {
	
	

}
