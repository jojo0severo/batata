package pucrs.myflight.modelo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class GerenciadorRotas {

	private ArrayList<Rota> rotas;

	public GerenciadorRotas() {
		rotas = new ArrayList<>();
	}

	public void adicionar(Rota r) {
		rotas.add(r);
	}

	public void ordenarCias() {
		// Collections.sort(rotas);
		rotas.sort((Rota r1, Rota r2) -> r1.getCia().getNome().compareTo(r2.getCia().getNome()));
	}

	public void ordenarOrigem() {
		rotas.sort((Rota r1, Rota r2) -> r1.getOrigem().getNome().compareTo(r2.getOrigem().getNome()));
	}

	public void ordenarOrigemCias() {
		/*
		rotas.sort((Rota r1, Rota r2) -> {
			int res = r1.getOrigem().getNome().compareTo(r2.getOrigem().getNome());
			if (res != 0) // não empatou no nome da origem...
				return res;
			// empatou, desempata pelo nome da cia.
			return r1.getCia().getNome().compareTo(r2.getCia().getNome());
		});
		*/
		rotas.sort(Comparator.comparing(
				(Rota r) -> r.getOrigem().getNome())
				.thenComparing((Rota r) -> r.getCia().getNome()));
	}

	public ArrayList<Rota> listarTodas() {
		return new ArrayList<>(rotas);
	}

}
