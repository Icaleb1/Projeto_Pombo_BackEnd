package senac.projeto.pombo.pombo.model.entity;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.UuidGenerator;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Data
@Table
public class Pruu {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@UuidGenerator
	private String uuid;
	
	@NotBlank(message = "Texto é obrigatório")
	@Size(min = 1, max = 300)
	private String texto;
	
	private LocalDateTime dataHoraCriacao;
	
	private boolean bloqueado;
	
	private int quantLikes;
	
	@ManyToOne
	@JoinColumn(name = "id_usuario")
	private Usuario usuario;
	
	@ManyToMany(mappedBy = "pruusCurtidos")
	@JsonIgnore
	private List<Usuario> usuariosQueCurtiram;

	private boolean ativo;
	
	@PrePersist
    protected void onCreate() {
        dataHoraCriacao = LocalDateTime.now();
        ativo = true;
    }
	
}
