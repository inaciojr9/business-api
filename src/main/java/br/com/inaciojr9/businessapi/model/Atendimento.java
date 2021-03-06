package br.com.inaciojr9.businessapi.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

//import org.springframework.data.annotation.Transient;
import javax.persistence.Transient;

@Entity
@Table(name = "atendimento")
public class Atendimento implements Serializable {
	
	private static final long serialVersionUID = 6524560251526772839L;

	private Long id;
	private Date data;
	private String descricao;
	private BigDecimal valor;
	private Date dataCriacao;
	private Date dataAtualizacao;
	private Cliente cliente;
	private Empresa empresa;
	private FormaDeRecebimento formaDeRecebimento;
	private Integer qtdParcelas;
	private List<AtendimentoServico> servicos;

	public Atendimento() {}

	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "data", nullable = false)
	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	@Column(name = "descricao", nullable = true)
	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
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
	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	@OneToOne(fetch = FetchType.EAGER)
	public FormaDeRecebimento getFormaDeRecebimento() {
		return formaDeRecebimento;
	}

	public void setFormaDeRecebimento(FormaDeRecebimento formaDeRecebimento) {
		this.formaDeRecebimento = formaDeRecebimento;
	}

	@Column(name = "qtd_parcelas", nullable = true)
	public Integer getQtdParcelas() {
		return qtdParcelas;
	}

	public void setQtdParcelas(Integer qtdParcelas) {
		this.qtdParcelas = qtdParcelas;
	}

	@Transient
	public List<AtendimentoServico> getServicos() {
		return servicos;
	}

	public void setServicos(List<AtendimentoServico> servicos) {
		this.servicos = servicos;
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

	@Override
	public String toString() {
		return "Atendimento [id=" + id + ", data=" + data + ", descricao=" + descricao + ", valor=" + valor
				+ ", dataCriacao=" + dataCriacao + ", dataAtualizacao=" + dataAtualizacao + ", cliente=" + cliente + "]";
	}

}
