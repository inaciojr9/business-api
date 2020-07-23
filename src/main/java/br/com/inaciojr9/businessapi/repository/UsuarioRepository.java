package br.com.inaciojr9.businessapi.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import br.com.inaciojr9.businessapi.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
	
	@Transactional(readOnly = true)
	Usuario findByEmail(String email);

}
