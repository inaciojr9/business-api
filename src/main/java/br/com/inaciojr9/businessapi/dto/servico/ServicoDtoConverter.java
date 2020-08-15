package br.com.inaciojr9.businessapi.dto.servico;

import java.util.ArrayList;
import java.util.List;

import br.com.inaciojr9.businessapi.model.Servico;

public class ServicoDtoConverter {
	
	public static List<ServicoDto> doModelParaDto(List<Servico> modelList) {
		
		List<ServicoDto> dtoList = new ArrayList<>();
		
		if(modelList != null && !modelList.isEmpty()) {
			for(Servico model:modelList) {
				dtoList.add(doModelParaDto(model));
			}
		}
	
		return dtoList;
	}

	public static ServicoDto doModelParaDto(Servico model) {
		
		ServicoDto dto = new ServicoDto();
		dto.setServicoResumoDto(ServicoResumoDtoConverter.doModelParaDto(model));		
		return dto;
		
	}
	
	
	public static Servico doDtoParaModel(ServicoDto dto) {
		
		Servico model = ServicoResumoDtoConverter.doDtoParaModel(dto.getServicoResumoDto());
		return model;
		
	}

}
