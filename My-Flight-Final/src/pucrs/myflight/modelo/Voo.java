package pucrs.myflight.modelo;

import java.time.Duration;
import java.time.LocalDateTime;

public abstract class Voo {
	
	public enum Status { CONFIRMADO, ATRASADO, CANCELADO };
	
	private LocalDateTime datahora;
//	private Duration duracao;
//	private Rota rota;
	private Status status;
	
	// Agora só recebemos a data e horário
	public Voo(LocalDateTime datahora) {
//		this.rota = rota;
		this.datahora = datahora;
//		this.duracao = duracao;
		this.status = Status.CONFIRMADO; // default é confirmado
	}
	
	// Construtor vazio: cria um vôo para o dia 28/8/2017, 12:00
	public Voo() {	
		this(LocalDateTime.of(2017, 8, 28, 12, 0));
		//this.rota = rota;
		//this.duracao = duracao;
		//this.status = Status.CONFIRMADO;
		//this.datahora = LocalDateTime.of(2017, 8, 28, 12, 0);
	}
	
	// getRota é abstrato, pois não temos mais a rota aqui
	public abstract Rota getRota();
	
	// getDuracao é abstrato, pois não temos mais a duração aqui
	public abstract Duration getDuracao();
	
	public LocalDateTime getDatahora() {
		return datahora;
	}
	
	public Status getStatus() {
		return status;
	}
	
	public void setStatus(Status novo) {
		this.status = novo;
	}

	@Override
    public String toString() {
       return datahora + " -  " + status;    		   
    }
}
