package br.com.inaciojr9.businessapi.repository;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

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
	private Long atendimentoId3;
	private Empresa empresa;


	@Before
	public void setUp() throws Exception {
		empresa = this.empresaRepository.save(obterDadosEmpresa());
		
		Cliente cliente = this.clienteRepository.save(obterDadosCliente(empresa));
		this.clienteId = cliente.getId();
		
		Atendimento atendimento1 = this.atendimentoRepository.save(obterDadosAtendimentos(empresa, cliente, 2020, 1, 10));
		this.atendimentoId1 = atendimento1.getId();
		Atendimento atendimento2 = this.atendimentoRepository.save(obterDadosAtendimentos(empresa, cliente, 2020, 1, 31));
		this.atendimentoId2 = atendimento2.getId();
		Atendimento atendimento3 = this.atendimentoRepository.save(obterDadosAtendimentos(empresa, cliente, 2020, 2, 15));
		this.atendimentoId3 = atendimento3.getId();
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
		
		assertEquals(3, atendimentos.size());
		assertEquals(atendimentoId1, atendimentos.get(0).getId());
		assertEquals(atendimentoId2, atendimentos.get(1).getId());
		assertEquals(atendimentoId3, atendimentos.get(2).getId());
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
		
		assertEquals(3, atendimentos.getTotalElements());
	}
	
	@Test
	public void testFindByAnoEMes() {
		List<Atendimento> atendimentos = this.atendimentoRepository.getAllPorAnoEMes(2020, 1, empresa.getId());
		assertNotNull(atendimentos);
		assertEquals(2, atendimentos.size());
	}

}
