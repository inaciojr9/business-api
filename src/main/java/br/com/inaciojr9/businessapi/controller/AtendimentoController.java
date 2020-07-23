package br.com.inaciojr9.businessapi.controller;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.inaciojr9.businessapi.dto.AtendimentoDto;
import br.com.inaciojr9.businessapi.helper.web.Response;
import br.com.inaciojr9.businessapi.model.Atendimento;
import br.com.inaciojr9.businessapi.model.Cliente;
import br.com.inaciojr9.businessapi.service.AtendimentoService;
import br.com.inaciojr9.businessapi.service.ClienteService;

@RestController
@RequestMapping("/api/atendimentos")
@CrossOrigin(origins = "*")
public class AtendimentoController {

	private static final Logger log = LoggerFactory.getLogger(AtendimentoController.class);
	private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@Autowired
	private AtendimentoService atendimentoService;

	@Autowired
	private ClienteService clienteService;
	
	@Value("${paginacao.qtd_por_pagina}")
	private int qtdPorPagina;

	public AtendimentoController() {
	}

	/**
	 * Retorna a listagem de atendimentos de um cliente.
	 * 
	 * @param clienteId
	 * @return ResponseEntity<Response<AtendimentoDto>>
	 */
	@GetMapping(value = "/cliente/{clienteId}")
	public ResponseEntity<Response<Page<AtendimentoDto>>> listarPorClienteId(
			@PathVariable("clienteId") Long clienteId,
			@RequestParam(value = "pag", defaultValue = "0") int pag,
			@RequestParam(value = "ord", defaultValue = "id") String ord,
			@RequestParam(value = "dir", defaultValue = "DESC") String dir) {
		log.info("Buscando atendimentos por ID do cliente: {}, página: {}", clienteId, pag);
		Response<Page<AtendimentoDto>> response = new Response<Page<AtendimentoDto>>();

		PageRequest pageRequest = PageRequest.of(pag, this.qtdPorPagina, Direction.valueOf(dir), ord);
		Page<Atendimento> atendimentos = this.atendimentoService.buscarPorClienteId(clienteId, pageRequest);
		Page<AtendimentoDto> atendimentosDto = atendimentos.map(atendimento -> this.converterAtendimentoDto(atendimento));

		response.setData(atendimentosDto);
		return ResponseEntity.ok(response);
	}

	/**
	 * Retorna um atendimento por ID.
	 * 
	 * @param id
	 * @return ResponseEntity<Response<AtendimentoDto>>
	 */
	@GetMapping(value = "/{id}")
	public ResponseEntity<Response<AtendimentoDto>> listarPorId(@PathVariable("id") Long id) {
		log.info("Buscando atendimento por ID: {}", id);
		Response<AtendimentoDto> response = new Response<AtendimentoDto>();
		Optional<Atendimento> atendimento = this.atendimentoService.buscarPorId(id);

		if (!atendimento.isPresent()) {
			log.info("Atendimento não encontrado para o ID: {}", id);
			response.getErrors().add("Atendimento não encontrado para o id " + id);
			return ResponseEntity.badRequest().body(response);
		}

		response.setData(this.converterAtendimentoDto(atendimento.get()));
		return ResponseEntity.ok(response);
	}

	/**
	 * Adiciona um novo atendimento.
	 * 
	 * @param atendimento
	 * @param result
	 * @return ResponseEntity<Response<AtendimentoDto>>
	 * @throws ParseException 
	 */
	@PostMapping
	public ResponseEntity<Response<AtendimentoDto>> adicionar(@Valid @RequestBody AtendimentoDto atendimentoDto,
			BindingResult result) throws ParseException {
		log.info("Adicionando atendimento: {}", atendimentoDto.toString());
		Response<AtendimentoDto> response = new Response<AtendimentoDto>();
		validarCliente(atendimentoDto, result);
		Atendimento atendimento = this.converterDtoParaAtendimento(atendimentoDto, result);

		if (result.hasErrors()) {
			log.error("Erro validando atendimento: {}", result.getAllErrors());
			result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}

		atendimento = this.atendimentoService.persistir(atendimento);
		response.setData(this.converterAtendimentoDto(atendimento));
		return ResponseEntity.ok(response);
	}

