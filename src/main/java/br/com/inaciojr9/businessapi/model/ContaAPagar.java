package br.com.inaciojr9.businessapi.model;

import java.io.Serializable;
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
@Table(name = "contas_a_pagar")
public class ContaAPagar implements Serializable {

	private static final long serialVersionUID = -6958174588788984860L;
	
	private Long id;
	private Date data;
	private BigDecimal valor;
	private Boolean paga;
	private Empresa empresa;
	private Date dataCriacao;
	private Date dataAtualizacao;
	private Despesa despesa;
	
	public ContaAPagar() {}

	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	public Empresa getEmpresa() {
		return empresa;
	}

	@Column(name = "data", nullable = false)
	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	@Column(name = "valor", nullable = false)
	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	@Column(name = "paga", nullable = false)
	public Boolean getPaga() {
		return paga;
	}

	public void setPaga(Boolean paga) {
		this.paga = paga;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	public Despesa getDespesa() {
		return despesa;
	}

	public void setDespesa(Despesa despesa) {
		this.despesa = despesa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
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
		return "ContaAPagar [id=" + id + ", data=" + data + ", valor=" + valor + ", paga=" + paga + ", empresa="
				+ empresa + ", dataCriacao=" + dataCriacao + ", dataAtualizacao=" + dataAtualizacao + ", despesa="
				+ despesa + "]";
	}
}
