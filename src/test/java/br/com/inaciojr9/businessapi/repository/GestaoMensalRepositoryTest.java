package br.com.inaciojr9.businessapi.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.math.BigDecimal;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.inaciojr9.businessapi.model.Empresa;
import br.com.inaciojr9.businessapi.model.GestaoMensal;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class GestaoMensalRepositoryTest extends BaseRepositoryTest {
	
	@Autowired
	private GestaoMensalRepository gestaoMensalRepository;
	
	@Autowired
	private EmpresaRepository empresaRepository;
	
	private Empresa empresa;


	@Before
	public void setUp() throws Exception {
		empresa = this.empresaRepository.save(obterDadosEmpresa());
		
		
		BigDecimal meta = new BigDecimal(100);
		BigDecimal despesaTotal = new BigDecimal(90);
		BigDecimal percentualComissao = new BigDecimal(15);
		BigDecimal receitaProduto = new BigDecimal(80);
		BigDecimal receitaServico = new BigDecimal(101);
		
		GestaoMensal gm1 = obterDadosGestaoMensal(1l, 
				2020, 1,
				receitaProduto, 
				receitaServico, 
				despesaTotal, 
				meta, 
				percentualComissao, 
				empresa);
		gestaoMensalRepository.save(gm1);
		
		GestaoMensal gm2 = new GestaoMensal(
				2l, 
				2020, 2,
				receitaProduto, 
				receitaServico, 
				despesaTotal, 
				meta, 
				percentualComissao, 
				empresa);
		gestaoMensalRepository.save(gm2);
		
		GestaoMensal gm3 = new GestaoMensal(
				3l, 
				2020, 3,
				receitaProduto, 
				receitaServico, 
				despesaTotal, 
				meta, 
				percentualComissao, 
				empresa);
		gestaoMensalRepository.save(gm3);
		
		GestaoMensal gm4 = new GestaoMensal(
				4l, 
				2020, 4,
				receitaProduto, 
				receitaServico, 
				despesaTotal, 
				meta, 
				percentualComissao, 
				empresa);
		gestaoMensalRepository.save(gm4);
		
	}

	@After
	public void tearDown() throws Exception {
		this.gestaoMensalRepository.deleteAll();
		this.empresaRepository.deleteAll();
	}

	@Test
	public void testFindByEmpresaAndAnoAndMes() {
		GestaoMensal gm = this.gestaoMensalRepository.findByEmpresaAndAnoAndMes(empresa, 2020, 1);
		assertNotNull(gm);
		assertEquals(1l, gm.getId().longValue());
	}
	
	
}
