package br.com.inaciojr9.businessapi.dto.empresa;

import br.com.inaciojr9.businessapi.model.Empresa;

public class EmpresaDtoConverter {
	
	public static EmpresaDto doModelParaDto(Empresa model) {
		
		EmpresaDto dto = new EmpresaDto();
		dto.setEmpresaResumoDto(EmpresaResumoDtoConverter.doModelParaDto(model));		
		dto.setCnpj(model.getCnpj());
		return dto;
		
	}
	
	
	public static Empresa doDtoParaModel(EmpresaDto dto) {
		
		Empresa model = EmpresaResumoDtoConverter.doDtoParaModel(dto.getEmpresaResumoDto());
		model.setCnpj(dto.getCnpj());
		return model;
		
	}

}
