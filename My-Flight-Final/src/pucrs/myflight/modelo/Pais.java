package pucrs.myflight.modelo;

public class Pais implements Comparable<Pais> {
	private String nome;
	private String codigo;
	
	public Pais(String nome, String codigo) {
		super();
		this.nome = nome;
		this.codigo = codigo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	@Override
	public String toString() {
		return "Pais [nome=" + nome + ", codigo=" + codigo + "]";
	}

	@Override
	public int compareTo(Pais o) {
		// TODO Auto-generated method stub
		return 0;
	}

	public boolean equals(Pais um, Pais dois) {
		return um==dois;
	}
	
	
}
