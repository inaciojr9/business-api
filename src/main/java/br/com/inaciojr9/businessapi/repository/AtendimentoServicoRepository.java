package br.com.inaciojr9.businessapi.repository;

import java.util.List;

import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import br.com.inaciojr9.businessapi.model.AtendimentoServico;

@Transactional(readOnly = true)
@NamedQueries({
		@NamedQuery(name = "AtendimentoServicoRepository.findByAtendimentoId", 
				query = "SELECT atend FROM AtendimentoServico atend WHERE atend.atendimento.id = :atendimentoId") })
public interface AtendimentoServicoRepository extends JpaRepository<AtendimentoServico, Long> {

	List<AtendimentoServico> findByAtendimentoId(@Param("atendimentoId") Long atendimentoId);

}
