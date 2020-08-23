package br.com.inaciojr9.businessapi.dto.atendimento;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotEmpty;

import br.com.inaciojr9.businessapi.dto.cliente.ClienteResumoDto;
import br.com.inaciojr9.businessapi.dto.empresa.EmpresaResumoDto;
import br.com.inaciojr9.businessapi.dto.formaDeRecebimento.FormaDeRecebimentoResumoDto;

public class AtendimentoDtoIn {
	
	private String data;
	private String descricao;
	private ClienteResumoDto cliente;
	private EmpresaResumoDto empresa;
	private FormaDeRecebimentoResumoDto formaDeRecebimento;
	private String qtdParcelas;
	private List<AtendimentoServicoDto> servicos = new ArrayList<>();

	public AtendimentoDtoIn() {}

	@NotEmpty(message = "Data não pode ser vazia.")
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
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
	
}