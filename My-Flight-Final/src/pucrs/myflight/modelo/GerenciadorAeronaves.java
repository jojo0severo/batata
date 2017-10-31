package pucrs.myflight.modelo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class GerenciadorAeronaves {

	private ArrayList<Aeronave> aeronaves;

	public GerenciadorAeronaves() {
		aeronaves = new ArrayList<>();
	}

	public void adicionar(Aeronave a) {
		aeronaves.add(a);
	}

	public void ordenarDescricao() {
		// Collections.sort(aeronaves);
		//aeronaves.sort( (Aeronave a1, Aeronave a2)
		//		-> a1.getDescricao().compareTo(a2.getDescricao()));
		//aeronaves.sort(Comparator.comparing(a -> a.getDescricao()));
		aeronaves.sort(Comparator.comparing(Aeronave::getDescricao));
	}

	public ArrayList<Aeronave> listarTodas() {
		return new ArrayList<>(aeronaves);
	}
}
