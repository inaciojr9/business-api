package br.com.inaciojr9.businessapi.repository;

import java.math.BigDecimal;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import br.com.inaciojr9.businessapi.model.Atendimento;
import br.com.inaciojr9.businessapi.model.Cliente;
import br.com.inaciojr9.businessapi.model.Empresa;
import br.com.inaciojr9.businessapi.model.GestaoMensal;
import br.com.inaciojr9.businessapi.model.PerfilEnum;
import br.com.inaciojr9.businessapi.util.PasswordUtils;

public class BaseRepositoryTest {
	
	public static final String CNPJ = "51463645000100";
	
	public Atendimento obterDadosAtendimentos(Empresa empresa, Cliente cliente, Integer ano, Integer mes, Integer dia){
		Atendimento atendimento = new Atendimento();
		try {
			atendimento.setData(new SimpleDateFormat("yyyy-MM-dd").parse(ano.toString()+"-"+mes.toString()+"-"+dia.toString()));
		} catch (ParseException e) {
			// nunca deveria lançar essa exceção
			e.printStackTrace();
		}
		atendimento.setDescricao("limpeza de pele");
		atendimento.setCliente(cliente);
		atendimento.setEmpresa(empresa);
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
	
	public GestaoMensal obterDadosGestaoMensal(Long id, Integer ano, Integer mes, 
			BigDecimal receitaProduto, BigDecimal receitaServico,
			BigDecimal despesaTotal, BigDecimal meta, BigDecimal percentualComissao, Empresa empresa){
		
		GestaoMensal gestaoMensal = new GestaoMensal(
				id, 
				ano, 
				mes,
				receitaProduto, 
				receitaServico, 
				despesaTotal, 
				meta, 
				percentualComissao, 
				empresa);
		
		return gestaoMensal;
	}

	public Empresa obterDadosEmpresa() {
		Empresa empresa = new Empresa();
		empresa.setRazaoSocial("Empresa de exemplo");
		empresa.setCnpj(CNPJ);
		return empresa;
	}

}
