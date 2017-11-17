package pucrs.myflight.gui;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Set;
import java.util.TreeMap;

import org.jxmapviewer.viewer.GeoPosition;

import pucrs.myflight.modelo.Aeroporto;
import pucrs.myflight.modelo.GerenciadorAeroportos;
import pucrs.myflight.modelo.GerenciadorRotas;
import pucrs.myflight.modelo.Pais;
import pucrs.myflight.modelo.Rota;

public class GerenciadorTrafego {
	private TreeMap<Aeroporto, Integer> tamanhos;
	private GerenciadorRotas gerRotas;
	private int tamanhoVetor;
	private Aeroporto[] aeroportos;
	private Pais pais;

	public GerenciadorTrafego(GerenciadorRotas gerRotas, int tamanhoVetor) {
		this.gerRotas = gerRotas;
		this.tamanhoVetor = tamanhoVetor;
		tamanhos = new TreeMap<>();
		aeroportos = new Aeroporto[tamanhoVetor];
	}

	public TreeMap<Aeroporto, Integer> getTamanhos() {
		return tamanhos;
	}

	public Aeroporto[] getAeroportos() {
		return aeroportos;
	}

	public void setTamanhoVetor(int tamanhoVetor) {
		this.tamanhoVetor = tamanhoVetor;
		this.aeroportos = new Aeroporto[tamanhoVetor];
	}

	public void carregaTamanhosHashMap() {

		for (Aeroporto aero : gerRotas.getHashMap().keySet()) {
			tamanhos.put(aero, gerRotas.getHashMap().get(aero).size());
		}

		trafego();
	}

	public void carregaTamanhosArray(ArrayList<Aeroporto> aeroportos) {

		for (int i = 0; i < aeroportos.size(); i++) {
			Set<Rota> verificador = gerRotas.getHashMap().get(aeroportos.get(i));
			if (verificador == null) {
				continue;
			} else {
				tamanhos.put(aeroportos.get(i), verificador.size());
			}

		}

		trafegoPais();
	}

	public void trafego() {
		for (int i = 0; i < tamanhoVetor; i++) {
			aeroportos[i] = tamanhos.lastKey();
			tamanhos.remove(tamanhos.firstKey());

		}
	}

	public void trafegoPais() {
		for (int i = 0; i < tamanhoVetor; i++) {
			aeroportos[i] = tamanhos.lastKey();
			tamanhos.remove(tamanhos.firstKey());

		}

	}

	public ArrayList<Aeroporto> aeroportosDeUmPais(GerenciadorMapa gerenciador, GerenciadorAeroportos gerAero,
			GeoPosition paisLoc) {

		ArrayList<Aeroporto> aeroportos = new ArrayList<>();
		pais = null;

		double ds0 = 5;

		double longiMais = Math.ceil(paisLoc.getLongitude() + ds0);
		double longiMenos = Math.ceil(paisLoc.getLongitude() - ds0);

		double latiMais = Math.ceil(paisLoc.getLatitude() + ds0);
		double latiMenos = Math.ceil(paisLoc.getLatitude() - ds0);

		for (String aeroporto : gerAero.listarTodos().keySet()) {
			Aeroporto aux = gerAero.listarTodos().get(aeroporto);
			if ((aux.getLocal().getLatitude() == paisLoc.getLatitude()
					|| (aux.getLocal().getLatitude() < latiMais && aux.getLocal().getLatitude() > latiMenos))
					&& (aux.getLocal().getLongitude() == paisLoc.getLongitude()
							|| (aux.getLocal().getLongitude() < longiMais
									&& aux.getLocal().getLongitude() > longiMenos))) {
				pais = aux.getPais();
				break;
			}
		}

		for (String codAeroporto : gerAero.listarTodos().keySet()) {
			Aeroporto aux = gerAero.listarTodos().get(codAeroporto);
			if (aux.getPais() == pais) {
				aeroportos.add(aux);
			}
		}

		return aeroportos;
	}
}
