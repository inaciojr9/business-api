package br.com.inaciojr9.businessapi.controller;


import java.math.BigDecimal;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.inaciojr9.businessapi.dto.gestaomensal.GestaoMensalDtoConverter;
import br.com.inaciojr9.businessapi.dto.gestaomensal.GestaoMensalDtoIn;
import br.com.inaciojr9.businessapi.dto.gestaomensal.GestaoMensalDtoOut;
import br.com.inaciojr9.businessapi.exception.ObjetoInvalidoException;
import br.com.inaciojr9.businessapi.helper.web.Response;
import br.com.inaciojr9.businessapi.model.Empresa;
import br.com.inaciojr9.businessapi.model.GestaoMensal;
import br.com.inaciojr9.businessapi.service.AtendimentoService;
import br.com.inaciojr9.businessapi.service.EmpresaService;
import br.com.inaciojr9.businessapi.service.GestaoMensalService;
import br.com.inaciojr9.businessapi.util.Constantes;

@RestController
@RequestMapping("/api/gestaomensal")
@CrossOrigin(origins = "*")
public class GestaoMensalController {

	private static final Logger log = LoggerFactory.getLogger(GestaoMensalController.class);

	@Autowired
	private EmpresaService empresaService;
	
	@Autowired
	private GestaoMensalService gestaoMensalService;
	
	@Autowired
	private AtendimentoService atendimentoService;

	public GestaoMensalController() {}
	
	/**
	 * Retorna um cliente dado um CPF.
	 * 
	 * @param cpf
	 * @return ResponseEntity<Response<ClienteDto>>
	 */
	@GetMapping(value = "/ano/{ano}/mes/{mes}")
	public ResponseEntity<Response<GestaoMensalDtoOut>> buscarPorAnoMes(Integer mes, Integer ano) {
		log.info("Buscando gestão mensal por ano {} e mês {}", ano, mes);
		Response<GestaoMensalDtoOut> response = new Response<GestaoMensalDtoOut>();
		
		Optional<Empresa> empresa = empresaService.buscarPorId(Constantes.ID_EMPRESA_DEFAULT);
		if (!empresa.isPresent()) {
			response.getErrors().add("Empresa não encontrada.");
			return ResponseEntity.badRequest().body(response);
		}
		
		Optional<GestaoMensal> gestaoMensal = gestaoMensalService.buscarPorAnoMes(empresa.get(), ano, mes);
		if (!gestaoMensal.isPresent()) {
			log.info("Gestão mensal não encontrada para o ano {} e mês {}", ano, mes);
			response.getErrors().add("Gestão mensal não encontrada para o ano " + ano + " mês " + mes);
			return ResponseEntity.badRequest().body(response);
		}

		response.setData(GestaoMensalDtoConverter.doModelParaDto(empresa.get(), gestaoMensal.get()));
		return ResponseEntity.ok(response);
	}
	
	@GetMapping(value = "/{id}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<Response<GestaoMensalDtoOut>> consultar(@PathVariable("id") Long id) throws NoSuchAlgorithmException {
		log.info("Obtendo gestão mensal: {}", id);
		Response<GestaoMensalDtoOut> response = new Response<GestaoMensalDtoOut>();
		
		Optional<Empresa> empresa = empresaService.buscarPorId(Constantes.ID_EMPRESA_DEFAULT);
		if (!empresa.isPresent()) {
			response.getErrors().add("Empresa não encontrada.");
			return ResponseEntity.badRequest().body(response);
		}
		
		Optional<GestaoMensal> cliente = this.gestaoMensalService.buscarPorId(empresa.get(), id);
		if (!cliente.isPresent()) {
			log.info("Gestão mensal não encontrada para o ID: {}", id);
			response.getErrors().add("Gestão mensal não encontrada para o id " + id);
			return ResponseEntity.badRequest().body(response);
		}

		response.setData(GestaoMensalDtoConverter.doModelParaDto(empresa.get(), cliente.get()));
		return ResponseEntity.ok(response);
	}
	
	
	/**
	 * Adiciona um novo cliente.
	 * @param atendimentoDto
	 * @param result
	 * @return
	 * @throws ParseException
	 */
	@PostMapping
	public ResponseEntity<Response<GestaoMensalDtoOut>> adicionar(@Valid @RequestBody GestaoMensalDtoIn gestaoMensalDto, BindingResult result){
		GestaoMensal gestaoMensal = null;
		Optional<Empresa> empresa = Optional.empty();
		
		if(gestaoMensalDto == null) {
			result.addError(new ObjectError("gestaoMensal", "Gestão mensal inválida."));
		} else {
			
			log.info("Adicionando gestão mensal: {}", gestaoMensalDto.toString());
			
			empresa = empresaService.buscarPorId(Constantes.ID_EMPRESA_DEFAULT);
			if (!empresa.isPresent()) {
				result.addError(new ObjectError("gestão mensal", "Empresa inválida."));
			} else {
				
				try {
					validarGestaoMensal(empresa.get(), gestaoMensalDto, result);
					BigDecimal receitaServico = atendimentoService.getValorTotalDosAtendimentosDeUmAnoMes(empresa.get(), gestaoMensalDto.getAno(), gestaoMensalDto.getMes());
					gestaoMensal = GestaoMensalDtoConverter.doDtoParaModel(empresa.get(), gestaoMensalDto, receitaServico);
				} catch (ObjetoInvalidoException e1) {
					result.addError(new ObjectError("gestaoMensal", e1.getMessage()));
				}
				
			}
		}
		
		Response<GestaoMensalDtoOut> response = new Response<GestaoMensalDtoOut>();
		if (result.hasErrors()) {
			log.error("Erro validando cliente: {}", result.getAllErrors());
			result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}

		gestaoMensal = this.gestaoMensalService.persistir(empresa.get(), gestaoMensal);
		response.setData(GestaoMensalDtoConverter.doModelParaDto(empresa.get(), gestaoMensal));
		return ResponseEntity.ok(response);
	}


