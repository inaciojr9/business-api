package br.com.inaciojr9.businessapi.dto.gestaomensal;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;

import br.com.inaciojr9.businessapi.dto.empresa.EmpresaDtoConverter;
import br.com.inaciojr9.businessapi.exception.ObjetoInvalidoException;
import br.com.inaciojr9.businessapi.model.Empresa;
import br.com.inaciojr9.businessapi.model.GestaoMensal;

public class GestaoMensalDtoConverter {
	
	private static final SimpleDateFormat DATE_TIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	
	public static GestaoMensalDtoOut doModelParaDto(Empresa empresa, GestaoMensal model) {
		
		GestaoMensalDtoOut dto = new GestaoMensalDtoOut();
		
		dto.setId(model.getId());
		dto.setAno(model.getAno());
		dto.setMes(model.getMes());
		dto.setReceitaProduto(model.getReceitaProduto().toString());
		dto.setDespesaTotal(model.getDespesaTotal().toString());
		dto.setMeta(model.getMeta().toString());
		dto.setPercentualComissao(model.getPercentualComissao().toEngineeringString());
		dto.setEmpresa(EmpresaDtoConverter.doModelParaDto(empresa).getEmpresaResumoDto());
		
		dto.setReceitaServico(model.getReceitaServico().toString());
		dto.setReceitaTotal(model.getReceitaTotal().toString());
		dto.setResultadoOperacional(model.getResultadoOperacional().toString());
		dto.setValorComissao(model.getValorComissao().toString());
		dto.setResultadoFinal(model.getResultadoFinal().toString());
		
		
		if(model.getDataCriacao() != null) {
			dto.setDataCriacao(DATE_TIME_FORMAT.format(model.getDataCriacao()));
		}
		if(model.getDataAtualizacao() != null) {
			dto.setDataAtualizacao(DATE_TIME_FORMAT.format(model.getDataAtualizacao()));
		}
		
		return dto;
		
	}
	
	
	public static GestaoMensal doDtoParaModel(Empresa empresa, GestaoMensalDtoIn dtoIn) throws ObjetoInvalidoException {
		
		// Get receita servico;
		BigDecimal receitaServico = new BigDecimal(0);
		
		GestaoMensal model = new GestaoMensal( 	null,
												dtoIn.getAno(),
												dtoIn.getMes(), 
												new BigDecimal(dtoIn.getReceitaProduto()), 
												receitaServico,
												new BigDecimal(dtoIn.getDespesaTotal()),
												new BigDecimal(dtoIn.getMeta()),
												new BigDecimal(dtoIn.getPercentualComissao()),
												empresa);
		
		return model;
		
	}

}
