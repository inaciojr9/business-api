package br.com.inaciojr9.businessapi.dto.atendimento;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import br.com.inaciojr9.businessapi.dto.empresa.EmpresaDtoConverter;
import br.com.inaciojr9.businessapi.dto.servico.ServicoResumoDtoConverter;
import br.com.inaciojr9.businessapi.exception.ObjetoInvalidoException;
import br.com.inaciojr9.businessapi.model.AtendimentoServico;
import br.com.inaciojr9.businessapi.model.Empresa;

public class AtendimentoServicoDtoConverter {
	
	private static final SimpleDateFormat DATE_TIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	
	public static AtendimentoServicoDto doModelParaDto(Empresa empresa, AtendimentoServico model) {
		
		AtendimentoServicoDto dto = new AtendimentoServicoDto();
		
		dto.setId(model.getId());
		dto.setEmpresa(EmpresaDtoConverter.doModelParaDto(empresa).getEmpresaResumoDto());
		dto.setServico(ServicoResumoDtoConverter.doModelParaDto(model.getServico()));
		dto.setValor(model.getValor().toString());
		if(model.getDataCriacao() != null) {
			dto.setDataCriacao(DATE_TIME_FORMAT.format(model.getDataCriacao()));
		}
		if(model.getDataAtualizacao() != null) {
			dto.setDataAtualizacao(DATE_TIME_FORMAT.format(model.getDataAtualizacao()));
		}
		dto.setAtendimentoId(model.getAtendimentoId());
		
		return dto;
		
	}
	
	public static List<AtendimentoServicoDto> doModelParaDto(Empresa empresa, List<AtendimentoServico> model) {
		List<AtendimentoServicoDto> servicosDto = new ArrayList<>();
		
		if(model != null && !model.isEmpty()) {
			for(AtendimentoServico servico:model) {
				servicosDto.add(doModelParaDto(empresa,servico));
			}
		}
		
		return servicosDto;
	}
	
	
	public static AtendimentoServico doDtoParaModel(Empresa empresa, AtendimentoServicoDto dto) throws ObjetoInvalidoException {
		
		AtendimentoServico atendimentoServico = new AtendimentoServico();
		atendimentoServico.setEmpresa(empresa);
		atendimentoServico.setId(dto.getId());
		atendimentoServico.setValor(new BigDecimal(dto.getValor()));
		atendimentoServico.setServico(ServicoResumoDtoConverter.doDtoParaModel(dto.getServico()));
		atendimentoServico.setAtendimentoId(dto.getAtendimentoId());
		
		return atendimentoServico;
		
	}
	
	public static List<AtendimentoServico> doDtoParaModel(Empresa empresa, List<AtendimentoServicoDto> dtos) throws ObjetoInvalidoException {
		
		List<AtendimentoServico> models = new ArrayList<>();
		if(dtos != null && !dtos.isEmpty()) {
			for(AtendimentoServicoDto dto:dtos) {
				models.add(doDtoParaModel(empresa, dto));
			}
		}
		return models;
	
	}


}
