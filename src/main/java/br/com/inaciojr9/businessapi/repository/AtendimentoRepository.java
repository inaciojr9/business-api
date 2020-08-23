package br.com.inaciojr9.businessapi.repository;

import java.util.List;

import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import br.com.inaciojr9.businessapi.model.Atendimento;

@Transactional(readOnly = true)
@NamedQueries({
		@NamedQuery(name = "AtendimentoRepository.findByClienteId", 
				query = "SELECT atend FROM Atendimento atend WHERE atend.cliente.id = :clienteId")}
		
		)
public interface AtendimentoRepository extends JpaRepository<Atendimento, Long> {

	List<Atendimento> findByClienteId(@Param("clienteId") Long clienteId);

	Page<Atendimento> findByClienteId(@Param("clienteId") Long clienteId, Pageable pageable);
	
	@Query(value = "select atend from Atendimento atend where year(atend.data) = :ano and month(atend.data) = :mes and atend.empresa.id = :empresaId")
	List<Atendimento> getAllPorAnoEMes(@Param("ano") Integer ano, @Param("mes") Integer mes, @Param("empresaId") Long empresaId);
	
}
