package br.com.inaciojr9.businessapi.dto.atendimento;

import br.com.inaciojr9.businessapi.dto.empresa.EmpresaResumoDto;
import br.com.inaciojr9.businessapi.dto.servico.ServicoResumoDto;

public class AtendimentoServicoDtoIn {
	
	private String valor;
	private ServicoResumoDto servico;
	private EmpresaResumoDto empresa;
	private Long atendimentoId;

	public AtendimentoServicoDtoIn() {}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	public ServicoResumoDto getServico() {
		return servico;
	}

	public void setServico(ServicoResumoDto servico) {
		this.servico = servico;
	}

	public EmpresaResumoDto getEmpresa() {
		return empresa;
	}

	public void setEmpresa(EmpresaResumoDto empresa) {
		this.empresa = empresa;
	}

	public Long getAtendimentoId() {
		return atendimentoId;
	}

	public void setAtendimentoId(Long atendimentoId) {
		this.atendimentoId = atendimentoId;
	}
	
}