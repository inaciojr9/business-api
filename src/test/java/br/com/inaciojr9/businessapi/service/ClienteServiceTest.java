package br.com.inaciojr9.businessapi.service;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.inaciojr9.businessapi.model.Cliente;
import br.com.inaciojr9.businessapi.model.Empresa;
import br.com.inaciojr9.businessapi.repository.ClienteRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class ClienteServiceTest {

	@MockBean
	private ClienteRepository clienteRepository;

	@Autowired
	private ClienteService clienteService;

	@Before
	public void setUp() throws Exception {
		BDDMockito.given(this.clienteRepository.save(Mockito.any(Cliente.class))).willReturn(new Cliente());
		BDDMockito.given(this.clienteRepository.findById(Mockito.anyLong())).willReturn(Optional.of(new Cliente()));
		BDDMockito.given(this.clienteRepository.findByEmail(Mockito.anyString())).willReturn(new Cliente());
		BDDMockito.given(this.clienteRepository.findByCpfAndEmpresa(Mockito.anyString(), Mockito.any(Empresa.class))).willReturn(new Cliente());
	}

	@Test
	public void testPersistirCliente() {
		Cliente cliente = this.clienteService.persistir(new Empresa(), new Cliente());

		assertNotNull(cliente);
	}

	@Test
	public void testBuscarClientePorId() {
		Optional<Cliente> cliente = this.clienteService.buscarPorId(new Empresa(), 1L);

		assertTrue(cliente.isPresent());
	}

	@Test
	public void testBuscarClientePorEmail() {
		Optional<Cliente> cliente = this.clienteService.buscarPorEmail(new Empresa(), "email@email.com");

		assertTrue(cliente.isPresent());
	}

	@Test
	public void testBuscarClientePorCpf() {
		Optional<Cliente> cliente = this.clienteService.buscarPorCpf(new Empresa(), "24291173474");

		assertTrue(cliente.isPresent());
	}
	
	@Test
	public void testPersistir() {
		Cliente cliente = this.clienteService.persistir(new Empresa(), new Cliente());
		assertNotNull(cliente);
	}

}
