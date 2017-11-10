package pucrs.myflight.modelo;

public class Aeroporto implements Comparable<Aeroporto> {
	private String codigo;
	private String nome;
	private Geo loc;
	private Pais pais;
	private boolean passouAqui;

	public Aeroporto(String codigo, String nome, Geo loc, Pais pais) {
		this.codigo = codigo;
		this.nome = nome;
		this.loc = loc;
		this.pais = pais;
		this.passouAqui = false;
	}

	public String getCodigo() {
		return codigo;
	}

	public Pais getPais() {
		return pais;
	}

	public void setPais(Pais pais) {
		this.pais = pais;
	}

	public String getNome() {
		return nome;
	}

	public Geo getLocal() {
		return loc;
	}

	public boolean getPassouAqui(){
		return passouAqui;
	}

	public void setPassouAqui(boolean passou){
		this.passouAqui = passou;
	}

	@Override
	public String toString() {
		return "Aeroporto [codigo=" + codigo + ", nome=" + nome + ", loc=" + loc + "]";
	}

	@Override
	public int compareTo(Aeroporto other) {
		return nome.compareTo(other.nome);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Aeroporto aeroporto = (Aeroporto) o;

		if (passouAqui != aeroporto.passouAqui) return false;
		if (codigo != null ? !codigo.equals(aeroporto.codigo) : aeroporto.codigo != null) return false;
		if (nome != null ? !nome.equals(aeroporto.nome) : aeroporto.nome != null) return false;
		if (loc != null ? !loc.equals(aeroporto.loc) : aeroporto.loc != null) return false;
		return pais != null ? pais.equals(aeroporto.pais) : aeroporto.pais == null;
	}

	@Override
	public int hashCode() {
		int result = codigo != null ? codigo.hashCode() : 0;
		result = 31 * result + (nome != null ? nome.hashCode() : 0);
		result = 31 * result + (loc != null ? loc.hashCode() : 0);
		result = 31 * result + (pais != null ? pais.hashCode() : 0);
		result = 31 * result + (passouAqui ? 1 : 0);
		return result;
	}
}