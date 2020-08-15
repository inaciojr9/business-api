package br.com.inaciojr9.businessapi.dto.formaDeRecebimento;
public class FormaDeRecebimentoResumoDto {
	
	private Long id;
	private String nome;

	public FormaDeRecebimentoResumoDto() {	}

	public FormaDeRecebimentoResumoDto(Long id, String nome) {
		super();
		this.id = id;
		this.nome = nome;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@Override
	public String toString() {
		return "ServicoResumoDto [id=" + id + ", nome=" + nome + "]";
	}

}
