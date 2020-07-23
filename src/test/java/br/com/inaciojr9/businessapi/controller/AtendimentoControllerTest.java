package br.com.inaciojr9.businessapi.controller;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.inaciojr9.businessapi.dto.AtendimentoDto;
import br.com.inaciojr9.businessapi.model.Atendimento;
import br.com.inaciojr9.businessapi.model.Cliente;
import br.com.inaciojr9.businessapi.service.AtendimentoService;
import br.com.inaciojr9.businessapi.service.ClienteService;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class AtendimentoControllerTest {

	@Autowired
	private MockMvc mvc;
	
	@MockBean
	private AtendimentoService atendimentoService;
	
	@MockBean
	private ClienteService clienteService;
	
	private static final String URL_BASE = "/api/atendimentos/";
	private static final Long ID_CLIENTE = 1L;
	private static final Long ID_ATENDIMENTO = 1L;
	//private static final String TIPO = TipoEnum.INICIO_TRABALHO.name();
	private static final Date DATA = new Date();
	private static final BigDecimal VALOR = new BigDecimal(1000.50);
	
	private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	@Test
	@WithMockUser
	public void testCadastrarAtendimento() throws Exception {
		Atendimento atendimento = obterDadosAtendimento();
		BDDMockito.given(this.clienteService.buscarPorId(Mockito.anyLong())).willReturn(Optional.of(new Cliente()));
		BDDMockito.given(this.atendimentoService.persistir(Mockito.any(Atendimento.class))).willReturn(atendimento);

		mvc.perform(MockMvcRequestBuilders.post(URL_BASE)
				.content(this.obterJsonRequisicaoPost())
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.data.id").value(ID_ATENDIMENTO))
				//.andExpect(jsonPath("$.data.tipo").value(TIPO))
				.andExpect(jsonPath("$.data.data").value(this.dateFormat.format(DATA)))
				.andExpect(jsonPath("$.data.valor").value(VALOR))
				.andExpect(jsonPath("$.data.clienteId").value(ID_CLIENTE))
				.andExpect(jsonPath("$.errors").isEmpty());
	}
	
	@Test
	@WithMockUser
	public void testCadastrarAtendimentoClienteIdInvalido() throws Exception {
		BDDMockito.given(this.clienteService.buscarPorId(Mockito.anyLong())).willReturn(Optional.empty());

		mvc.perform(MockMvcRequestBuilders.post(URL_BASE)
				.content(this.obterJsonRequisicaoPost())
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.errors").value("Cliente não encontrado. ID inexistente."))
				.andExpect(jsonPath("$.data").isEmpty());
	}
	
	@Test
	@WithMockUser(username = "admin@admin.com", roles = {"ADMIN"})
	public void testRemoverAtendimento() throws Exception {
		BDDMockito.given(this.atendimentoService.buscarPorId(Mockito.anyLong())).willReturn(Optional.of(new Atendimento()));

		mvc.perform(MockMvcRequestBuilders.delete(URL_BASE + ID_ATENDIMENTO)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}
	
	
	@Test
	@WithMockUser
	public void testRemoverAtendimentoAcessoNegado() throws Exception {
		BDDMockito.given(this.atendimentoService.buscarPorId(Mockito.anyLong())).willReturn(Optional.of(new Atendimento()));

		mvc.perform(MockMvcRequestBuilders.delete(URL_BASE + ID_ATENDIMENTO)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isForbidden());
	}

	private String obterJsonRequisicaoPost() throws JsonProcessingException {
		AtendimentoDto atendimentoDto = new AtendimentoDto();
		atendimentoDto.setId(null);
		atendimentoDto.setData(this.dateFormat.format(DATA));
		atendimentoDto.setValor(VALOR.toString());
		//atendimentoDto.setTipo(TIPO);
		atendimentoDto.setClienteId(ID_CLIENTE);
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(atendimentoDto);
	}

	private Atendimento obterDadosAtendimento() {
		Atendimento atendimento = new Atendimento();
		atendimento.setId(ID_ATENDIMENTO);
		atendimento.setData(DATA);
		atendimento.setValor(VALOR);
		//atendimento.setTipo(TipoEnum.valueOf(TIPO));
		atendimento.setCliente(new Cliente());
		atendimento.getCliente().setId(ID_CLIENTE);
		return atendimento;
	}	

}
