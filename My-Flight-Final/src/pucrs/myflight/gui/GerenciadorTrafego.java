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
		valores = new Integer[tamanhoVetor];
		aeroportos = new Aeroporto[tamanhoVetor];
	}

	public ArrayList<Integer> getTamanhos() {
		return tamanhos;
	}

	public Aeroporto[] getAeroportos() {
		return aeroportos;
	}

	public void setPosicoes(int tamanhoVetor) {
		this.tamanhoVetor = tamanhoVetor;
		aeroportos = new Aeroporto[tamanhoVetor];
	}

	public void trafego() {
		carregaTamanhos();
		carregaValores();
		int cont = 0;
		//Percore os aeroportos
		for (Aeroporto aero : gerRotas.getHashMap().keySet()) {
			cont = 0;
			//Percorre os tamanhos dos Set<Rota> salvos
			for (Integer integer : valores) {
				//Verifica se o tamanho do Set<Rota> da variavel 'aero' é igual a um dos tamanhos salvos
				if (gerRotas.getHashMap().get(aero).size() == integer) {
					//Adiciona em um vetor de aeroportos do tamanho informado pelo usuario
					aeroportos[cont] = aero;
				}
				cont++;
			}
		}
	}

	public void carregaTamanhos() {
		//Percorre os aeroportos salvando o tamanho dos Set<Rota> referentes a cada um
		for (Aeroporto aero : gerRotas.getHashMap().keySet()) {
			tamanhos.add(gerRotas.getHashMap().get(aero).size());
		}		
		//Organiza o vetor
		Collections.sort(tamanhos);
	}
	
	public void carregaValores() {
		//Salva o tamanho dos Set<Rota> em um vetor do tamanho que o usuario informar
		for (int i = 1; i < tamanhoVetor; i++) {
			valores[i]=(tamanhos.get(tamanhoVetor - i));
		}
	}
}







