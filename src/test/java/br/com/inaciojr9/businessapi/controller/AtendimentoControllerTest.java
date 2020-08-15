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

import br.com.inaciojr9.businessapi.config.BaseTest;
import br.com.inaciojr9.businessapi.dto.atendimento.AtendimentoDto;
import br.com.inaciojr9.businessapi.dto.cliente.ClienteResumoDto;
import br.com.inaciojr9.businessapi.dto.formaDeRecebimento.FormaDeRecebimentoResumoDto;
import br.com.inaciojr9.businessapi.model.Atendimento;
import br.com.inaciojr9.businessapi.model.Cliente;
import br.com.inaciojr9.businessapi.model.Empresa;
import br.com.inaciojr9.businessapi.model.FormaDeRecebimento;
import br.com.inaciojr9.businessapi.service.AtendimentoService;
import br.com.inaciojr9.businessapi.service.ClienteService;
import br.com.inaciojr9.businessapi.service.EmpresaService;
import br.com.inaciojr9.businessapi.service.FormaDeRecebimentoService;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class AtendimentoControllerTest extends BaseTest{

	@Autowired
	private MockMvc mvc;
	
	@MockBean
	private EmpresaService empresaService;
	
	@MockBean
	private AtendimentoService atendimentoService;
	
	@MockBean
	private ClienteService clienteService;
	
	@MockBean
	private FormaDeRecebimentoService formaDeRecebimentoService;
	
	
	
	private static final String URL_BASE = "/api/atendimentos/";
	private static final Long ID_CLIENTE = 1L;
	private static final String NOME_CLIENTE = "cliente1";
	private static final Long ID_ATENDIMENTO = 1L;
	private static final Long ID_FORMA_DE_RECEBIMENTO = 1L;
	private static final String NOME_FORMA_DE_RECEBIMENTO = "formaRec1";
	private static final Integer QTD_PARCELAS = 10;
	
	//private static final String TIPO = TipoEnum.INICIO_TRABALHO.name();
	private static final Date DATA = new Date();
	private static final BigDecimal VALOR = new BigDecimal(1000.50);
	
	
	private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	
	@Test
	@WithMockUser
	public void testCadastrarAtendimento() throws Exception {
		//Empresa empresa = obterDadosEmpresa();
		Atendimento atendimento = obterDadosAtendimento();
		BDDMockito.given(this.empresaService.buscarPorId(Mockito.anyLong())).willReturn(Optional.of(new Empresa()));
		BDDMockito.given(this.clienteService.buscarPorId(Mockito.any(Empresa.class), Mockito.anyLong())).willReturn(Optional.of(new Cliente()));
		BDDMockito.given(this.atendimentoService.persistir(Mockito.any(Empresa.class), Mockito.any(Atendimento.class))).willReturn(atendimento);
		BDDMockito.given(this.formaDeRecebimentoService.buscarPorId(Mockito.anyLong())).willReturn(Optional.of(new FormaDeRecebimento()));

		
		mvc.perform(MockMvcRequestBuilders.post(URL_BASE)
				.content(this.obterJsonRequisicaoPost())
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.data.id").value(ID_ATENDIMENTO))
				.andExpect(jsonPath("$.data.data").value(this.dateFormat.format(DATA)))
				.andExpect(jsonPath("$.data.valor").value(VALOR))
				.andExpect(jsonPath("$.data.cliente.id").value(ID_CLIENTE))
				.andExpect(jsonPath("$.data.formaDeRecebimento.id").value(ID_FORMA_DE_RECEBIMENTO))
				.andExpect(jsonPath("$.data.qtdParcelas").value(QTD_PARCELAS))
				.andExpect(jsonPath("$.errors").isEmpty());
	}
	
	@Test
	@WithMockUser
	public void testCadastrarAtendimentoClienteIdInvalido() throws Exception {
		BDDMockito.given(this.empresaService.buscarPorId(Mockito.anyLong())).willReturn(Optional.of(obterDadosEmpresa()));
		BDDMockito.given(this.atendimentoService.buscarPorId(Mockito.any(Empresa.class), Mockito.anyLong())).willReturn(Optional.empty());

		mvc.perform(MockMvcRequestBuilders.post(URL_BASE)
				.content(this.obterJsonRequisicaoPost())
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.errors").value("Cliente não encontrado. ID inexistente."))
				.andExpect(jsonPath("$.data").isEmpty());
	}
	
	@Test
	@WithMockUser
	public void testCadastrarAtendimentoFormaDeRecebimentoIdInvalido() throws Exception {
		BDDMockito.given(this.empresaService.buscarPorId(Mockito.anyLong())).willReturn(Optional.of(obterDadosEmpresa()));
		BDDMockito.given(this.clienteService.buscarPorId(Mockito.any(Empresa.class), Mockito.anyLong())).willReturn(Optional.of(new Cliente()));
		BDDMockito.given(this.atendimentoService.buscarPorId(Mockito.any(Empresa.class), Mockito.anyLong())).willReturn(Optional.empty());

		mvc.perform(MockMvcRequestBuilders.post(URL_BASE)
				.content(this.obterJsonRequisicaoPost())
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.errors").value("Forma de recebimento näo encontrada. ID inexistente."))
				.andExpect(jsonPath("$.data").isEmpty());
	}
	
	@Test
	@WithMockUser(username = "admin@admin.com", roles = {"ADMIN"})
	public void testRemoverAtendimento() throws Exception {
		BDDMockito.given(this.empresaService.buscarPorId(Mockito.anyLong())).willReturn(Optional.of(new Empresa()));
		BDDMockito.given(this.atendimentoService.buscarPorId(Mockito.any(Empresa.class), Mockito.anyLong())).willReturn(Optional.of(new Atendimento()));

		mvc.perform(MockMvcRequestBuilders.delete(URL_BASE + ID_ATENDIMENTO)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}
	
	
	@Test
	@WithMockUser
	public void testRemoverAtendimentoAcessoNegado() throws Exception {
		BDDMockito.given(this.empresaService.buscarPorId(Mockito.anyLong())).willReturn(Optional.of(new Empresa()));
		BDDMockito.given(this.atendimentoService.buscarPorId(Mockito.any(Empresa.class), Mockito.anyLong())).willReturn(Optional.of(new Atendimento()));

		mvc.perform(MockMvcRequestBuilders.delete(URL_BASE + ID_ATENDIMENTO)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isForbidden());
	}

	private String obterJsonRequisicaoPost() throws JsonProcessingException {
		AtendimentoDto atendimentoDto = new AtendimentoDto();
		atendimentoDto.setId(null);
		atendimentoDto.setData(this.dateFormat.format(DATA));
		atendimentoDto.setValor(VALOR.toString());
		atendimentoDto.setCliente(new ClienteResumoDto(ID_CLIENTE, NOME_CLIENTE));
		atendimentoDto.setFormaDeRecebimento(new FormaDeRecebimentoResumoDto(ID_FORMA_DE_RECEBIMENTO, NOME_FORMA_DE_RECEBIMENTO));
		atendimentoDto.setQtdParcelas("10");
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(atendimentoDto);
	}

	private Atendimento obterDadosAtendimento() {
		Atendimento atendimento = new Atendimento();
		atendimento.setId(ID_ATENDIMENTO);
		atendimento.setData(DATA);
		atendimento.setValor(VALOR);
		atendimento.setCliente(new Cliente());
		atendimento.getCliente().setId(ID_CLIENTE);
		atendimento.setFormaDeRecebimento(new FormaDeRecebimento());
		atendimento.getFormaDeRecebimento().setId(ID_FORMA_DE_RECEBIMENTO);
		atendimento.setQtdParcelas(QTD_PARCELAS);
		return atendimento;
	}	

}
