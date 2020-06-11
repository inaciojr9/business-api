package br.com.inaciojr9.businessapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import br.com.inaciojr9.businessapi.model.Cliente;

@Transactional(readOnly = true)
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

	Cliente findByCpf(String cpf);
	
	Cliente findByEmail(String email);
	
	Cliente findByCpfOrEmail(String cpf, String email);
}