	/**
	 * Atualiza os dados de um atendimento.
	 * 
	 * @param id
	 * @param atendimentoDto
	 * @return ResponseEntity<Response<Atendimento>>
	 * @throws ParseException 
	 */
	@PutMapping(value = "/{id}")
	public ResponseEntity<Response<AtendimentoDto>> atualizar(@PathVariable("id") Long id,
			@Valid @RequestBody AtendimentoDto atendimentoDto, BindingResult result) throws ParseException {
		log.info("Atualizando atendimento: {}", atendimentoDto.toString());
		Response<AtendimentoDto> response = new Response<AtendimentoDto>();
		validarCliente(atendimentoDto, result);
		atendimentoDto.setId(Optional.of(id));
		Atendimento atendimento = this.converterDtoParaAtendimento(atendimentoDto, result);

		if (result.hasErrors()) {
			log.error("Erro validando atendimento: {}", result.getAllErrors());
			result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}

		atendimento = this.atendimentoService.persistir(atendimento);
		response.setData(this.converterAtendimentoDto(atendimento));
		return ResponseEntity.ok(response);
	}

	/**
	 * Remove um atendimento por ID.
	 * 
	 * @param id
	 * @return ResponseEntity<Response<Atendimento>>
	 */
	@DeleteMapping(value = "/{id}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<Response<String>> remover(@PathVariable("id") Long id) {
		log.info("Removendo atendimento: {}", id);
		Response<String> response = new Response<String>();
		Optional<Atendimento> atendimento = this.atendimentoService.buscarPorId(id);

		if (!atendimento.isPresent()) {
			log.info("Erro ao remover devido ao atendimento ID: {} ser inválido.", id);
			response.getErrors().add("Erro ao remover atendimento. Registro não encontrado para o id " + id);
			return ResponseEntity.badRequest().body(response);
		}

		this.atendimentoService.remover(id);
		return ResponseEntity.ok(new Response<String>());
	}

	/**
	 * Valida um cliente, verificando se ele é existente e válido no
	 * sistema.
	 * 
	 * @param atendimentoDto
	 * @param result
	 */
	private void validarCliente(AtendimentoDto atendimentoDto, BindingResult result) {
		if (atendimentoDto.getClienteId() == null) {
			result.addError(new ObjectError("cliente", "Cliente não informado."));
			return;
		}

		log.info("Validando cliente id {}: ", atendimentoDto.getClienteId());
		Optional<Cliente> cliente = this.clienteService.buscarPorId(atendimentoDto.getClienteId());
		if (!cliente.isPresent()) {
			result.addError(new ObjectError("cliente", "Cliente não encontrado. ID inexistente."));
		}
	}

	/**
	 * Converte uma entidade atendimento para seu respectivo DTO.
	 * 
	 * @param atendimento
	 * @return AtendimentoDto
	 */
	private AtendimentoDto converterAtendimentoDto(Atendimento atendimento) {
		AtendimentoDto atendimentoDto = new AtendimentoDto();
		atendimentoDto.setId(Optional.of(atendimento.getId()));
		atendimentoDto.setData(this.dateFormat.format(atendimento.getData()));
		atendimentoDto.setDescricao(atendimento.getDescricao());
		atendimentoDto.setClienteId(atendimento.getCliente().getId());
		atendimentoDto.setValor(atendimento.getValor().toString());

		return atendimentoDto;
	}

	/**
	 * Converte um AtendimentoDto para uma entidade Atendimento.
	 * 
	 * @param atendimentoDto
	 * @param result
	 * @return Atendimento
	 * @throws ParseException 
	 */
	private Atendimento converterDtoParaAtendimento(AtendimentoDto atendimentoDto, BindingResult result) throws ParseException {
		Atendimento atendimento = new Atendimento();

		if (atendimentoDto.getId().isPresent()) {
			Optional<Atendimento> lanc = this.atendimentoService.buscarPorId(atendimentoDto.getId().get());
			if (lanc.isPresent()) {
				atendimento = lanc.get();
			} else {
				result.addError(new ObjectError("atendimento", "Atendimento não encontrado."));
			}
		} else {
			atendimento.setCliente(new Cliente());
			atendimento.getCliente().setId(atendimentoDto.getClienteId());
		}

		atendimento.setDescricao(atendimentoDto.getDescricao());
		atendimento.setData(this.dateFormat.parse(atendimentoDto.getData()));
		atendimento.setValor(new BigDecimal(atendimentoDto.getValor()));

		/*
		if (EnumUtils.isValidEnum(TipoEnum.class, atendimentoDto.getTipo())) {
			atendimento.setTipo(TipoEnum.valueOf(atendimentoDto.getTipo()));
		} else {
			result.addError(new ObjectError("tipo", "Tipo inválido."));
		}
		*/

		return atendimento;
	}

}
