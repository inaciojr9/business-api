package br.com.inaciojr9.businessapi.dto.formaDeRecebimento;

import br.com.inaciojr9.businessapi.model.FormaDeRecebimento;

public class FormaDeRecebimentoResumoDtoConverter {
	
	public static FormaDeRecebimentoResumoDto doModelParaDto(FormaDeRecebimento model) {
		
		FormaDeRecebimentoResumoDto dto = new FormaDeRecebimentoResumoDto();
		dto.setId(model.getId());
		dto.setNome(model.getNome());
		return dto;
		
	}
	
	public static FormaDeRecebimento doDtoParaModel(FormaDeRecebimentoResumoDto dto) {
		FormaDeRecebimento model = new FormaDeRecebimento();
		model.setId(dto.getId());
		model.setNome(dto.getNome());
		return model;
	}
	

}
