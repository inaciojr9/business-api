package br.com.inaciojr9.businessapi.dto.cliente;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class ClienteResumoDto {
	
	private Long id;
	private String nome;
	
	public ClienteResumoDto(Long id, String nome) {
		this.id = id;
		this.nome = nome;
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	@NotEmpty(message = "Nome não pode ser vazio.")
	@Size(min = 3, max = 255, message = "Nome deve conter entre 3 e 255 caracteres.")
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	@Override
	public String toString() {
		return "ClienteResumoDto [id=" + id + ", nome=" + nome + "]";
	}

}
