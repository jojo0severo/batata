package pucrs.myflight.gui;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;

import org.jxmapviewer.viewer.GeoPosition;

import pucrs.myflight.modelo.Aeroporto;
import pucrs.myflight.modelo.GerenciadorAeroportos;
import pucrs.myflight.modelo.GerenciadorRotas;
import pucrs.myflight.modelo.Pais;
import pucrs.myflight.modelo.Rota;

public class GerenciadorTrafego {
	private ArrayList<Integer> tamanhos;
	private Integer[] valores;
	private GerenciadorRotas gerRotas;
	private int tamanhoVetor;
	private Aeroporto[] aeroportos;

	public GerenciadorTrafego(GerenciadorRotas gerRotas, int tamanhoVetor) {
		this.gerRotas = gerRotas;
		this.tamanhoVetor = tamanhoVetor;
		tamanhos = new ArrayList<>();
		valores = new Integer[tamanhoVetor];
		aeroportos = new Aeroporto[tamanhoVetor];
	}

	public ArrayList<Integer> getTamanhos() {
		return tamanhos;
	}

	public Aeroporto[] getAeroportos() {
		return aeroportos;
	}

	public void setTamanhoVetor(int tamanhoVetor) {
		this.tamanhoVetor = tamanhoVetor;
		this.aeroportos = new Aeroporto[tamanhoVetor];
		this.valores = new Integer[tamanhoVetor];
	}

	public void carregaTamanhosHashMap() {

		for (Aeroporto aero : gerRotas.getHashMap().keySet()) {
			tamanhos.add(gerRotas.getHashMap().get(aero).size());
		}
		Collections.sort(tamanhos);

		carregaValores();
	}
	
	public void carregaTamanhosArray(ArrayList<Aeroporto> aeroportos) {
		
		for(int i =0; i<aeroportos.size();i++ ) {
			Set<Rota> verificador = gerRotas.getHashMap().get(aeroportos.get(i));
			if(verificador ==null) {
				continue;
			}
			else {
				tamanhos.add(verificador.size());
			}
			
		}
		
		Collections.sort(tamanhos);
		
		carregaValores();
	}

	public void carregaValores() {
		for (int i = 0; i < tamanhoVetor; i++) {
			valores[i] = (tamanhos.get(tamanhos.size() - i - 1));
		}

		trafego();
	}

	public void trafego() {
		int cont = 0;
		for (Aeroporto aero : gerRotas.getHashMap().keySet()) {
			cont = 0;
			for (int i = 0; i < valores.length; i++) {
				Integer integer = valores[i];
				if (gerRotas.getHashMap().get(aero).size() == integer) {
					aeroportos[cont] = aero;
				}
				cont++;
			}
		}
	}

	public ArrayList<Aeroporto> aeroportosDeUmPais(GerenciadorMapa gerenciador, GerenciadorAeroportos gerAero, GeoPosition paisLoc) {
		
		ArrayList<Aeroporto> aeroportos = new ArrayList<>();
		Pais pais = null;
		
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
			if (aux.getPais().getNome().equals(pais.getNome())) {
				aeroportos.add(aux);
			}
		}
		
		return aeroportos;
	}
	

}
