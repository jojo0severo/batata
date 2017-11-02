package pucrs.myflight.modelo;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class VooEscalas extends Voo
{
    private ArrayList<Rota> rotas;
    private int count;
    // Construtor
    public VooEscalas(Rota rota, Rota rotaFinal, LocalDateTime datahora, Duration duracao) {
    	super(rota, datahora); 
        this.rotas = new ArrayList<Rota>();
    }

   public void addRota(Rota n){
	   rotas.add(n);
	   count++;
   }
   
   public ArrayList<Rota> totalRotas(){
	   ArrayList<Rota> aux = new ArrayList<>();
	   for(int i =0; i < rotas.size();i++){
		   aux.addAll(rotas);
	   }
	   return aux;
   }
	
   public Duration getDuracao(){
	  double dist=0;  
	  for(Rota aux: rotas){		  
		  dist+=aux.getOrigem().getLocal().distancia(aux.getDestino().getLocal());		  
	  }	  
	  long tempo = (long) ((dist/805) + (0.5*rotas.size()));	  
	  return Duration.ofHours(tempo);	 
   }

    @Override
    public String toString() {
    	StringBuilder VoEs = new StringBuilder();
    	VoEs.append(rotas);
    	return VoEs.toString();
    }

	@Override
	public Rota getRota() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Status getStatus() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LocalDateTime getDatahora() {
		// TODO Auto-generated method stub
		return null;
	}

	
	
}