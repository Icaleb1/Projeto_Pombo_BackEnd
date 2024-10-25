package senac.projeto.pombo.pombo.model.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import senac.projeto.pombo.pombo.model.entity.Usuario;

@Repository
public interface UsuarioRepository extends 
		JpaRepository<Usuario, String>, JpaSpecificationExecutor<Usuario>{

	Optional<Usuario> findByEmail(String email);
}
