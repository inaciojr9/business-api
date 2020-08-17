package br.com.inaciojr9.businessapi.dto.gestaomensal;

import br.com.inaciojr9.businessapi.dto.empresa.EmpresaResumoDto;

public class GestaoMensalDtoOut {
	
	private Long id;
	private Integer mes;
	private Integer ano;
	private String receitaProduto;
	private String despesaTotal;
	private String meta;
	private String percentualComissao;
	private EmpresaResumoDto empresa;
	
	private String receitaServico;
	private String receitaTotal;
	private String resultadoOperacional;
	private String valorComissao;
	private String resultadoFinal;
	
	private String dataCriacao;
	private String dataAtualizacao;
	
	public GestaoMensalDtoOut() {}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getMes() {
		return mes;
	}

	public void setMes(Integer mes) {
		this.mes = mes;
	}

	public Integer getAno() {
		return ano;
	}

	public void setAno(Integer ano) {
		this.ano = ano;
	}

	public String getReceitaProduto() {
		return receitaProduto;
	}

	public void setReceitaProduto(String receitaProduto) {
		this.receitaProduto = receitaProduto;
	}

	public String getDespesaTotal() {
		return despesaTotal;
	}

	public void setDespesaTotal(String despesaTotal) {
		this.despesaTotal = despesaTotal;
	}

	public String getMeta() {
		return meta;
	}

	public void setMeta(String meta) {
		this.meta = meta;
	}

	public String getPercentualComissao() {
		return percentualComissao;
	}

	public void setPercentualComissao(String percentualComissao) {
		this.percentualComissao = percentualComissao;
	}

	public EmpresaResumoDto getEmpresa() {
		return empresa;
	}

	public void setEmpresa(EmpresaResumoDto empresa) {
		this.empresa = empresa;
	}

	public String getReceitaServico() {
		return receitaServico;
	}

	public void setReceitaServico(String receitaServico) {
		this.receitaServico = receitaServico;
	}

	public String getReceitaTotal() {
		return receitaTotal;
	}

	public void setReceitaTotal(String receitaTotal) {
		this.receitaTotal = receitaTotal;
	}

	public String getResultadoOperacional() {
		return resultadoOperacional;
	}

	public void setResultadoOperacional(String resultadoOperacional) {
		this.resultadoOperacional = resultadoOperacional;
	}

	public String getValorComissao() {
		return valorComissao;
	}

	public void setValorComissao(String valorComissao) {
		this.valorComissao = valorComissao;
	}

	public String getResultadoFinal() {
		return resultadoFinal;
	}

	public void setResultadoFinal(String resultadoFinal) {
		this.resultadoFinal = resultadoFinal;
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

}
