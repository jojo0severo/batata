package pucrs.myflight.gui;

import java.util.*;

import pucrs.myflight.modelo.*;

public class GerenciadorTrafego {

	private Map<Integer, ArrayList<Aeroporto>> tamanhosJoJo;
	private ArrayList<Integer> tamanhos;
	private ArrayList<Integer> valores;
	private GerenciadorRotas gerRotas;
	private ArrayList<Aeroporto> aeroportosMundial;
	private int tamanhoVetor;
	private ArrayList<Aeroporto> aeroportos;
	private Aeroporto[] aeroportos1;
	private Pais pais = null;

	public GerenciadorTrafego(GerenciadorRotas gerRotas, int tamanhoVetor) {
		this.gerRotas = gerRotas;
		this.tamanhoVetor = tamanhoVetor;
		tamanhos = new ArrayList<>();
		valores = new ArrayList<>();
		aeroportos = new ArrayList<>(10);
		aeroportos1 = new Aeroporto[10];
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

	public ArrayList<Aeroporto> getAeroportos() {
		return aeroportos;
	}

	public Aeroporto[] getAeroportos1() {
		return aeroportos1;
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

	public void trafego() {
		aeroportos.clear();

		for (Aeroporto aero : gerRotas.getHashMap().keySet()) {
			for (int i = 0; i < valores.size(); i++) {
				Integer integer = valores.get(i);
				if (gerRotas.getHashMap().get(aero).size() == integer) {
					// aeroportos.add(aero);
					aeroportos1[i] = aero;
					valores.remove(i);
					break;
				}
			}
		}
	}

	public void trafegoPais() {
		aeroportos.clear();
		Collections.sort(valores);
		for (int i : gerRotas.getTamanhos().keySet()) {
			ArrayList<Aeroporto> aux = gerRotas.getTamanhos().get(i);
			if (aeroportos.size() <= 9) {
				for (Aeroporto aero : aux)
					if (aero.getPais() == pais) {
						if (aeroportos.size() <= 9) {
							aeroportos.add(aero);
						}
					}
			} else {
				break;
			}

		}
	}

	public ArrayList<Aeroporto> aeroportosDeUmPais(GerenciadorMapa gerenciador, GerenciadorAeroportos gerAero,
			Pais pais1) {

		ArrayList<Aeroporto> aeroportos = new ArrayList<>();

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
