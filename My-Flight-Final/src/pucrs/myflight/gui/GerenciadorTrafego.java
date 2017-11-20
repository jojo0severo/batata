package pucrs.myflight.gui;

import java.lang.reflect.Array;

import java.util.*;

import org.jxmapviewer.viewer.GeoPosition;

import pucrs.myflight.modelo.*;

public class GerenciadorTrafego {

	private Map<Integer, ArrayList<Aeroporto>> tamanhosJoJo;
	private ArrayList<Integer> tamanhos;
	private ArrayList<Integer> tamanhos1;
	private ArrayList<Integer> valores;
	private ArrayList<Integer> valores1;
	private GerenciadorRotas gerRotas;
	private ArrayList<Aeroporto> aeroportosMundial;
	private int tamanhoVetor;
	private ArrayList<Aeroporto> aeroportos;
	private Pais pais = null;

	public GerenciadorTrafego(GerenciadorRotas gerRotas, int tamanhoVetor) {
		this.gerRotas = gerRotas;
		this.tamanhoVetor = tamanhoVetor;
		tamanhos = new ArrayList<>();
		tamanhos1 = new ArrayList<>();
		valores = new ArrayList<>();
		valores1 = new ArrayList<>();
		aeroportos = new ArrayList<>();
		aeroportosMundial = new ArrayList<>();
		tamanhosJoJo = gerRotas.getTamanhos();
	}

	public ArrayList<Integer> getTamanhos() {
		return tamanhos;
	}

	public Integer getTamanhoVetor() {
		return tamanhoVetor;
	}

	public ArrayList<Aeroporto> maiores() {
		ArrayList<Aeroporto> maiores = new ArrayList<>();
		for (Integer key : tamanhosJoJo.keySet()) {
			for (Aeroporto aero : tamanhosJoJo.get(key)) {
				maiores.add(aero);
			}
		}
		return maiores;
	}

	public ArrayList<Aeroporto> maioresPais(Pais pais) {
		ArrayList<Aeroporto> maiores = new ArrayList<>();
		for (Integer key : tamanhosJoJo.keySet()) {
			for (Aeroporto aero : tamanhosJoJo.get(key)) {
				if (aero.getPais().getCodigo().equals(pais.getCodigo())) {
					maiores.add(aero);
				}
			}
		}
		return maiores;
	}

	public ArrayList<Aeroporto> getAeroportos() {
		return aeroportos;
	}

	public ArrayList<Aeroporto> getAeroportosMundial() {
		return aeroportosMundial;
	}

	public void setTamanhoVetor(int tamanhoVetor) {
		this.tamanhoVetor = tamanhoVetor;
	}

	public void carregaTamanhosHashMap() {
		tamanhos.clear();
		for (Aeroporto aero : gerRotas.getHashMap().keySet()) {
			tamanhos.add(gerRotas.getHashMap().get(aero).size());
		}
		Collections.sort(tamanhos);
	}

	public void carregaTamanhosArray(ArrayList<Aeroporto> aeroportos) {
		tamanhos.clear();
		for (int i = 0; i < aeroportos.size(); i++) {
			Set<Rota> verificador = gerRotas.getHashMap().get(aeroportos.get(i));
			if (verificador == null) {
				continue;
			} else {
				tamanhos.add(verificador.size());
			}

		}

		Collections.sort(tamanhos);

	}

	public void carregaValores() {

		for (int i = 0; i < tamanhoVetor; i++) {
			valores.add(tamanhos.get(tamanhos.size() - i - 1));
		}
		if (pais == null) {
			trafego();
		} else {
			trafegoPais();
		}
	}
	// public void carregaValores2() {
	// valores1.clear();
	// for (int i = 0; i < tamanhoVetor; i++) {
	// valores1.add(tamanhos1.get(tamanhos1.size() - i - 1));
	// System.out.println(tamanhoVetor);
	// }
	// if (pais == null) {
	// trafego1();
	// } else {
	// trafegoPais();
	// }
	// }

	public void trafego() {
		aeroportos.clear();
		for (Aeroporto aero : gerRotas.getHashMap().keySet()) {
			for (int i = 0; i < valores.size(); i++) {
				Integer integer = valores.get(i);
				if (gerRotas.getHashMap().get(aero).size() == integer) {
					aeroportos.add(aero);
					valores.remove(i);
					break;
				}
			}
		}
	}

	public void trafegoPais() {
		aeroportos.clear();
		for (Aeroporto aero : gerRotas.getHashMap().keySet()) {
			if (aero.getPais().equals(pais)) {
				for (int i = 0; i < valores.size(); i++) {
					Integer integer = valores.get(i);
					if (gerRotas.getHashMap().get(aero).size() == integer) {
						aeroportos.add(aero);
						valores.remove(i);
						break;
					}
				}
			}
		}
	}

	// public void trafego1() {
	// aeroportosMundial.clear();
	// for (Aeroporto aero : gerRotas.getHashMap().keySet()) {
	// for (int i = 0; i < valores1.size(); i++) {
	// Integer integer = valores1.get(i);
	// if (gerRotas.getHashMap().get(aero).size() == integer) {
	// aeroportosMundial.add(aero);
	// valores1.remove(i);
	// break;
	// }
	// }
	// }
	// }
	//
	// public void trafegoPais2() {
	// aeroportosMundial.clear();
	// for (Aeroporto aero : gerRotas.getHashMap().keySet()) {
	// if (aero.getPais().equals(pais)) {
	// for (int i = 0; i < valores.size(); i++) {
	// Integer integer = valores.get(i);
	// if (gerRotas.getHashMap().get(aero).size() == integer) {
	// aeroportosMundial.add(aero);
	// valores.remove(i);
	// break;
	// }
	// }
	// }
	// }
	// }

	public ArrayList<Aeroporto> aeroportosDeUmPais(GerenciadorMapa gerenciador, GerenciadorAeroportos gerAero,
			Pais pais1) {

		ArrayList<Aeroporto> aeroportos = new ArrayList<>();

		// double ds0 = 5;
		//
		// double longiMais = Math.ceil(paisLoc.getLongitude() + ds0);
		// double longiMenos = Math.ceil(paisLoc.getLongitude() - ds0);
		//
		// double latiMais = Math.ceil(paisLoc.getLatitude() + ds0);
		// double latiMenos = Math.ceil(paisLoc.getLatitude() - ds0);

		for (String aeroporto : gerAero.listarTodos().keySet()) {
			Aeroporto aux = gerAero.listarTodos().get(aeroporto);

			if (aux.getPais() == pais1) {
				pais = aux.getPais();
				break;
			}
		}

		for (String codAeroporto : gerAero.listarTodos().keySet()) {
			Aeroporto aux = gerAero.listarTodos().get(codAeroporto);
			if (aux.getPais().getNome().equals(pais.getNome())) {
				aeroportos.add(aux);
			}
		}

		return aeroportos;
	}

}
