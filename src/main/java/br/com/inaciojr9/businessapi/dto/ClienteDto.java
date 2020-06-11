package br.com.inaciojr9.businessapi.dto;
import java.util.Optional;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;


public class ClienteDto {
	
	private Long id;
	private String nome;
	private String email;
	private String cpf;
	private String telefone;
	private Optional<String> senha = Optional.empty();
	
	public ClienteDto() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@NotEmpty(message = "Nome não pode ser vazio.")
	@Size(min = 3, max = 200, message = "Nome deve conter entre 3 e 200 caracteres.")
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@NotEmpty(message = "Email não pode ser vazio.")
	@Size(min = 5, max = 200, message = "Email deve conter entre 5 e 200 caracteres.")
	@Email(message="Email inválido.")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Optional<String> getSenha() {
		return senha;
	}

	public void setSenha(Optional<String> senha) {
		this.senha = senha;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	@NotEmpty(message = "Telefone não pode ser vazio.")
	@Size(min = 5, max = 200, message = "Telefone deve conter entre 8 e 50 caracteres.")
	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	@Override
	public String toString() {
		return "ClienteDto [id=" + id + ", nome=" + nome + ", email=" + email + ", cpf=" + cpf + ", telefone="
				+ telefone + "]";
	}
	

}
