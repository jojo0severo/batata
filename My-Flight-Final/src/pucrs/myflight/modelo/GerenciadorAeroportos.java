package pucrs.myflight.modelo;

import java.util.ArrayList;
import java.util.Collections;

public class GerenciadorAeroportos {

	private ArrayList<Aeroporto> aeroportos;
	
	public GerenciadorAeroportos() {
		aeroportos = new ArrayList<>();
	}
	
	public void adicionar(Aeroporto aero) {
		aeroportos.add(aero);
	}
	
	public void ordenarNome() {
		//Collections.sort(aeroportos);
		aeroportos.sort( (Aeroporto a1, Aeroporto a2)
				-> a1.getNome().compareTo(a2.getNome()));
	};
	
	public void ordenarCodigo() {
		aeroportos.sort( (Aeroporto a1, Aeroporto a2)
				-> a1.getCodigo().compareTo(a2.getCodigo()));
	};
	
	public ArrayList<Aeroporto> listarTodos() {
		return new ArrayList<>(aeroportos);			
	}
}
