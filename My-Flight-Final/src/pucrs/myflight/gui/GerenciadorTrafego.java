package pucrs.myflight.gui;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

import org.jxmapviewer.viewer.GeoPosition;

import pucrs.myflight.modelo.Aeroporto;
import pucrs.myflight.modelo.GerenciadorAeroportos;
import pucrs.myflight.modelo.GerenciadorRotas;
import pucrs.myflight.modelo.Pais;

public class GerenciadorTrafego {
	private Map<Integer, ArrayList<Aeroporto>> tamanhos;
	private GerenciadorRotas gerRotas;
	private int tamanhoVetor;
	private ArrayList<Aeroporto> aeroportos;
	private Pais pais;

	public GerenciadorTrafego(GerenciadorRotas gerRotas, int tamanhoVetor) {
		this.gerRotas = gerRotas;
		this.tamanhoVetor = tamanhoVetor;
		tamanhos = new TreeMap().descendingMap();
		aeroportos = new ArrayList<>();
	}

	public Map<Integer, ArrayList<Aeroporto>> getTamanhos() {
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
		int cont = 0;
		while (cont < 10) {
			for (Integer integer : tamanhos.keySet()) {
				for (Aeroporto aero : tamanhos.get(integer)) {
					aeroportos.add(aero);
					cont++;
				}
			}
		}
	}

	public void trafegoPais() {
		int cont = 0;
		while (cont < 10) {
			for (Integer integer : tamanhos.keySet()) {
				for (Aeroporto aero : tamanhos.get(integer)) {
					if (aero.getPais() == pais) {
						aeroportos.add(aero);
						cont++;
					}
				}
			}
		}
	}

	public void aeroportosDeUmPais(GerenciadorAeroportos gerAero, GeoPosition paisLoc) {
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
	}
}
