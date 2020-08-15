package br.com.inaciojr9.businessapi.service;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.inaciojr9.businessapi.model.Atendimento;
import br.com.inaciojr9.businessapi.model.Empresa;
import br.com.inaciojr9.businessapi.repository.AtendimentoRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class AtendimentoServiceTest {

	@MockBean
	private AtendimentoRepository atendimentoRepository;

	@Autowired
	private AtendimentoService atendimentoService;

	@Before
	public void setUp() throws Exception {
		BDDMockito
				.given(this.atendimentoRepository.findByClienteId(Mockito.anyLong(), Mockito.any(PageRequest.class)))
				.willReturn(new PageImpl<Atendimento>(new ArrayList<Atendimento>()));
		BDDMockito.given(this.atendimentoRepository.findById(Mockito.anyLong())).willReturn(Optional.of(new Atendimento()));
		BDDMockito.given(this.atendimentoRepository.save(Mockito.any(Atendimento.class))).willReturn(new Atendimento());
	}

	@Test
	public void testBuscarAtendimentoPorClienteId() {
		Page<Atendimento> atendimento = this.atendimentoService.buscarPorClienteId(1L, PageRequest.of(0, 10));

		assertNotNull(atendimento);
	}

	@Test
	public void testBuscarAtendimentoPorId() {
		Optional<Atendimento> atendimento = this.atendimentoService.buscarPorId(new Empresa(), 1L);

		assertTrue(atendimento.isPresent());
	}

	@Test
	public void testPersistirAtendimento() {
		Atendimento atendimento = this.atendimentoService.persistir(new Empresa(), new Atendimento());

		assertNotNull(atendimento);
	}

}
