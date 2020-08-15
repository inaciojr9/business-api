package br.com.inaciojr9.businessapi.dto.atendimento;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotEmpty;

import br.com.inaciojr9.businessapi.dto.cliente.ClienteResumoDto;
import br.com.inaciojr9.businessapi.dto.empresa.EmpresaResumoDto;
import br.com.inaciojr9.businessapi.dto.formaDeRecebimento.FormaDeRecebimentoResumoDto;

public class AtendimentoDto {
	
	private Long id;
	private String data;
	private String descricao;
	private String valor;
	private ClienteResumoDto cliente;
	private EmpresaResumoDto empresa;
	private FormaDeRecebimentoResumoDto formaDeRecebimento;
	private String qtdParcelas;
	private List<AtendimentoServicoDto> servicos = new ArrayList<>();
	private String dataCriacao;
	private String dataAtualizacao;

	public AtendimentoDto() {}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@NotEmpty(message = "Data não pode ser vazia.")
	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public ClienteResumoDto getCliente() {
		return cliente;
	}

	public void setCliente(ClienteResumoDto cliente) {
		this.cliente = cliente;
	}

	public EmpresaResumoDto getEmpresa() {
		return empresa;
	}

	public void setEmpresa(EmpresaResumoDto empresa) {
		this.empresa = empresa;
	}

	public FormaDeRecebimentoResumoDto getFormaDeRecebimento() {
		return formaDeRecebimento;
	}

	public void setFormaDeRecebimento(FormaDeRecebimentoResumoDto formaDeRecebimento) {
		this.formaDeRecebimento = formaDeRecebimento;
	}

	public String getQtdParcelas() {
		return qtdParcelas;
	}

	public void setQtdParcelas(String qtdParcelas) {
		this.qtdParcelas = qtdParcelas;
	}

	public List<AtendimentoServicoDto> getServicos() {
		return servicos;
	}

	public void setServicos(List<AtendimentoServicoDto> servicos) {
		this.servicos = servicos;
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