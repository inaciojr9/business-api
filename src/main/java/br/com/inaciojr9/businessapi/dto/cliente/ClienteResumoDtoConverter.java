package br.com.inaciojr9.businessapi.dto.cliente;

import br.com.inaciojr9.businessapi.model.Cliente;

public class ClienteResumoDtoConverter {
	
	public static ClienteResumoDto doModelParaDto(Cliente model) {
		
		ClienteResumoDto dto = new ClienteResumoDto(model.getId(), model.getNome());
		return dto;
		
	}
	
	public static Cliente doDtoParaModel(ClienteResumoDto dto) {
		Cliente model = new Cliente();
		model.setId(dto.getId());
		model.setNome(dto.getNome());
		return model;
	}
	

}
