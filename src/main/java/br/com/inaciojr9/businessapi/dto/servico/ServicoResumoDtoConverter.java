package br.com.inaciojr9.businessapi.dto.servico;

import br.com.inaciojr9.businessapi.model.Servico;

public class ServicoResumoDtoConverter {
	
	public static ServicoResumoDto doModelParaDto(Servico model) {
		
		ServicoResumoDto dto = new ServicoResumoDto();
		dto.setId(model.getId());
		dto.setNome(model.getNome());
		return dto;
		
	}
	
	public static Servico doDtoParaModel(ServicoResumoDto dto) {
		Servico model = new Servico();
		model.setId(dto.getId());
		model.setNome(dto.getNome());
		return model;
	}
	

}
