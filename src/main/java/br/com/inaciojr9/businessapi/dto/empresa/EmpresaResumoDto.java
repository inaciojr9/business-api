package br.com.inaciojr9.businessapi.dto.empresa;
public class EmpresaResumoDto {
	
	private Long id;
	private String razaoSocial;

	public EmpresaResumoDto() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRazaoSocial() {
		return razaoSocial;
	}

	public void setRazaoSocial(String razaoSocial) {
		this.razaoSocial = razaoSocial;
	}

	@Override
	public String toString() {
		return "EmpresaResumoDto [id=" + id + ", razaoSocial=" + razaoSocial + "]";
	}

}
