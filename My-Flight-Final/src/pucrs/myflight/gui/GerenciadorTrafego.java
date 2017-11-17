package pucrs.myflight.gui;

import java.util.ArrayList;
import java.util.TreeMap;

import org.jxmapviewer.viewer.GeoPosition;

import pucrs.myflight.modelo.Aeroporto;
import pucrs.myflight.modelo.GerenciadorAeroportos;
import pucrs.myflight.modelo.GerenciadorRotas;
import pucrs.myflight.modelo.Pais;

public class GerenciadorTrafego {
	private TreeMap<Integer, ArrayList<Aeroporto>> tamanhos;
	private GerenciadorRotas gerRotas;
	private int tamanhoVetor;
	private ArrayList<Aeroporto> aeroportosPais;
	private ArrayList<Aeroporto> aeroportos;
	private Pais pais;

	public GerenciadorTrafego(GerenciadorRotas gerRotas, int tamanhoVetor) {
		this.gerRotas = gerRotas;
		this.tamanhoVetor = tamanhoVetor;
		tamanhos = new TreeMap<>();
		aeroportos = new ArrayList<>();
	}

	public TreeMap<Integer, ArrayList<Aeroporto>> getTamanhos() {
		return tamanhos;
	}

	public ArrayList<Aeroporto> getAeroportos() {
		return aeroportos;
	}
	
	public int getTamanhoVetor() {
		return tamanhoVetor;
	}

	public void setTamanhoVetor(int tamanhoVetor) {
		this.tamanhoVetor = tamanhoVetor;
	}

	public void carregaTamanhos() {
		tamanhos = gerRotas.getTamanhos();

		if (pais == null) {
			trafego();
		} else {
			trafegoPais();
		}

	}

	public void trafego() {
		for (int i = 0; i < tamanhoVetor; i++) {
			Integer aux = tamanhos.lastKey();
			for (Aeroporto aero : tamanhos.get(aux)) {
				aeroportos.add(aero);
			}
			tamanhos.remove(tamanhos.lastKey());
		}
	}

	public void trafegoPais() {
		for (int i = 0; i < tamanhoVetor; i++) {
			Integer aux = tamanhos.lastKey();
			for (Aeroporto aero : tamanhos.get(aux)) {
				if (aero.getPais() == pais) {
					aeroportos.add(aero);
				}
			}
			tamanhos.remove(tamanhos.lastKey());
		}
	}

	public void aeroportosDeUmPais(GerenciadorAeroportos gerAero, GeoPosition paisLoc) {

		aeroportosPais = new ArrayList<>();
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
				aeroportosPais.add(aux);
			}
		}
	}
}
