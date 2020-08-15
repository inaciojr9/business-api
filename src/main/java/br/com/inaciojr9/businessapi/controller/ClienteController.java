package br.com.inaciojr9.businessapi.controller;
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

import br.com.inaciojr9.businessapi.dto.cliente.ClienteDto;
import br.com.inaciojr9.businessapi.dto.cliente.ClienteDtoConverter;
import br.com.inaciojr9.businessapi.exception.ObjetoInvalidoException;
import br.com.inaciojr9.businessapi.helper.web.Response;
import br.com.inaciojr9.businessapi.model.Cliente;
import br.com.inaciojr9.businessapi.model.Empresa;
import br.com.inaciojr9.businessapi.service.ClienteService;
import br.com.inaciojr9.businessapi.service.EmpresaService;
import br.com.inaciojr9.businessapi.util.Constantes;

@RestController
@RequestMapping("/api/clientes")
@CrossOrigin(origins = "*")
public class ClienteController {

	private static final Logger log = LoggerFactory.getLogger(ClienteController.class);

	@Autowired
	private EmpresaService empresaService;
	
	@Autowired
	private ClienteService clienteService;

	public ClienteController() {}
	
	/**
	 * Retorna um cliente dado um CPF.
	 * 
	 * @param cpf
	 * @return ResponseEntity<Response<ClienteDto>>
	 */
	@GetMapping(value = "/cpf/{cpf}")
	public ResponseEntity<Response<ClienteDto>> buscarPorCpf(@PathVariable("cpf") String cpf) {
		log.info("Buscando cliente por CPF: {}", cpf);
		Response<ClienteDto> response = new Response<ClienteDto>();
		
		Optional<Empresa> empresa = empresaService.buscarPorId(Constantes.ID_EMPRESA_DEFAULT);
		if (!empresa.isPresent()) {
			response.getErrors().add("Empresa não encontrada.");
			return ResponseEntity.badRequest().body(response);
		}
		
		Optional<Cliente> cliente = clienteService.buscarPorCpf(empresa.get(), cpf);
		if (!cliente.isPresent()) {
			log.info("Cliente não encontrada para o CPF: {}", cpf);
			response.getErrors().add("Cliente não encontrada para o CPF " + cpf);
			return ResponseEntity.badRequest().body(response);
		}

		response.setData(ClienteDtoConverter.doModelParaDto(empresa.get(), cliente.get()));
		return ResponseEntity.ok(response);
	}
	
	/**
	 * Retorna um cliente dado um CPF.
	 * 
	 * @param cpf
	 * @return ResponseEntity<Response<ClienteDto>>
	 */
	@GetMapping(value = "/email/{email}")
	public ResponseEntity<Response<ClienteDto>> buscarPorEmail(@PathVariable("email") String email) {
		log.info("Buscando cliente por email: {}", email);
		Response<ClienteDto> response = new Response<ClienteDto>();
		
		Optional<Empresa> empresa = empresaService.buscarPorId(Constantes.ID_EMPRESA_DEFAULT);
		if (!empresa.isPresent()) {
			response.getErrors().add("Empresa não encontrada.");
			return ResponseEntity.badRequest().body(response);
		}
		
		Optional<Cliente> cliente = clienteService.buscarPorEmail(empresa.get(), email);
		if (!cliente.isPresent()) {
			log.info("Cliente não encontrado para o email: {}", email);
			response.getErrors().add("Cliente não encontrado para o email " + email);
			return ResponseEntity.badRequest().body(response);
		}

		response.setData(ClienteDtoConverter.doModelParaDto(empresa.get(), cliente.get()));
		return ResponseEntity.ok(response);
	}
	
