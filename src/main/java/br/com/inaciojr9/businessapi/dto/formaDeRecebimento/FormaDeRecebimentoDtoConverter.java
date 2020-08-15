package br.com.inaciojr9.businessapi.dto.formaDeRecebimento;

import br.com.inaciojr9.businessapi.model.FormaDeRecebimento;

public class FormaDeRecebimentoDtoConverter {
	
	public static FormaDeRecebimentoDto doModelParaDto(FormaDeRecebimento model) {
		
		FormaDeRecebimentoDto dto = new FormaDeRecebimentoDto();
		dto.setFormaDeRecebimentoResumoDto(FormaDeRecebimentoResumoDtoConverter.doModelParaDto(model));		
		return dto;
		
	}
	
	
	public static FormaDeRecebimento doDtoParaModel(FormaDeRecebimentoDto dto) {
		
		FormaDeRecebimento model = FormaDeRecebimentoResumoDtoConverter.doDtoParaModel(dto.getFormaDeRecebimentoResumoDto());
		return model;
		
	}
	
	public static FormaDeRecebimento doDtoParaModel(FormaDeRecebimentoResumoDto dto) {
		
		FormaDeRecebimento model = FormaDeRecebimentoResumoDtoConverter.doDtoParaModel(dto);
		return model;
		
	}

}
