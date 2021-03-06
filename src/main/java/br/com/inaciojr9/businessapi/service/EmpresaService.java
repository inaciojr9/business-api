package br.com.inaciojr9.businessapi.service;

import java.util.Optional;

import br.com.inaciojr9.businessapi.model.Empresa;

public interface EmpresaService {

	/**
	 * Retorna uma empresa dado um CNPJ.
	 * 
	 * @param cnpj
	 * @return Optional<Empresa>
	 */
	Optional<Empresa> buscarPorCnpj(String cnpj);
	
	/**
	 * Cadastra uma nova empresa na base de dados.
	 * 
	 * @param empresa
	 * @return Empresa
	 */
	Empresa persistir(Empresa empresa);

	
	Optional<Empresa> buscarPorId(Long id);
	
}
