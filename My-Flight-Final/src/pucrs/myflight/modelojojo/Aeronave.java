package pucrs.myflight.modelojojo;

public class Aeronave implements imprimivel, Contavel, Comparable<Aeronave> {
	private String codigo;
	private String descricao;
	private int capacidade;
	private static int quantaerov = 0;

	public Aeronave(String codigo, String descricao, int cap) {
		this.codigo = codigo;
		this.descricao = descricao;
		this.capacidade = cap;
		quantaerov++;
	}

	public String getDescricao() {
		return descricao;
	}

	public int getCapacidade() {
		return capacidade;
	}

	public String getCodigo() {
		return codigo;
	}

	@Override
	public String toString() {
		return "Aeronave [codigo=" + codigo + ", descricao=" + descricao + ", capacidade=" + capacidade + "]";
	}

	public void imprimir() {
		System.out.println(codigo + " - " + descricao);
	}

	public int getTotal() {
		return quantaerov;
	}

	@Override
	public int compareTo(Aeronave outra) {
		return descricao.compareTo(outra.descricao);
	}

}
