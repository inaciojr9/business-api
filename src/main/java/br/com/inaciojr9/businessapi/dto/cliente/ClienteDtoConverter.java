package br.com.inaciojr9.businessapi.dto.cliente;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import br.com.inaciojr9.businessapi.dto.empresa.EmpresaDtoConverter;
import br.com.inaciojr9.businessapi.exception.ObjetoInvalidoException;
import br.com.inaciojr9.businessapi.model.Cliente;
import br.com.inaciojr9.businessapi.model.Empresa;

public class ClienteDtoConverter {
	
	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
	private static final SimpleDateFormat DATE_TIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	
	public static ClienteDto doModelParaDto(Empresa empresa, Cliente model) {
		
		ClienteDto dto = new ClienteDto();
		
		dto.setClienteResumoDto(new ClienteResumoDto(model.getId(), model.getNome()));
		dto.setEmpresa(EmpresaDtoConverter.doModelParaDto(empresa).getEmpresaResumoDto());
		dto.setEmail(model.getEmail());
		dto.setCpf(model.getCpf());
		dto.setTelefone(model.getTelefone());
		dto.setDataNascimento(DATE_FORMAT.format(model.getDataNascimento()));
		dto.setSexo(model.getSexo());
		dto.setSexoOutro(model.getSexoOutro());
		dto.setEndereco(model.getEndereco());
		dto.setBairro(model.getBairro());
		dto.setCidade(model.getCidade());
		dto.setEstado(model.getEstado());
		dto.setCep(model.getCep());
		if(model.getDataCriacao() != null) {
			dto.setDataCriacao(DATE_TIME_FORMAT.format(model.getDataCriacao()));
		}
		if(model.getDataAtualizacao() != null) {
			dto.setDataAtualizacao(DATE_TIME_FORMAT.format(model.getDataAtualizacao()));
		}
		
		return dto;
		
	}
	
	
	public static Cliente doDtoParaModel(Empresa empresa, ClienteDto dto) throws ObjetoInvalidoException {
		
		Cliente cliente = new Cliente();
		cliente.setEmpresa(empresa);
		cliente.setId(dto.getClienteResumoDto().getId());
		cliente.setEmail(dto.getEmail());
		cliente.setNome(dto.getClienteResumoDto().getNome());
		cliente.setCpf(dto.getCpf());
		cliente.setTelefone(dto.getTelefone());
		try {
			cliente.setDataNascimento(DATE_FORMAT.parse(dto.getDataNascimento()));
		} catch (ParseException e) {
			throw new ObjetoInvalidoException("Data de nascimento inválida.");
		}
		cliente.setSexo(dto.getSexo());
		cliente.setSexoOutro(dto.getSexoOutro());
		cliente.setEndereco(dto.getEndereco());
		cliente.setBairro(dto.getBairro());
		cliente.setCidade(dto.getCidade());
		cliente.setEstado(dto.getEstado());
		cliente.setCep(dto.getCep());
		
		return cliente;
		
	}
	
	public static Cliente doDtoParaModel(Empresa empresa, ClienteResumoDto dto) throws ObjetoInvalidoException {
		
		return ClienteResumoDtoConverter.doDtoParaModel(dto);
		
	}

}
