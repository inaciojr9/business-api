package br.com.inaciojr9.businessapi.repository;
import static org.junit.Assert.assertEquals;

import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.inaciojr9.businessapi.model.Atendimento;
import br.com.inaciojr9.businessapi.model.Cliente;
import br.com.inaciojr9.businessapi.model.Empresa;
import br.com.inaciojr9.businessapi.model.PerfilEnum;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class AtendimentoRepositoryTest {
	
	@Autowired
	private AtendimentoRepository atendimentoRepository;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private EmpresaRepository empresaRepository;
	
	private Long clienteId;

	@Before
	public void setUp() throws Exception {
		Empresa empresa = this.empresaRepository.save(obterDadosEmpresa());
		
		Cliente cliente = this.clienteRepository.save(obterDadosCliente(empresa));
		this.clienteId = cliente.getId();
		
		this.atendimentoRepository.save(obterDadosAtendimentos(cliente));
		this.atendimentoRepository.save(obterDadosAtendimentos(cliente));
	}

	@After
	public void tearDown() throws Exception {
		this.empresaRepository.deleteAll();
	}

	@Test
	public void testBuscarAtendimentosPorClienteId() {
		List<Atendimento> atendimentos = this.atendimentoRepository.findByClienteId(clienteId);
		
		assertEquals(2, atendimentos.size());
	}
	
	@Test
	public void testBuscarAtendimentosPorClienteIdPaginado() {
		PageRequest page = PageRequest.of(0, 10);
		
		Page<Atendimento> atendimentos = this.atendimentoRepository.findByClienteId(clienteId, page);
		
		assertEquals(2, atendimentos.getTotalElements());
	}
	
	private Atendimento obterDadosAtendimentos(Cliente cliente){
		Atendimento atendimento = new Atendimento();
		atendimento.setData(new Date());
		atendimento.setDescricao("limpeza de pele");
		atendimento.setCliente(cliente);
		return atendimento;
	}

	private Cliente obterDadosCliente(Empresa empresa) throws NoSuchAlgorithmException {
		Cliente cliente = new Cliente();
		cliente.setNome("Fulano de Tal");
		cliente.setPerfil(PerfilEnum.ROLE_USUARIO);
		//cliente.setSenha(PasswordUtils.gerarBCrypt("123456"));
		cliente.setSenha("123456");
		cliente.setCpf("24291173474");
		cliente.setEmail("email@email.com");
		cliente.setEmpresa(empresa);
		return cliente;
	}

	private Empresa obterDadosEmpresa() {
		Empresa empresa = new Empresa();
		empresa.setRazaoSocial("Empresa de exemplo");
		empresa.setCnpj("51463645000100");
		return empresa;
	}

}
