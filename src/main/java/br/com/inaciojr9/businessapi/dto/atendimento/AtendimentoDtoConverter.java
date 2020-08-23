package br.com.inaciojr9.businessapi.dto.atendimento;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import br.com.inaciojr9.businessapi.dto.cliente.ClienteResumoDtoConverter;
import br.com.inaciojr9.businessapi.dto.empresa.EmpresaDtoConverter;
import br.com.inaciojr9.businessapi.dto.formaDeRecebimento.FormaDeRecebimentoDtoConverter;
import br.com.inaciojr9.businessapi.dto.formaDeRecebimento.FormaDeRecebimentoResumoDtoConverter;
import br.com.inaciojr9.businessapi.exception.ObjetoInvalidoException;
import br.com.inaciojr9.businessapi.model.Atendimento;
import br.com.inaciojr9.businessapi.model.Empresa;

public class AtendimentoDtoConverter {
	
	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
	private static final SimpleDateFormat DATE_TIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	
	public static AtendimentoDto doModelParaDto(Empresa empresa, Atendimento model) {
		
		AtendimentoDto dto = new AtendimentoDto();
		
		dto.setId(model.getId());
		dto.setCliente(ClienteResumoDtoConverter.doModelParaDto(model.getCliente()));
		dto.setEmpresa(EmpresaDtoConverter.doModelParaDto(empresa).getEmpresaResumoDto());
		dto.setFormaDeRecebimento(FormaDeRecebimentoResumoDtoConverter.doModelParaDto(model.getFormaDeRecebimento()));
		dto.setData(DATE_FORMAT.format(model.getData()));
		dto.setValor(model.getValor().toString());
		if(model.getDataCriacao() != null) {
			dto.setDataCriacao(DATE_TIME_FORMAT.format(model.getDataCriacao()));
		}
		if(model.getDataAtualizacao() != null) {
			dto.setDataAtualizacao(DATE_TIME_FORMAT.format(model.getDataAtualizacao()));
		}
		dto.setQtdParcelas(model.getQtdParcelas().toString());
		dto.setServicos(AtendimentoServicoDtoConverter.doModelParaDto(empresa, model.getServicos()));
		
		return dto;
		
	}
	
	
	public static Atendimento doDtoParaModel(Empresa empresa, AtendimentoDtoIn dto) throws ObjetoInvalidoException {
		
		Atendimento atendimento = new Atendimento();
		atendimento.setEmpresa(empresa);
		atendimento.setCliente(ClienteResumoDtoConverter.doDtoParaModel(dto.getCliente()));
		atendimento.setFormaDeRecebimento(FormaDeRecebimentoDtoConverter.doDtoParaModel(dto.getFormaDeRecebimento()));
		atendimento.setQtdParcelas(Integer.valueOf(dto.getQtdParcelas()));
		try {
			atendimento.setData(DATE_FORMAT.parse(dto.getData()));
		} catch (ParseException e) {
			throw new ObjetoInvalidoException("Data do atendimento inválida.");
		}
		atendimento.setServicos(AtendimentoServicoDtoConverter.doDtoParaModel(empresa, dto.getServicos()));
		
		return atendimento;
		
	}

}
