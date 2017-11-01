package pucrs.myflight.modelojojo;

public class Aeroporto implements Comparable<Aeroporto> {
	private String codigo;
	private String nome;
	private Geo loc;
	private Pais pais;

	public Aeroporto(String codigo, String nome, Geo loc, Pais pais) {
		this.codigo = codigo;
		this.nome = nome;
		this.loc = loc;
		this.pais = pais;
	}

	public String getCodigo() {
		return codigo;
	}

	public String getNome() {
		return nome;
	}

	public Geo getLocal() {
		return loc;
	}

	@Override
	public String toString() {
		return "Aeroporto [codigo=" + codigo + ", nome=" + nome + ", loc=" + loc + "]";
	}

	@Override
	public int compareTo(Aeroporto other) {
		return nome.compareTo(other.nome);
	}

}