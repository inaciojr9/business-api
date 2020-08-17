package br.com.inaciojr9.businessapi.dto.gestaomensal;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import br.com.inaciojr9.businessapi.dto.empresa.EmpresaResumoDto;

public class GestaoMensalDtoIn {
	
	private Integer mes;
	private Integer ano;
	private String receitaProduto;
	private String despesaTotal;
	private String meta;
	private String percentualComissao;
	private EmpresaResumoDto empresa;
	
	public GestaoMensalDtoIn() {}

	@NotEmpty(message = "Por favor, informe o mês da gestão.")
	@Min(value = 1, message = "Informe um mês entre 1 e 12")
	@Max(value = 12, message = "Informe um mês entre 1 e 12")
	public Integer getMes() {
		return this.mes;
	}
	public void setMes(Integer mes) {
		this.mes = mes;
	}
	
	@NotEmpty(message = "Por favor, informe o ano da gestão.")
	@Positive (message = "Ano inválido. Tem que ser um número positivo.")
	public Integer getAno() {
		return this.ano;
	}
	public void setAno(Integer ano) {
		this.ano = ano;
	}
	
	@NotEmpty(message = "Por favor, informe o total de receitas com produto no mês.")
	public String getReceitaProduto() {
		return receitaProduto;
	}
	public void setReceitaProduto(String receitaProduto) {
		this.receitaProduto = receitaProduto;
	}

	@NotEmpty(message = "Por favor, informe o total de despesas do mês.")
	public String getDespesaTotal() {
		return despesaTotal;
	}
	public void setDespesaTotal(String despesaTotal) {
		this.despesaTotal = despesaTotal;
	}

	@NotEmpty(message = "Por favor, informe o valor da meta do mês.")
	public String getMeta() {
		return meta;
	}
	public void setMeta(String meta) {
		this.meta = meta;
	}

	@NotEmpty(message = "Por favor, informe o percetual de comissão. Ex: 20 para 20%")
	public String getPercentualComissao() {
		return percentualComissao;
	}
	public void setPercentualComissao(String percentualComissao) {
		this.percentualComissao = percentualComissao;
	}

	@NotNull
	public EmpresaResumoDto getEmpresa() {
		return empresa;
	}
	public void setEmpresa(EmpresaResumoDto empresa) {
		this.empresa = empresa;
	}

}
