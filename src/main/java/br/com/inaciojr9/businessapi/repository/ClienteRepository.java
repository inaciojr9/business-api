package br.com.inaciojr9.businessapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import br.com.inaciojr9.businessapi.model.Cliente;
import br.com.inaciojr9.businessapi.model.Empresa;

@Transactional(readOnly = true)
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

	Cliente findByCpfAndEmpresa(String cpf, Empresa empresa);
	
	Cliente findByEmail(String email);
	
	Cliente findByCpfOrEmail(String cpf, String email);
}
