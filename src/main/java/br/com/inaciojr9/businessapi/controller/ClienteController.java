package br.com.inaciojr9.businessapi.controller;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.inaciojr9.businessapi.dto.ClienteDto;
import br.com.inaciojr9.businessapi.helper.web.Response;
import br.com.inaciojr9.businessapi.model.Cliente;
import br.com.inaciojr9.businessapi.service.ClienteService;

@RestController
@RequestMapping("/api/clientes")
@CrossOrigin(origins = "*")
public class ClienteController {

	private static final Logger log = LoggerFactory.getLogger(ClienteController.class);

	@Autowired
	private ClienteService clienteService;

	public ClienteController() {
	}
	
	@GetMapping(value = "/{id}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<Response<ClienteDto>> consultar(@PathVariable("id") Long id) throws NoSuchAlgorithmException {
		log.info("Obtendo cliente: {}", id);
		Response<ClienteDto> response = new Response<ClienteDto>();

		Optional<Cliente> cliente = this.clienteService.buscarPorId(id);
		
		if (!cliente.isPresent()) {
			log.info("Cliente não encontrado para o ID: {}", id);
			response.getErrors().add("Cliente não encontrado para o id " + id);
			return ResponseEntity.badRequest().body(response);
		}

		response.setData(this.converterClienteDto(cliente.get()));
		return ResponseEntity.ok(response);
	}

	/**
	 * Atualiza os dados de um cliente.
	 * 
	 * @param id
	 * @param clienteDto
	 * @param result
	 * @return ResponseEntity<Response<ClienteDto>>
	 * @throws NoSuchAlgorithmException
	 */
	@PutMapping(value = "/{id}")
	public ResponseEntity<Response<ClienteDto>> atualizar(@PathVariable("id") Long id,
			@Valid @RequestBody ClienteDto clienteDto, BindingResult result) throws NoSuchAlgorithmException {
		log.info("Atualizando cliente: {}", clienteDto.toString());
		Response<ClienteDto> response = new Response<ClienteDto>();

		Optional<Cliente> cliente = this.clienteService.buscarPorId(id);
		if (!cliente.isPresent()) {
			result.addError(new ObjectError("cliente", "Cliente não encontrado."));
		}

		this.atualizarDadosCliente(cliente.get(), clienteDto, result);

		if (result.hasErrors()) {
			log.error("Erro validando cliente: {}", result.getAllErrors());
			result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}

		this.clienteService.persistir(cliente.get());
		response.setData(this.converterClienteDto(cliente.get()));

		return ResponseEntity.ok(response);
	}

	/**
	 * Atualiza os dados do cliente com base nos dados encontrados no DTO.
	 * 
	 * @param cliente
	 * @param clienteDto
	 * @param result
	 * @throws NoSuchAlgorithmException
	 */
	private void atualizarDadosCliente(Cliente cliente, ClienteDto clienteDto, BindingResult result)
			throws NoSuchAlgorithmException {
		cliente.setNome(clienteDto.getNome());

		if (!cliente.getEmail().equals(clienteDto.getEmail())) {
			this.clienteService.buscarPorEmail(clienteDto.getEmail())
					.ifPresent(func -> result.addError(new ObjectError("email", "Email já existente.")));
			cliente.setEmail(clienteDto.getEmail());
		}
		
		cliente.setCpf(clienteDto.getCpf());
		cliente.setTelefone(clienteDto.getTelefone());
		

		/*
		 * cliente.setQtdHorasAlmoco(null); clienteDto.getQtdHorasAlmoco()
		 * .ifPresent(qtdHorasAlmoco ->
		 * cliente.setQtdHorasAlmoco(Float.valueOf(qtdHorasAlmoco)));
		 * 
		 * cliente.setQtdHorasTrabalhoDia(null); clienteDto.getQtdHorasTrabalhoDia()
		 * .ifPresent(qtdHorasTrabDia ->
		 * cliente.setQtdHorasTrabalhoDia(Float.valueOf(qtdHorasTrabDia)));
		 * 
		 * cliente.setValorHora(null); clienteDto.getValorHora().ifPresent(valorHora ->
		 * cliente.setValorHora(new BigDecimal(valorHora)));
		 * 
		 * if (clienteDto.getSenha().isPresent()) {
		 * cliente.setSenha(PasswordUtils.gerarBCrypt(clienteDto.getSenha().get())); }
		 */
	}

	/**
	 * Retorna um DTO com os dados de um cliente.
	 * 
	 * @param cliente
	 * @return ClienteDto
	 */
	private ClienteDto converterClienteDto(Cliente cliente) {
		ClienteDto clienteDto = new ClienteDto();
		clienteDto.setId(cliente.getId());
		clienteDto.setEmail(cliente.getEmail());
		clienteDto.setNome(cliente.getNome());
		clienteDto.setCpf(cliente.getCpf());
		clienteDto.setTelefone(cliente.getTelefone());
		
		/*
		 * cliente.getQtdHorasAlmocoOpt().ifPresent( qtdHorasAlmoco ->
		 * clienteDto.setQtdHorasAlmoco(Optional.of(Float.toString(qtdHorasAlmoco))));
		 * cliente.getQtdHorasTrabalhoDiaOpt().ifPresent( qtdHorasTrabDia ->
		 * clienteDto.setQtdHorasTrabalhoDia(Optional.of(Float.toString(qtdHorasTrabDia)
		 * ))); cliente.getValorHoraOpt() .ifPresent(valorHora ->
		 * clienteDto.setValorHora(Optional.of(valorHora.toString())));
		 */
		return clienteDto;
	}

}
