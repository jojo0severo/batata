package pucrs.myflight.modelo;

import java.time.Duration;
import java.time.LocalDateTime;

public abstract class Voo {
	
	public enum Status { CONFIRMADO, ATRASADO, CANCELADO };
	
	private LocalDateTime datahora;
//	private Duration duracao;
//	private Rota rota;
	private Status status;
	
	public Voo(Rota rota, LocalDateTime datahora) {
	//	this.rota = rota;
		this.datahora = datahora;
	//	this.duracao = duracao;
		this.status = Status.CONFIRMADO; // default é confirmado
	}

	public Voo(Rota rota) {
		// TODO Auto-generated constructor stub
	}

	public abstract Rota getRota() ;
	
	public abstract LocalDateTime getDatahora();
	
	public abstract Duration getDuracao();
	
	public abstract Status getStatus();
	
	public void setStatus(Status novo) {
		this.status = novo;
	}

	@Override
	public String toString() {		
		return "Voo:"+ "datahora=" + datahora +"status=" + status;
	}
	
}
