package pucrs.myflight.modelo;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class VooEscalas extends Voo {

	private ArrayList<Rota> rotas;

	public VooEscalas(Rota rota, LocalDateTime datahora, Duration duracao) {
		super(datahora);
		//this.rota = rota;
		this.rotas = new ArrayList<>();
		adicionarRota(rota);
		//this.datahora = datahora;
		//this.duracao = duracao;
		//this.status = Status.CONFIRMADO; // default é confirmado
	}
	
	public void adicionarRota(Rota r) {
		rotas.add(r);
	}
	
	public int totalRotas() {
		return rotas.size();
	}
	
	@Override
    public String toString() {
		StringBuilder aux = new StringBuilder();
		aux.append(super.toString()+" ("+getDuracao()+")");
		for(Rota r: rotas)
				aux.append("\n    "+r);
		return aux.toString();
       //return getStatus() + " " + getDatahora() +
       //	   "("+getDuracao()+"):\n" + "    "+getRota()
       //	   +"\n    "+rotaFinal;
    }

	@Override
	public Rota getRota() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Duration getDuracao() {
		int dist = 0;
		for(Rota r: rotas) {
			dist += r.getOrigem().getLocal()
					.distancia(r.getDestino().getLocal());
		}
		double tempo = dist/805 + 0.5*rotas.size();
		return Duration.ofMinutes((int)(tempo*60));
	}

}