	/**
	 * Atualiza os dados de um cliente.
	 * 
	 * @param id
	 * @param gestaoMensalDto
	 * @param result
	 * @return ResponseEntity<Response<ClienteDto>>
	 * @throws NoSuchAlgorithmException
	 */
	@PutMapping(value = "/{id}")
	public ResponseEntity<Response<GestaoMensalDtoOut>> atualizar(@PathVariable("id") Long id,
			@Valid @RequestBody GestaoMensalDtoIn gestaoMensalDto, BindingResult result) throws NoSuchAlgorithmException {
		
		log.info("Atualizando gestao mensal: ", gestaoMensalDto.toString());
		
		GestaoMensal gestaoMensal = null;
		Response<GestaoMensalDtoOut> response = new Response<GestaoMensalDtoOut>();
		
		Optional<Empresa> empresa = empresaService.buscarPorId(Constantes.ID_EMPRESA_DEFAULT);
		if (!empresa.isPresent()) {
			response.getErrors().add("Empresa não encontrada.");
			return ResponseEntity.badRequest().body(response);
			
		} else {
				
			try {
				GestaoMensal gestaoMensalPersistida = validarGestaoMensalParaAtualizacao(empresa.get(), id, gestaoMensalDto, result);
				BigDecimal receitaServico = atendimentoService.getValorTotalDosAtendimentosDeUmAnoMes(empresa.get(), gestaoMensalDto.getAno(), gestaoMensalDto.getMes());
				gestaoMensal = GestaoMensalDtoConverter.doDtoParaModel(empresa.get(), gestaoMensalDto, receitaServico);
				gestaoMensal.setId(id);
				gestaoMensal.setDataCriacao(gestaoMensalPersistida.getDataCriacao());
			} catch (ObjetoInvalidoException e1) {
				result.addError(new ObjectError("cliente", e1.getMessage()));
			}
			
		}

		if (result.hasErrors()) {
			log.error("Erro validando cliente: {}", result.getAllErrors());
			result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}

		gestaoMensal = this.gestaoMensalService.persistir(empresa.get(), gestaoMensal);
		response.setData(GestaoMensalDtoConverter.doModelParaDto(empresa.get(), gestaoMensal));

		return ResponseEntity.ok(response);
	}
	
	
	private void validarGestaoMensal(Empresa empresa, GestaoMensalDtoIn gestaoMensalDto, BindingResult result) throws ObjetoInvalidoException{
		
		log.info("Validando ano {} e mes {}", gestaoMensalDto.getAno(), gestaoMensalDto.getMes());
		Optional<GestaoMensal> gestaoMensalPersistida = this.gestaoMensalService.buscarPorAnoMes(empresa, gestaoMensalDto.getAno(), gestaoMensalDto.getMes());
		if (gestaoMensalPersistida.isPresent()) {
			throw new ObjetoInvalidoException("Já existe uma gestão para o mês "+gestaoMensalDto.getMes() + " e ano " + gestaoMensalDto.getAno());
			
		}
		
	}
	
	private GestaoMensal validarGestaoMensalParaAtualizacao(Empresa empresa, Long id, GestaoMensalDtoIn gestaoMensalDto, BindingResult result) throws ObjetoInvalidoException{
		
		log.info("Validando ano {} e mes {}", gestaoMensalDto.getAno(), gestaoMensalDto.getMes());
		
		Optional<GestaoMensal> gestaoMensalPersistida = this.gestaoMensalService.buscarPorId(empresa, id);
		if (!gestaoMensalPersistida.isPresent()) {
			throw new ObjetoInvalidoException("Gestão mensal não encontrada.");
		}
		
		if((gestaoMensalDto.getAno() != gestaoMensalPersistida.get().getAno() ||  
				gestaoMensalDto.getMes() != gestaoMensalPersistida.get().getMes()) ) {
			
			Optional<GestaoMensal> gestaoMensalAnoMes = this.gestaoMensalService.buscarPorAnoMes(empresa, gestaoMensalDto.getAno(), gestaoMensalDto.getMes());
			if (gestaoMensalAnoMes.isPresent() && id != gestaoMensalAnoMes.get().getId() ) {
				throw new ObjetoInvalidoException("Já existe uma gestão para o mês "+gestaoMensalDto.getMes() + " e ano " + gestaoMensalDto.getAno());
			}
			
		} 
		
		return gestaoMensalPersistida.get();
		
	}

}
