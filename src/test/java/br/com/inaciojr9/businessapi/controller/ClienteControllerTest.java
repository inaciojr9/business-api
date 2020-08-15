package br.com.inaciojr9.businessapi.controller;


import static org.hamcrest.CoreMatchers.equalTo;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.security.NoSuchAlgorithmException;
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
import br.com.inaciojr9.businessapi.model.Cliente;
import br.com.inaciojr9.businessapi.model.Empresa;
import br.com.inaciojr9.businessapi.service.ClienteService;
import br.com.inaciojr9.businessapi.service.EmpresaService;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class ClienteControllerTest extends BaseTest{

	@Autowired
	private MockMvc mvc;
	
	@MockBean
	private EmpresaService empresaService;

	@MockBean
	private ClienteService clienteService;

	private static final String URL_BASE = "/api/clientes/";
	private static final String BUSCAR_CLIENTE_CPF_URL = URL_BASE + "cpf/";
	private static final String BUSCAR_CLIENTE_EMAIL_URL = URL_BASE + "email/";
	
	@Test
	@WithMockUser
	public void testBuscarClienteCpfInvalido() throws Exception {
		BDDMockito.given(this.empresaService.buscarPorId(Mockito.anyLong())).willReturn(Optional.of(new Empresa()));
		BDDMockito.given(this.clienteService.buscarPorCpf(Mockito.any(Empresa.class), Mockito.anyString())).willReturn(Optional.empty());

		mvc.perform(MockMvcRequestBuilders.get(BUSCAR_CLIENTE_CPF_URL + CLIENTE_CPF).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.errors").value("Cliente não encontrada para o CPF " + CLIENTE_CPF));
	}

	@Test
	@WithMockUser
	public void testBuscarClienteCpfValido() throws Exception {
		BDDMockito.given(this.empresaService.buscarPorId(Mockito.anyLong())).willReturn(Optional.of(new Empresa()));
		BDDMockito.given(this.clienteService.buscarPorCpf(Mockito.any(Empresa.class),Mockito.anyString()))
				.willReturn(Optional.of(this.obterDadosCliente(obterDadosEmpresa())));

		mvc.perform(MockMvcRequestBuilders.get(BUSCAR_CLIENTE_CPF_URL + CLIENTE_CPF)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.data.clienteResumoDto.id").value(CLIENTE_ID))
				.andExpect(jsonPath("$.data.clienteResumoDto.nome", equalTo(CLIENTE_NOME)))
				.andExpect(jsonPath("$.data.cpf", equalTo(CLIENTE_CPF)))
				.andExpect(jsonPath("$.errors").isEmpty());
	}
	
	@Test
	@WithMockUser
	public void testBuscarClienteEmailInvalido() throws Exception {
		BDDMockito.given(this.empresaService.buscarPorId(Mockito.anyLong())).willReturn(Optional.of(new Empresa()));
		BDDMockito.given(this.clienteService.buscarPorEmail(Mockito.any(Empresa.class), Mockito.anyString())).willReturn(Optional.empty());

		mvc.perform(MockMvcRequestBuilders.get(BUSCAR_CLIENTE_EMAIL_URL + CLIENTE_EMAIL).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.errors").value("Cliente não encontrado para o email " + CLIENTE_EMAIL));
	}

	@Test
	@WithMockUser
	public void testBuscarClienteEmailValido() throws Exception {
		BDDMockito.given(this.empresaService.buscarPorId(Mockito.anyLong())).willReturn(Optional.of(new Empresa()));
		BDDMockito.given(this.clienteService.buscarPorEmail(Mockito.any(Empresa.class),Mockito.anyString()))
				.willReturn(Optional.of(this.obterDadosCliente(obterDadosEmpresa())));

		mvc.perform(MockMvcRequestBuilders.get(BUSCAR_CLIENTE_EMAIL_URL + CLIENTE_EMAIL)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.data.clienteResumoDto.id").value(CLIENTE_ID))
				.andExpect(jsonPath("$.data.clienteResumoDto.nome", equalTo(CLIENTE_NOME)))
				.andExpect(jsonPath("$.data.cpf", equalTo(CLIENTE_CPF)))
				.andExpect(jsonPath("$.errors").isEmpty());
	}
	
	@Test
	@WithMockUser
	public void testCadastrarCliente() throws Exception {
		Empresa empresa = obterDadosEmpresa();
		Cliente cliente = obterDadosCliente(empresa);
		BDDMockito.given(this.empresaService.buscarPorId(Mockito.anyLong())).willReturn(Optional.of(empresa));
		BDDMockito.given(this.clienteService.buscarPorId(Mockito.any(Empresa.class), Mockito.anyLong())).willReturn(Optional.of(cliente));
		BDDMockito.given(this.clienteService.persistir(Mockito.any(Empresa.class), Mockito.any(Cliente.class))).willReturn(cliente);

		mvc.perform(MockMvcRequestBuilders.post(URL_BASE)
				.content(this.obterJsonRequisicaoPost())
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.data.clienteResumoDto.id").value(CLIENTE_ID))
				//.andExpect(jsonPath("$.data.tipo").value(TIPO))
				
				/*
				.andExpect(jsonPath("$.data.data").value(this.dateFormat.format(DATA)))
				.andExpect(jsonPath("$.data.valor").value(VALOR))
				.andExpect(jsonPath("$.data.clienteId").value(ID_CLIENTE))
				*/
				.andExpect(jsonPath("$.errors").isEmpty());
	}
	
	private String obterJsonRequisicaoPost() throws JsonProcessingException, NoSuchAlgorithmException {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(obterClienteDto());
	}
	

}