	@GetMapping(value = "/{id}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<Response<ClienteDto>> consultar(@PathVariable("id") Long id) throws NoSuchAlgorithmException {
		log.info("Obtendo cliente: {}", id);
		Response<ClienteDto> response = new Response<ClienteDto>();
		
		Optional<Empresa> empresa = empresaService.buscarPorId(Constantes.ID_EMPRESA_DEFAULT);
		if (!empresa.isPresent()) {
			response.getErrors().add("Empresa não encontrada.");
			return ResponseEntity.badRequest().body(response);
		}
		
		Optional<Cliente> cliente = this.clienteService.buscarPorId(empresa.get(), id);
		if (!cliente.isPresent()) {
			log.info("Cliente não encontrado para o ID: {}", id);
			response.getErrors().add("Cliente não encontrado para o id " + id);
			return ResponseEntity.badRequest().body(response);
		}

		response.setData(ClienteDtoConverter.doModelParaDto(empresa.get(), cliente.get()));
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
	public ResponseEntity<Response<ClienteDto>> adicionar(@Valid @RequestBody ClienteDto clienteDto, BindingResult result){
		Cliente cliente = null;
		Optional<Empresa> empresa = Optional.empty();
		
		if(clienteDto == null) {
			result.addError(new ObjectError("cliente", "Cliente inválido."));
		} else {
			
			log.info("Adicionando cliente: {}", clienteDto.toString());
			
			empresa = empresaService.buscarPorId(Constantes.ID_EMPRESA_DEFAULT);
			if (!empresa.isPresent()) {
				result.addError(new ObjectError("cliente", "Empresa inválida."));
			} else {
				
				try {
					validarCliente(empresa.get(), clienteDto, result);
					cliente = ClienteDtoConverter.doDtoParaModel(empresa.get(), clienteDto);
				} catch (ObjetoInvalidoException e1) {
					result.addError(new ObjectError("cliente", e1.getMessage()));
				}
				
			}
		}
		
		Response<ClienteDto> response = new Response<ClienteDto>();
		if (result.hasErrors()) {
			log.error("Erro validando cliente: {}", result.getAllErrors());
			result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}

		cliente = this.clienteService.persistir(empresa.get(), cliente);
		response.setData(ClienteDtoConverter.doModelParaDto(empresa.get(), cliente));
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
		
		Cliente cliente = null;
		Response<ClienteDto> response = new Response<ClienteDto>();
		
		Optional<Empresa> empresa = empresaService.buscarPorId(Constantes.ID_EMPRESA_DEFAULT);
		if (!empresa.isPresent()) {
			response.getErrors().add("Empresa não encontrada.");
			return ResponseEntity.badRequest().body(response);
			
		} else {
			Optional<Cliente> clientePersistido = this.clienteService.buscarPorId(empresa.get(), id);
			if (!clientePersistido.isPresent()) {
				result.addError(new ObjectError("cliente", "Cliente não encontrado para o id " + id));
			} else {
				
				try {
					validarClienteParaAtualizacao(empresa.get(), clienteDto, result);
					cliente = ClienteDtoConverter.doDtoParaModel(empresa.get(), clienteDto);
					cliente.setDataCriacao(clientePersistido.get().getDataCriacao());
					cliente.setSenha(clientePersistido.get().getSenha());
					cliente.setPerfil(clientePersistido.get().getPerfil());
				} catch (ObjetoInvalidoException e1) {
					result.addError(new ObjectError("cliente", e1.getMessage()));
				}
				
			}
			
		}

		if (result.hasErrors()) {
			log.error("Erro validando cliente: {}", result.getAllErrors());
			result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}

		cliente = this.clienteService.persistir(empresa.get(), cliente);
		response.setData(ClienteDtoConverter.doModelParaDto(empresa.get(), cliente));

		return ResponseEntity.ok(response);
	}
	
	
	private void validarCliente(Empresa empresa, ClienteDto clienteDto, BindingResult result) throws ObjetoInvalidoException{
		
		log.info("Validando cpf {}: ", clienteDto.getCpf());
		Optional<Cliente> clientePersistido = this.clienteService.buscarPorCpf(empresa, clienteDto.getCpf());
		if (clientePersistido.isPresent()) {
			throw new ObjetoInvalidoException("Já existe um cliente com o cpf "+clienteDto.getCpf());
			
		} else {
			
			log.info("Validando email {}: ", clienteDto.getEmail());
			clientePersistido = this.clienteService.buscarPorEmail(empresa, clienteDto.getEmail());
			if (clientePersistido.isPresent()) {
				throw new ObjetoInvalidoException("Já existe um cliente com o email "+clienteDto.getEmail());
				
			} 
		}
		
	}
	
	private void validarClienteParaAtualizacao(Empresa empresa, ClienteDto clienteDto, BindingResult result) throws ObjetoInvalidoException{
		
		log.info("Validando cpf {}: ", clienteDto.getCpf());
		Optional<Cliente> clientePersistido = this.clienteService.buscarPorCpf(empresa, clienteDto.getCpf());
		if (clientePersistido.isPresent() && clienteDto.getClienteResumoDto().getId() != clientePersistido.get().getId() ) {
			throw new ObjetoInvalidoException("Já existe um cliente com o cpf "+clienteDto.getCpf());
			
		} else {
			
			log.info("Validando email {}: ", clienteDto.getEmail());
			clientePersistido = this.clienteService.buscarPorEmail(empresa, clienteDto.getEmail());
			if (clientePersistido.isPresent() && clienteDto.getClienteResumoDto().getId() != clientePersistido.get().getId()) {
				throw new ObjetoInvalidoException("Já existe um cliente com o email "+clienteDto.getEmail());
				
			} 
		}
		
	}

}
