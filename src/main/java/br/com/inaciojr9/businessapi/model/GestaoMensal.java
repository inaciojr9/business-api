package br.com.inaciojr9.businessapi.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

@Entity
@Table(name = "gestao_mensal")
public class GestaoMensal {

	private Long id;
	private Integer ano;
	private Integer mes;
	private BigDecimal receitaProduto;
	private BigDecimal receitaServico;
	private BigDecimal receitaTotal;
	private BigDecimal despesaTotal;
	private BigDecimal resultadoOperacional;
	private BigDecimal meta;
	private BigDecimal percentualComissao;
	private BigDecimal valorComissao;
	private BigDecimal resultadoFinal;
	private Date dataCriacao;
	private Date dataAtualizacao;
	private Empresa empresa;
	
	public GestaoMensal() {
		super();
	}

	public GestaoMensal(Long id, Integer ano, Integer mes, BigDecimal receitaProduto, BigDecimal receitaServico,
			BigDecimal despesaTotal, BigDecimal meta, BigDecimal percentualComissao, Empresa empresa) {
		super();
		this.id = id;
		this.ano = ano;
		this.mes = mes;
		this.receitaProduto = receitaProduto;
		this.receitaServico = receitaServico;
		this.despesaTotal = despesaTotal;
		this.meta = meta;
		this.percentualComissao = percentualComissao;
		this.empresa = empresa;
		atualizarCalculos();
	}
	
	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(name = "ano", nullable = false)
	public Integer getAno() {
		return this.ano;
	}
	public void setAno(Integer ano) {
		this.ano = ano;
	}
	
	@Column(name = "mes", nullable = false)
	public Integer getMes() {
		return this.mes;
	}
	public void setMes(Integer mes) {
		this.mes = mes;
	}
	
	@Column(name = "receita_produto", nullable = false)
	public BigDecimal getReceitaProduto() {
		return receitaProduto;
	}
	public void setReceitaProduto(BigDecimal receitaProduto) {
		this.receitaProduto = receitaProduto;
	}
	
	@Column(name = "receita_servico", nullable = false)
	public BigDecimal getReceitaServico() {
		return receitaServico;
	}
	public void setReceitaServico(BigDecimal receitaServico) {
		this.receitaServico = receitaServico;
	}
	
	@Column(name = "receita_total", nullable = false)
	public BigDecimal getReceitaTotal() {
		return receitaTotal;
	}
	public void setReceitaTotal(BigDecimal receitaTotal) {
		this.receitaTotal = receitaTotal;
	}
	
	@Column(name = "despesa_total", nullable = false)
	public BigDecimal getDespesaTotal() {
		return despesaTotal;
	}
	public void setDespesaTotal(BigDecimal despesaTotal) {
		this.despesaTotal = despesaTotal;
	}
	
	@Column(name = "resultado_operacional", nullable = false)
	public BigDecimal getResultadoOperacional() {
		return resultadoOperacional;
	}
	public void setResultadoOperacional(BigDecimal resultadoOperacional) {
		this.resultadoOperacional = resultadoOperacional;
	}
	
	public BigDecimal getMeta() {
		return meta;
	}
	public void setMeta(BigDecimal meta) {
		this.meta = meta;
	}
	
	@Column(name = "percentual_comissao", nullable = false)
	public BigDecimal getPercentualComissao() {
		return percentualComissao;
	}
	public void setPercentualComissao(BigDecimal percentualComissao) {
		this.percentualComissao = percentualComissao;
	}
	
	@Column(name = "valor_comissao", nullable = false)
	public BigDecimal getValorComissao() {
		return valorComissao;
	}
	public void setValorComissao(BigDecimal valorComissao) {
		this.valorComissao = valorComissao;
	}
	
	@Column(name = "resultado_final", nullable = false)
	public BigDecimal getResultadoFinal() {
		return resultadoFinal;
	}
	public void setResultadoFinal(BigDecimal resultadoFinal) {
		this.resultadoFinal = resultadoFinal;
	}
	
	@Column(name = "data_criacao", nullable = false)
	public Date getDataCriacao() {
		return dataCriacao;
	}
	public void setDataCriacao(Date dataCriacao) {
		this.dataCriacao = dataCriacao;
	}
	
	@Column(name = "data_atualizacao", nullable = false)
	public Date getDataAtualizacao() {
		return dataAtualizacao;
	}
	public void setDataAtualizacao(Date dataAtualizacao) {
		this.dataAtualizacao = dataAtualizacao;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	public Empresa getEmpresa() {
		return empresa;
	}
	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}
	
	public BigDecimal calcularReceitaTotal() {
		return this.receitaProduto.add(this.receitaServico);
	}
	
	public BigDecimal calcularResultadoOperacional() {
		return this.receitaTotal.subtract(this.despesaTotal);
	}
	
	public BigDecimal calcularValorComissao() {
		System.out.println(">>>>>>>>>>>>>>>>> resultado operacional "+this.resultadoOperacional);
		if(this.resultadoOperacional.compareTo(new BigDecimal(0)) == -1) {
			System.out.println(">>>>>>>>>>>>>>>>> 0 ");
			return new BigDecimal(0);
		} else {
			System.out.println(">>>>>>>>>>>>>>>>> else ");
			return this.resultadoOperacional.multiply(this.percentualComissao).divide(new BigDecimal(100));
		}
	}
	
	public BigDecimal calcularResultadoFinal() {
		return this.resultadoOperacional.subtract(this.valorComissao);
	}
	
	public void atualizarCalculos() {
		this.receitaTotal = calcularReceitaTotal();
		this.resultadoOperacional = calcularResultadoOperacional();
		this.valorComissao = calcularValorComissao();
		this.resultadoFinal = calcularResultadoFinal();
	}
	
	@PreUpdate
    public void preUpdate() {
        dataAtualizacao = new Date();
    }
     
    @PrePersist
    public void prePersist() {
        final Date atual = new Date();
        dataCriacao = atual;
        dataAtualizacao = atual;
    }
	
}
