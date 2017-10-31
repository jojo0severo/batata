package pucrs.myflight.modelo;

import java.util.ArrayList;

public class GerenciadorVoos {
	private ArrayList<Voo> voos;

	public GerenciadorVoos() {
		voos = new ArrayList<>();
	}

	public void adicionar(Voo v) {
		voos.add(v);
	}

	public ArrayList<Voo> listarTodos() {
		return new ArrayList<>(voos);
	}
}
