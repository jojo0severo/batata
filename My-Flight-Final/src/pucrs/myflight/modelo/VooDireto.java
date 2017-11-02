package pucrs.myflight.modelo;

import java.time.Duration;
import java.time.LocalDateTime;

public class VooDireto extends Voo {

	private Rota rota;

	public VooDireto(Rota rota, LocalDateTime datahora) {
		super(rota, datahora);
		this.rota = rota;
	}

	@Override
	public Rota getRota() {
		// TODO Auto-generated method stub
		return rota;
	}

	@Override
	public Duration getDuracao() {
		long dist = 0;
		dist = (long) (this.rota.getOrigem().getLocal().distancia(rota.getDestino().getLocal()) + 0.5);
		return Duration.ofHours(dist);
	}

	public String toString() {
		String aux = super.toString() + "Dura�ao" + getDuracao();
		return aux;
	}

	@Override
	public LocalDateTime getDatahora() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Status getStatus() {
		// TODO Auto-generated method stub
		return null;
	}

}
