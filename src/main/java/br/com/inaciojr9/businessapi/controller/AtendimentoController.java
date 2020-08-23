package br.com.inaciojr9.businessapi.controller;
import java.text.ParseException;
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

import br.com.inaciojr9.businessapi.dto.atendimento.AtendimentoDto;
import br.com.inaciojr9.businessapi.dto.atendimento.AtendimentoDtoConverter;
import br.com.inaciojr9.businessapi.dto.atendimento.AtendimentoDtoIn;
import br.com.inaciojr9.businessapi.exception.ObjetoInvalidoException;
import br.com.inaciojr9.businessapi.helper.web.Response;
import br.com.inaciojr9.businessapi.model.Atendimento;
import br.com.inaciojr9.businessapi.model.Cliente;
import br.com.inaciojr9.businessapi.model.Empresa;
import br.com.inaciojr9.businessapi.model.FormaDeRecebimento;
import br.com.inaciojr9.businessapi.service.AtendimentoService;
import br.com.inaciojr9.businessapi.service.ClienteService;
import br.com.inaciojr9.businessapi.service.EmpresaService;
import br.com.inaciojr9.businessapi.service.FormaDeRecebimentoService;
import br.com.inaciojr9.businessapi.util.Constantes;

@RestController
@RequestMapping("/api/atendimentos")
@CrossOrigin(origins = "*")
public class AtendimentoController {

	private static final Logger log = LoggerFactory.getLogger(AtendimentoController.class);

	@Autowired
	private AtendimentoService atendimentoService;
	
	@Autowired
	private EmpresaService empresaService;
	
	@Autowired
	private ClienteService clienteService;
	
	@Autowired
	private FormaDeRecebimentoService formaDeRecebimentoService;
	
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

		Optional<Empresa> empresa = empresaService.buscarPorId(Constantes.ID_EMPRESA_DEFAULT);
		if (!empresa.isPresent()) {
			response.getErrors().add("Empresa não encontrada.");
			return ResponseEntity.badRequest().body(response);
		}
		
		PageRequest pageRequest = PageRequest.of(pag, this.qtdPorPagina, Direction.valueOf(dir), ord);
		Page<Atendimento> atendimentos = this.atendimentoService.buscarPorClienteId(clienteId, pageRequest);
		Page<AtendimentoDto> atendimentosDto = atendimentos.map(atendimento -> AtendimentoDtoConverter.doModelParaDto(empresa.get(), atendimento));

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
		
		Optional<Empresa> empresa = empresaService.buscarPorId(Constantes.ID_EMPRESA_DEFAULT);
		if (!empresa.isPresent()) {
			response.getErrors().add("Empresa não encontrada.");
			return ResponseEntity.badRequest().body(response);
		}
		
		Optional<Atendimento> atendimento = this.atendimentoService.buscarPorId(empresa.get(), id);
		if (!atendimento.isPresent()) {
			log.info("Atendimento não encontrado para o ID: {}", id);
			response.getErrors().add("Atendimento não encontrado para o id " + id);
			return ResponseEntity.badRequest().body(response);
		}

