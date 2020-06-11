package br.com.inaciojr9.businessapi.dto;

import java.util.Optional;

import javax.validation.constraints.NotEmpty;

public class AtendimentoDto {
	
	private Optional<Long> id = Optional.empty();
	private String data;
	private String descricao;
	private String valor;
	private Long clienteId;

	public AtendimentoDto() {
	}

	public Optional<Long> getId() {
		return id;
	}

	public void setId(Optional<Long> id) {
		this.id = id;
	}

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

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	public Long getClienteId() {
		return clienteId;
	}

	public void setClienteId(Long clienteId) {
		this.clienteId = clienteId;
	}

	@Override
	public String toString() {
		return "AtendimentoDto [id=" + id + ", data=" + data + ", descricao=" + descricao + ", valor=" + valor
				+ ", clienteId=" + clienteId + "]";
	}
	
}