package br.com.inaciojr9.businessapi.controller;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.inaciojr9.businessapi.dto.servico.ServicoDto;
import br.com.inaciojr9.businessapi.dto.servico.ServicoDtoConverter;
import br.com.inaciojr9.businessapi.helper.web.Response;
import br.com.inaciojr9.businessapi.model.Servico;
import br.com.inaciojr9.businessapi.service.ServicoService;

@RestController
@RequestMapping("/api/servicos")
@CrossOrigin(origins = "*")
public class ServicoController {

	private static final Logger log = LoggerFactory.getLogger(ServicoController.class);

	@Autowired
	private ServicoService servicoService;

	public ServicoController() {}

	/**
	 * Retorna um serviço pelo id.
	 * 
	 * @param id
	 * @return ResponseEntity<Response<ServicoDto>>
	 */
	@GetMapping(value = "/id/{id}")
	public ResponseEntity<Response<ServicoDto>> buscarPorId(@PathVariable("id") Long id) {
		log.info("Buscando serviço por id: {}", id);
		Response<ServicoDto> response = new Response<ServicoDto>();
		Optional<Servico> servico = servicoService.buscarPorId(id);

		if (!servico.isPresent()) {
			log.info("Serviço não encontrado para o id: {}", id);
			response.getErrors().add("Serviço não encontrado para o id " + id);
			return ResponseEntity.badRequest().body(response);
		}

		response.setData(ServicoDtoConverter.doModelParaDto(servico.get()));
		return ResponseEntity.ok(response);
	}
	
	/**
	 * Retorna todos os serviços
	 * 
	 * @return ResponseEntity<Response<List<ServicoDto>>>
	 */
	@GetMapping()
	public ResponseEntity<Response<List<ServicoDto>>> buscarTodos() {
		log.info("Buscando todos os serviço");
		Response<List<ServicoDto>> response = new Response<>();
		List<Servico> servicos = servicoService.buscarTodos();

		response.setData(ServicoDtoConverter.doModelParaDto(servicos));
		return ResponseEntity.ok(response);
	}

}
