package senac.projeto.pombo.pombo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import senac.projeto.pombo.pombo.exception.ProjetoPomboException;
import senac.projeto.pombo.pombo.model.dto.DenunciaDTO;
import senac.projeto.pombo.pombo.model.entity.Denuncia;
import senac.projeto.pombo.pombo.model.entity.Pruu;
import senac.projeto.pombo.pombo.model.enumPombo.EnumDenuncia;
import senac.projeto.pombo.pombo.model.repository.DenunciaRepository;
import senac.projeto.pombo.pombo.model.repository.PruuRepository;


@Service
public class DenunciaService {
	@Autowired
	private DenunciaRepository denunciaRepository;
	
	@Autowired
	private PruuRepository pruuRepository;
	
	public Denuncia denunciarPruu(Denuncia denuncia) throws ProjetoPomboException { 
		List<Denuncia> denuncias = buscarDenuncias();
		
		for (Denuncia denunciaVerificada : denuncias) {
			if (denunciaVerificada.getUsuarioId() == denuncia.getUsuarioId()) {
				throw new ProjetoPomboException ("Usuário já denunciou este pruu");
			}
		}
		
		return denunciaRepository.save(denuncia);
		
	}
		
	public List<Denuncia> buscarDenuncias(){
		return denunciaRepository.findAll();		
	}
	
	public Denuncia buscarDenuncia(String idDenuncia) {
		return denunciaRepository.findById(idDenuncia).get();
	}
	
	public List<Denuncia> buscarDenunciasPorIdPruu(String idPruu){
		List<Denuncia> denunciasGerais = denunciaRepository.findAll();
		List<Denuncia> denunciasPruu = new ArrayList<>();
		
		for (Denuncia denuncia : denunciasGerais) {
			if (denuncia.getPruuId().equalsIgnoreCase(idPruu)) {
				denunciasPruu.add(denuncia);
			}
		}
		return denunciasPruu;
				
				
	}
	
	public Denuncia atualizarSituacaoDenuncia(Denuncia denunciaAnalizada) throws ProjetoPomboException{
		if (denunciaAnalizada.getSituacao() == EnumDenuncia.PENDENTE) {
			denunciaAnalizada.setSituacao(EnumDenuncia.ANALISADA);
		}else {
			throw new ProjetoPomboException("Denuncia já análizada");
		}
		
		
		return denunciaRepository.save(denunciaAnalizada);
	}

	public List<Denuncia> buscarDenunciasPendentesPruu(String idPruu){
		Pruu pruu = pruuRepository.findById(idPruu).get();
		List<Denuncia> denunciasPruu = buscarDenunciasPorIdPruu(idPruu);
		List<Denuncia> denunciasPendentes = new ArrayList<>();
		
		for (Denuncia denuncia : denunciasPruu) {
			if (denuncia.getPruuId().equalsIgnoreCase(idPruu) & denuncia.getSituacao() == EnumDenuncia.PENDENTE) {
				denunciasPendentes.add(denuncia);
			}
		}
		return denunciasPendentes;
					
	}
	
	public List<Denuncia> buscarDenunciasAnalisadasPruu(String idPruu){
		Pruu pruu = pruuRepository.findById(idPruu).get();
		List<Denuncia> denunciasPruu = buscarDenunciasPorIdPruu(idPruu);
		List<Denuncia> denunciasAnalisadas = new ArrayList<>();
		
		for (Denuncia denuncia : denunciasPruu) {
			if (denuncia.getPruuId().equalsIgnoreCase(idPruu) & denuncia.getSituacao() == EnumDenuncia.ANALISADA) {
				denunciasAnalisadas.add(denuncia);
			}
		}
		return denunciasAnalisadas;
					
	}
	
	// Tentativa de gerar relatório com idDenuncia
	/*public DenunciaDTO gerarRelatorioDenuncia(String idDenuncia) {
		Denuncia denunciaRelatada =  denunciaRepository.findById(idDenuncia).get();
		List<Denuncia> denunciasPruu = buscarDenunciasPorIdPruu(denunciaRelatada.getPruuId());
		List<Denuncia> denunciasPendentes = buscarDenunciasPendentesPruu(denunciaRelatada.getPruuId());
		List<Denuncia> denunciasAnalisadas = buscarDenunciasAnalisadasPruu(denunciaRelatada.getPruuId());
		*/
	
	public DenunciaDTO gerarRelatorioPorIdPruu(String idPruu) {
		List<Denuncia> denunciasAnalisadas = buscarDenunciasAnalisadasPruu(idPruu);
		List<Denuncia> denunciasPendentes = buscarDenunciasPendentesPruu(idPruu);
		List<Denuncia> denunciasPruu = buscarDenunciasPorIdPruu(idPruu);
		
		DenunciaDTO dto = new DenunciaDTO();
		
		dto.setIdPruuDenunciado(idPruu);
		dto.setQuantDenunciasAnalisadas(denunciasAnalisadas.size());
		dto.setQuantDenunciasPendentes(denunciasPendentes.size());
		dto.setQuantDenunciasPruu(denunciasPruu.size());
		
		return dto;
		
	}
	
	
}
