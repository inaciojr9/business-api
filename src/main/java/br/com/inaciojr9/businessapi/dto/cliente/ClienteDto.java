package br.com.inaciojr9.businessapi.dto.cliente;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import br.com.inaciojr9.businessapi.dto.empresa.EmpresaResumoDto;


public class ClienteDto {
	
	private ClienteResumoDto clienteResumoDto;
	private String email;
	private String cpf;
	private String telefone;
	private String senha;
	private String perfil;
	private String dataCriacao;
	private String dataAtualizacao;
	private EmpresaResumoDto empresa;
	private String dataNascimento;
	private String sexo;
	private String sexoOutro;
	private String endereco;
	private String bairro;
	private String cidade;
	private String estado;
	private String cep;
	
	public ClienteDto() {
	}

	public ClienteResumoDto getClienteResumoDto() {
		return clienteResumoDto;
	}

	public void setClienteResumoDto(ClienteResumoDto clienteResumoDto) {
		this.clienteResumoDto = clienteResumoDto;
	}

	@NotEmpty(message = "Email não pode ser vazio.")
	@Size(min = 5, max = 255, message = "Email deve conter entre 5 e 255 caracteres.")
	@Email(message="Email inválido.")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	@NotEmpty(message = "CPF não pode ser vazio.")
	@Size(min = 11, max = 11, message = "O cpf deve conter 11 caracteres.")
	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	@NotEmpty(message = "Telefone não pode ser vazio.")
	@Size(min = 7, max = 255, message = "Telefone deve conter entre 7 e 255 caracteres.")
	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	@Size(min = 0, max = 255, message = "Perfil deve conter no máximo 255 caracteres.")
	public String getPerfil() {
		return perfil;
	}

	public void setPerfil(String perfil) {
		this.perfil = perfil;
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

	public EmpresaResumoDto getEmpresa() {
		return empresa;
	}

	public void setEmpresa(EmpresaResumoDto empresa) {
		this.empresa = empresa;
	}

	@NotEmpty(message = "A Data de Nascimento não pode ser vazia.")
	public String getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(String dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	@NotEmpty(message = "Sexo não pode ser vazio.")
	@Size(min = 1, max = 1, message = "Sexo deve conter apenas 1 caracter (M (Masculino), F (Feminino), O (Outros).")
	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	@Size(min = 0, max = 255, message = "Especifique outro sexo com no máximo 255 caracteres")
	public String getSexoOutro() {
		return sexoOutro;
	}

	public void setSexoOutro(String sexoOutro) {
		this.sexoOutro = sexoOutro;
	}

	@NotEmpty(message = "Endereco não pode ser vazio.")
	@Size(min = 0, max = 255, message = "Endereço deve conter no máximo 255 caracteres.")
	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	@NotEmpty(message = "Bairro não pode ser vazio.")
	@Size(min = 0, max = 255, message = "Bairro deve conter no máximo 255 caracteres.")
	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	@NotEmpty(message = "Cidade não pode ser vazio.")
	@Size(min = 0, max = 255, message = "Cidade deve conter no máximo 255 caracteres.")
	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	@NotEmpty(message = "Estado não pode ser vazio.")
	@Size(min = 0, max = 255, message = "Estado deve conter no máximo 255 caracteres.")
	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	@NotEmpty(message = "Cep não pode ser vazio.")
	@Size(min = 8, max = 8, message = "Cep deve conter no máximo 8 caracteres.")
	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	@Override
	public String toString() {
		return "ClienteDto [clienteResumoDto=" + clienteResumoDto + ", email=" + email + ", cpf=" + cpf + ", telefone="
				+ telefone + ", senha=" + senha + ", perfil=" + perfil + ", dataCriacao=" + dataCriacao
				+ ", dataAtualizacao=" + dataAtualizacao + ", empresa=" + empresa + ", dataNascimento=" + dataNascimento
				+ ", sexo=" + sexo + ", sexoOutro=" + sexoOutro + ", endereco=" + endereco + ", bairro=" + bairro
				+ ", cidade=" + cidade + ", estado=" + estado + ", cep=" + cep + "]";
	}

}
