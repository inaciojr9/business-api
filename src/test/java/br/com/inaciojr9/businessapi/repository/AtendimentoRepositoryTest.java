package br.com.inaciojr9.businessapi.repository;
import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.Optional;

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

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class AtendimentoRepositoryTest extends BaseRepositoryTest {
	
	@Autowired
	private AtendimentoRepository atendimentoRepository;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private EmpresaRepository empresaRepository;
	
	private Long clienteId;
	private Long atendimentoId1;
	private Long atendimentoId2;


	@Before
	public void setUp() throws Exception {
		Empresa empresa = this.empresaRepository.save(obterDadosEmpresa());
		
		Cliente cliente = this.clienteRepository.save(obterDadosCliente(empresa));
		this.clienteId = cliente.getId();
		
		Atendimento atendimento1 = this.atendimentoRepository.save(obterDadosAtendimentos(cliente));
		this.atendimentoId1 = atendimento1.getId();
		Atendimento atendimento2 = this.atendimentoRepository.save(obterDadosAtendimentos(cliente));
		this.atendimentoId2 = atendimento2.getId();
	}

	@After
	public void tearDown() throws Exception {
		this.atendimentoRepository.deleteAll();
		this.clienteRepository.deleteAll();
		this.empresaRepository.deleteAll();
	}

	@Test
	public void testBuscarAtendimentosPorClienteId() {
		List<Atendimento> atendimentos = this.atendimentoRepository.findByClienteId(clienteId);
		
		assertEquals(2, atendimentos.size());
		assertEquals(atendimentoId1, atendimentos.get(0).getId());
		assertEquals(atendimentoId2, atendimentos.get(1).getId());
	}
	
	@Test
	public void testBuscarAtendimentosPorId() {
		Optional<Atendimento> atendimento = this.atendimentoRepository.findById(atendimentoId1);
		
		assertEquals(atendimentoId1, atendimento.get().getId());
	}
	
	@Test
	public void testBuscarAtendimentosPorClienteIdPaginado() {
		PageRequest page = PageRequest.of(0, 10);
		
		Page<Atendimento> atendimentos = this.atendimentoRepository.findByClienteId(clienteId, page);
		
		assertEquals(2, atendimentos.getTotalElements());
	}
	
	/*
	 * private Atendimento obterDadosAtendimentos(Cliente cliente){ Atendimento
	 * atendimento = new Atendimento(); atendimento.setData(new Date());
	 * atendimento.setDescricao("limpeza de pele"); atendimento.setCliente(cliente);
	 * return atendimento; }
	 * 
	 * private Cliente obterDadosCliente(Empresa empresa) throws
	 * NoSuchAlgorithmException { Cliente cliente = new Cliente();
	 * cliente.setNome("Fulano de Tal"); cliente.setPerfil(PerfilEnum.ROLE_USUARIO);
	 * cliente.setSenha(PasswordUtils.gerarBCrypt("123456"));
	 * cliente.setDataNascimento(new Date()); cliente.setCpf("24291173474");
	 * cliente.setSexo("O"); cliente.setSexoOutro("outro sexo");
	 * cliente.setEmail("email@email.com"); cliente.setTelefone("12345678");
	 * cliente.setEndereco("endereco"); cliente.setBairro("bairro");
	 * cliente.setCidade("cidade"); cliente.setEstado("SP");
	 * cliente.setCep("11111111");
	 * 
	 * cliente.setEmpresa(empresa); return cliente; }
	 * 
	 * private Empresa obterDadosEmpresa() { Empresa empresa = new Empresa();
	 * empresa.setRazaoSocial("Empresa de exemplo");
	 * empresa.setCnpj("51463645000100"); return empresa; }
	 */

}
