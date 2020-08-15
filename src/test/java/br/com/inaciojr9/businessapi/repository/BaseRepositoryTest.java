package br.com.inaciojr9.businessapi.repository;

import java.security.NoSuchAlgorithmException;
import java.util.Date;

import br.com.inaciojr9.businessapi.model.Atendimento;
import br.com.inaciojr9.businessapi.model.Cliente;
import br.com.inaciojr9.businessapi.model.Empresa;
import br.com.inaciojr9.businessapi.model.PerfilEnum;
import br.com.inaciojr9.businessapi.util.PasswordUtils;

public class BaseRepositoryTest {
	
	public static final String CNPJ = "51463645000100";
	
	public Atendimento obterDadosAtendimentos(Cliente cliente){
		Atendimento atendimento = new Atendimento();
		atendimento.setData(new Date());
		atendimento.setDescricao("limpeza de pele");
		atendimento.setCliente(cliente);
		return atendimento;
	}

	public Cliente obterDadosCliente(Empresa empresa) throws NoSuchAlgorithmException {
		Cliente cliente = new Cliente();
		cliente.setNome("Fulano de Tal");
		cliente.setPerfil(PerfilEnum.ROLE_USUARIO);
		cliente.setSenha(PasswordUtils.gerarBCrypt("123456"));
		cliente.setDataNascimento(new Date());
		cliente.setCpf("24291173474");
		cliente.setSexo("O");
		cliente.setSexoOutro("outro sexo");
		cliente.setEmail("email@email.com");
		cliente.setTelefone("12345678");
		cliente.setEndereco("endereco");
		cliente.setBairro("bairro");
		cliente.setCidade("cidade");
		cliente.setEstado("SP");
		cliente.setCep("11111111");
		
		cliente.setEmpresa(empresa);
		return cliente;
	}

	public Empresa obterDadosEmpresa() {
		Empresa empresa = new Empresa();
		empresa.setRazaoSocial("Empresa de exemplo");
		empresa.setCnpj(CNPJ);
		return empresa;
	}

}
