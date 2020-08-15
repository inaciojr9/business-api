package br.com.inaciojr9.businessapi.dto.empresa;
public class EmpresaDto {
	
	private EmpresaResumoDto empresaResumoDto;
	private String cnpj;

	public EmpresaDto() {}

	public EmpresaResumoDto getEmpresaResumoDto() {
		return empresaResumoDto;
	}

	public void setEmpresaResumoDto(EmpresaResumoDto empresaResumoDto) {
		this.empresaResumoDto = empresaResumoDto;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	@Override
	public String toString() {
		return "EmpresaDto [empresaResumoDto=" + empresaResumoDto + ", cnpj=" + cnpj + "]";
	}

}
