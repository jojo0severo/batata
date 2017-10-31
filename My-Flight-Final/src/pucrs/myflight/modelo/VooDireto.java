package pucrs.myflight.modelo;

import java.time.Duration;
import java.time.LocalDateTime;

public class VooDireto extends Voo {

	private Rota rota;

	public VooDireto(LocalDateTime datahora, Rota rota) {
		super(datahora);
		this.rota = rota;
	}
	
	public VooDireto(Rota r) {
		super();
		this.rota = r;
	}
	
	@Override
	public Rota getRota() {
		return rota;
	}

	@Override
	public Duration getDuracao() {
		double dist = rota.getOrigem().getLocal()
					.distancia(rota.getDestino().getLocal());
		double tempo = dist/805 + 0.5;
		return Duration.ofMinutes((int)(tempo*60));
	}
	
	@Override
    public String toString() {		
		String aux = super.toString()+" ("+getDuracao()+")";
		aux += "\n    "+rota;
		return aux;    
    }

}
