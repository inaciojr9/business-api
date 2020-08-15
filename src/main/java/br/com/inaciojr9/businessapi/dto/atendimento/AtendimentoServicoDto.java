package br.com.inaciojr9.businessapi.dto.atendimento;

import br.com.inaciojr9.businessapi.dto.empresa.EmpresaResumoDto;
import br.com.inaciojr9.businessapi.dto.servico.ServicoResumoDto;

public class AtendimentoServicoDto {
	
	private Long id;
	private String valor;
	private ServicoResumoDto servico;
	private EmpresaResumoDto empresa;
	private String dataCriacao;
	private String dataAtualizacao;
	private Long atendimentoId;

	public AtendimentoServicoDto() {}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

	public String getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(String dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	public String getDataAtualizacao() {
		return dataAtualizacao;
	}

	public void setDataAtualizacao(String dataAtualizacao) {
		this.dataAtualizacao = dataAtualizacao;
	}

	public Long getAtendimentoId() {
		return atendimentoId;
	}

	public void setAtendimentoId(Long atendimentoId) {
		this.atendimentoId = atendimentoId;
	}
	
}