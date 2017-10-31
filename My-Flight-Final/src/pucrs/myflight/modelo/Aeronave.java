package pucrs.myflight.modelo;

public class Aeronave implements Imprimivel, Contavel,
		Comparable<Aeronave> {
	private static int contador = 0;
	private String codigo;
	private String descricao;
	private int capacidade;
	
	public Aeronave(String codigo, String descricao, int cap) {
		this.codigo = codigo;
		this.descricao = descricao;
		this.capacidade = cap;
		contador++;
	}
	
	public String getCodigo() {
		return codigo;
	}
	
	public String getDescricao() {
		return descricao;
	}

	public int getCapacidade() {
		return capacidade;
	}

	@Override
	public void imprimir() {
		System.out.println(codigo + " - " + descricao);		
	}

	@Override
	public int getTotal() {
		return contador;
	}

	@Override
	public int compareTo(Aeronave o) {
		return this.descricao.compareTo(o.descricao);
	}
}