		response.setData(AtendimentoDtoConverter.doModelParaDto(empresa.get(), atendimento.get()));
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
	public ResponseEntity<Response<AtendimentoDto>> adicionar(@Valid @RequestBody AtendimentoDtoIn atendimentoDto,
			BindingResult result) throws ParseException {
		
		Atendimento atendimento = null;
		Optional<Empresa> empresa = Optional.empty();
		
		if(atendimentoDto == null) {
			result.addError(new ObjectError("atendimento", "Atendimento inválido."));
		} else {
			
			log.info("Adicionando atendimento: {}", atendimentoDto.toString());
			
			empresa = empresaService.buscarPorId(Constantes.ID_EMPRESA_DEFAULT);
			if (!empresa.isPresent()) {
				result.addError(new ObjectError("atendimento", "Empresa inválida."));
			} else {
				
				try {
					validarAtendimento(empresa.get(), atendimentoDto, result);
					atendimento = AtendimentoDtoConverter.doDtoParaModel(empresa.get(), atendimentoDto);
				} catch (ObjetoInvalidoException e1) {
					result.addError(new ObjectError("atendimento", e1.getMessage()));
				}
				
			}
		}
		
		Response<AtendimentoDto> response = new Response<AtendimentoDto>();
		if (result.hasErrors()) {
			log.error("Erro validando atendimento: {}", result.getAllErrors());
			result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}

		atendimento = this.atendimentoService.persistir(empresa.get(), atendimento);
		response.setData(AtendimentoDtoConverter.doModelParaDto(empresa.get(), atendimento));
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
			@Valid @RequestBody AtendimentoDtoIn atendimentoDto, BindingResult result) throws ParseException {
		log.info("Atualizando atendimento: {}", atendimentoDto.toString());
		
		Atendimento atendimento = null;
		Response<AtendimentoDto> response = new Response<AtendimentoDto>();
		
		Optional<Empresa> empresa = empresaService.buscarPorId(Constantes.ID_EMPRESA_DEFAULT);
		if (!empresa.isPresent()) {
			response.getErrors().add("Empresa não encontrada.");
			return ResponseEntity.badRequest().body(response);
			
		} else {
			Optional<Atendimento> atendimentoPersistido = this.atendimentoService.buscarPorId(empresa.get(), id);
			if (!atendimentoPersistido.isPresent()) {
				result.addError(new ObjectError("atendimento", "Atendimento não encontrado para o id " + id));
			} else {
				
				try {
					validarAtendimento(empresa.get(), atendimentoDto, result);
					atendimento = AtendimentoDtoConverter.doDtoParaModel(empresa.get(), atendimentoDto);
					atendimento.setId(id);
					atendimento.setDataCriacao(atendimentoPersistido.get().getDataCriacao());
					atendimento.setEmpresa(atendimentoPersistido.get().getEmpresa());
				} catch (ObjetoInvalidoException e1) {
					result.addError(new ObjectError("atendimento", e1.getMessage()));
				}
				
			}
			
		}
		
		
		if (result.hasErrors()) {
			log.error("Erro validando atendimento: {}", result.getAllErrors());
			result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}

		atendimento = this.atendimentoService.persistir(empresa.get(), atendimento);
		response.setData(AtendimentoDtoConverter.doModelParaDto(empresa.get(), atendimento));

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
		
		Optional<Empresa> empresa = empresaService.buscarPorId(Constantes.ID_EMPRESA_DEFAULT);
		if (!empresa.isPresent()) {
			response.getErrors().add("Empresa não encontrada.");
			return ResponseEntity.badRequest().body(response);
		}
		
		Optional<Atendimento> atendimento = this.atendimentoService.buscarPorId(empresa.get(), id);

		if (!atendimento.isPresent()) {
			log.info("Erro ao remover devido ao atendimento ID: {} ser inválido.", id);
			response.getErrors().add("Erro ao remover atendimento. Registro não encontrado para o id " + id);
			return ResponseEntity.badRequest().body(response);
		}

		this.atendimentoService.remover(id);
		return ResponseEntity.ok(new Response<String>());
	}
	
	private void validarAtendimento(Empresa empresa, AtendimentoDtoIn atendimentoDto, BindingResult result) throws ObjetoInvalidoException{
		
		Optional<Cliente> clientePersistido = this.clienteService.buscarPorId(empresa, atendimentoDto.getCliente().getId());
		if (!clientePersistido.isPresent()) {
			throw new ObjetoInvalidoException("Cliente não encontrado. ID inexistente.");
			
		} else {
			
			Optional<FormaDeRecebimento> formaDeRecebimentoPersistido = this.formaDeRecebimentoService.buscarPorId(atendimentoDto.getFormaDeRecebimento().getId());
			if (!formaDeRecebimentoPersistido.isPresent()) {
				throw new ObjetoInvalidoException("Forma de recebimento näo encontrada. ID inexistente.");
				
			}
		}
		
	}
	
}
