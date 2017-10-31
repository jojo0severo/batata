package pucrs.myflight.modelo;

public class InterfaceTexto {

	private GerenciadorCias gerCias;
	private GerenciadorAeronaves gerAvioes;
	private GerenciadorAeroportos gerAero;
	private GerenciadorRotas gerRotas;
	private GerenciadorVoos gerVoos;
	
	public InterfaceTexto() {
		gerCias = new GerenciadorCias();
		gerAvioes = new GerenciadorAeronaves();
		gerAero = new GerenciadorAeroportos();
		gerRotas = new GerenciadorRotas();
		gerVoos = new GerenciadorVoos();
	}
}
