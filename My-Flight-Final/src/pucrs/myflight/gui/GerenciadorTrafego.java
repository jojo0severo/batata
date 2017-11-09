package pucrs.myflight.gui;

import java.util.ArrayList;
import java.util.Collections;

import pucrs.myflight.modelo.Aeroporto;
import pucrs.myflight.modelo.GerenciadorRotas;

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


	public void carregaTamanhos() {
		
		for (Aeroporto aero : gerRotas.getHashMap().keySet()) {
			tamanhos.add(gerRotas.getHashMap().get(aero).size());
		}		
		Collections.sort(tamanhos);
		
		carregaValores();
	}
	
	public void carregaValores() {
		for (int i = 1; i < tamanhoVetor; i++) {
			valores[i]=(tamanhos.get(tamanhoVetor - i));
		}
		
		trafego();
	}
	
	public void trafego() {
		int cont = 0;
		for (Aeroporto aero : gerRotas.getHashMap().keySet()) {
			cont = 0;
			for (Integer integer : valores) {
				if (gerRotas.getHashMap().get(aero).size() == integer) {
					aeroportos[cont] = aero;
				}
				cont++;
			}
		}
	}
}







