package br.com.inaciojr9.businessapi.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.inaciojr9.businessapi.model.Cliente;
import br.com.inaciojr9.businessapi.model.Empresa;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class ClienteRepositoryTest extends BaseRepositoryTest{

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private EmpresaRepository empresaRepository;

	private static final String EMAIL = "email@email.com";
	private static final String CPF = "24291173474";

	@Before
	public void setUp() throws Exception {
		Empresa empresa = this.empresaRepository.save(obterDadosEmpresa());
		this.clienteRepository.save(obterDadosCliente(empresa));
	}

	@After
	public final void tearDown() {
		this.empresaRepository.deleteAll();
	}

	@Test
	public void testBuscarClientePorEmail() {
		Cliente cliente = this.clienteRepository.findByEmail(EMAIL);

		assertEquals(EMAIL, cliente.getEmail());
	}

	@Test
	public void testBuscarClientePorCpf() {
		
		Cliente cliente = this.clienteRepository.findByCpfAndEmpresa(CPF, this.empresaRepository.findByCnpj(CNPJ));

		assertEquals(CPF, cliente.getCpf());
	}

	@Test
	public void testBuscarClientePorEmailECpf() {
		Cliente cliente = this.clienteRepository.findByCpfOrEmail(CPF, EMAIL);

		assertNotNull(cliente);
	}

	@Test
	public void testBuscarClientePorEmailOuCpfParaEmailInvalido() {
		Cliente cliente = this.clienteRepository.findByCpfOrEmail(CPF, "email@invalido.com");

		assertNotNull(cliente);
	}

	@Test
	public void testBuscarClientePorEmailECpfParaCpfInvalido() {
		Cliente cliente = this.clienteRepository.findByCpfOrEmail("12345678901", EMAIL);

		assertNotNull(cliente);
	}

	/*
	 * private Cliente obterDadosCliente(Empresa empresa) throws
	 * NoSuchAlgorithmException { Cliente cliente = new Cliente();
	 * cliente.setNome("Fulano de Tal"); cliente.setPerfil(PerfilEnum.ROLE_USUARIO);
	 * //cliente.setSenha(PasswordUtils.gerarBCrypt("123456"));
	 * cliente.setSenha("123456"); cliente.setCpf(CPF); cliente.setEmail(EMAIL);
	 * cliente.setEmpresa(empresa); return cliente; }
	 * 
	 * private Empresa obterDadosEmpresa() { Empresa empresa = new Empresa();
	 * empresa.setRazaoSocial("Empresa de exemplo");
	 * empresa.setCnpj("51463645000100"); return empresa; }
	 */

}
