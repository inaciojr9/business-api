package br.com.inaciojr9.businessapi.config;

import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;

import br.com.inaciojr9.businessapi.dto.cliente.ClienteDto;
import br.com.inaciojr9.businessapi.dto.cliente.ClienteResumoDto;
import br.com.inaciojr9.businessapi.dto.empresa.EmpresaDto;
import br.com.inaciojr9.businessapi.dto.empresa.EmpresaResumoDto;
import br.com.inaciojr9.businessapi.model.Atendimento;
import br.com.inaciojr9.businessapi.model.Cliente;
import br.com.inaciojr9.businessapi.model.Empresa;
import br.com.inaciojr9.businessapi.model.PerfilEnum;
import br.com.inaciojr9.businessapi.util.PasswordUtils;

public class BaseTest {
	
	public static final String CNPJ = "51463645000100";
	public static final String EMPRESA_RAZAO_SOCIAL = "razao social";
	public static final Long EMPRESA_ID = 1l;
	
	protected static final Long CLIENTE_ID = Long.valueOf(1);
	protected static final String CLIENTE_CPF = "11111111111";
	protected static final String CLIENTE_NOME = "Cliente 1";
	protected static final String CLIENTE_EMAIL = "email@email.com";
	
	protected static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	public Atendimento obterDadosAtendimentos(Cliente cliente){
		Atendimento atendimento = new Atendimento();
		atendimento.setData(new Date());
		atendimento.setDescricao("limpeza de pele");
		atendimento.setCliente(cliente);
		return atendimento;
	}

	public Cliente obterDadosCliente(Empresa empresa) throws NoSuchAlgorithmException {
		Cliente cliente = new Cliente();
		cliente.setId(CLIENTE_ID);
		cliente.setNome(CLIENTE_NOME);
		cliente.setPerfil(PerfilEnum.ROLE_USUARIO);
		cliente.setSenha(PasswordUtils.gerarBCrypt("123456"));
		cliente.setDataNascimento(new Date());
		cliente.setCpf(CLIENTE_CPF);
		cliente.setSexo("O");
		cliente.setSexoOutro("outro sexo");
		cliente.setEmail(CLIENTE_EMAIL);
		cliente.setTelefone("12345678");
		cliente.setEndereco("endereco");
		cliente.setBairro("bairro");
		cliente.setCidade("cidade");
		cliente.setEstado("SP");
		cliente.setCep("11111111");
		
		cliente.setEmpresa(empresa);
		return cliente;
	}
	
	public ClienteDto obterClienteDto() throws NoSuchAlgorithmException {
		ClienteDto dto = new ClienteDto();
		dto.setClienteResumoDto(new ClienteResumoDto(CLIENTE_ID, CLIENTE_NOME));
		dto.setDataNascimento(DATE_FORMAT.format(new Date()));
		dto.setPerfil(PerfilEnum.ROLE_USUARIO.name());
		dto.setCpf(CLIENTE_CPF);
		dto.setSexo("O");
		dto.setSexoOutro("outro sexo");
		dto.setEmail(CLIENTE_EMAIL);
		dto.setTelefone("12345678");
		dto.setEndereco("endereco");
		dto.setBairro("bairro");
		dto.setCidade("cidade");
		dto.setEstado("SP");
		dto.setCep("11111111");
		dto.setSenha("123456");
		dto.setEmpresa(obterEmpresaDto().getEmpresaResumoDto());
		return dto;
	}

	public Empresa obterDadosEmpresa() {
		Empresa empresa = new Empresa();
		empresa.setId(EMPRESA_ID);
		empresa.setRazaoSocial(EMPRESA_RAZAO_SOCIAL);
		empresa.setCnpj(CNPJ);
		return empresa;
	}
	
	public EmpresaDto obterEmpresaDto() {
		EmpresaDto dto = new EmpresaDto();
		EmpresaResumoDto empresaResumoDto = new EmpresaResumoDto();
		empresaResumoDto.setId(EMPRESA_ID);
		empresaResumoDto.setRazaoSocial(EMPRESA_RAZAO_SOCIAL);
		dto.setEmpresaResumoDto(empresaResumoDto);
		dto.setCnpj(CNPJ);
		return dto;
	}

}
