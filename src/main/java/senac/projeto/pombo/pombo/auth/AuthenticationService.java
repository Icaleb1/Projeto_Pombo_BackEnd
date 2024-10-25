package senac.projeto.pombo.pombo.auth;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import senac.projeto.pombo.pombo.exception.ProjetoPomboException;
import senac.projeto.pombo.pombo.model.entity.Usuario;

@Service
public class AuthenticationService {
	
	private final JwtService jwtService;
	
	public AuthenticationService(JwtService jwtService) {
		this.jwtService = jwtService;
	}
	
	public String authenticate(Authentication authentication) {
		return jwtService.getGenerateToken(authentication);
	}
	
	public Usuario getUsuarioAutenticado() throws ProjetoPomboException{
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Usuario usuarioAutenticado = null;
		
		if (usuarioAutenticado != null && authentication.isAuthenticated()) {
			Object principal = authentication.getPrincipal();
			
			if (principal instanceof Usuario) {
				UserDetails userDetails = (Usuario) principal;
				usuarioAutenticado = (Usuario) userDetails;
			}
		}
		
		if (usuarioAutenticado == null) {
			throw new ProjetoPomboException("Usuário não encontrado");
		}
		
		return usuarioAutenticado;
	}

}
