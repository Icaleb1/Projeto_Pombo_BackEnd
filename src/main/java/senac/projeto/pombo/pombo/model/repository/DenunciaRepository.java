package senac.projeto.pombo.pombo.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import senac.projeto.pombo.pombo.model.entity.Denuncia;


@Repository
public interface DenunciaRepository extends JpaRepository<Denuncia, String>{

}
