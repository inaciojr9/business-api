package br.com.inaciojr9.businessapi.dto.empresa;

import br.com.inaciojr9.businessapi.model.Empresa;

public class EmpresaResumoDtoConverter {
	
	public static EmpresaResumoDto doModelParaDto(Empresa model) {
		
		EmpresaResumoDto dto = new EmpresaResumoDto();
		dto.setId(model.getId());
		dto.setRazaoSocial(model.getRazaoSocial());
		return dto;
		
	}
	
	public static Empresa doDtoParaModel(EmpresaResumoDto dto) {
		Empresa model = new Empresa();
		model.setId(dto.getId());
		model.setRazaoSocial(dto.getRazaoSocial());
		return model;
	}
	

}
