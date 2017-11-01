package pucrs.myflight.modelojojo;

public class CiaAerea {
	private static int totalCias=0;
	private String codigo;
	private String nome;
	
	public CiaAerea(String codigo, String nome) {
		this.codigo = codigo;
		this.nome = nome;
		totalCias++;
	}
	
	public static int getTotalCias(){
		return totalCias;
	}
	public String getCodigo() {
		return codigo;
	}
	
	public String getNome() {
		return nome;
	}

	@Override
	public String toString() {
		return "CiaAerea [codigo=" + codigo + ", nome=" + nome + "]";
	}	
	
}
