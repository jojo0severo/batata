	package pucrs.myflight.modelo;


import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class GerenciadorVoos {
	private ArrayList<Voo> voos;
	
	public GerenciadorVoos(){
		voos = new ArrayList<>();
	}
	public void addVoo(Voo novo){
		voos.add(novo);
		
	}
	public ArrayList<Voo> listarTodos(){
		ArrayList<Voo> copia = new ArrayList<>();
		for (Voo batata: voos){
			copia.add(batata);
		
		}
		return copia;
	}
	public ArrayList<Voo> buscarData(LocalDateTime data){
		ArrayList<Voo> auxiliar = new ArrayList<>();
		for(Voo batata: voos){
			if(batata.getDatahora().equals(data)){
				auxiliar.add(batata);
			}
		}
		return auxiliar;
	}
	public ArrayList<Voo> buscarPorCodComp(String CodComp){
            ArrayList<Voo> auxiliar = new ArrayList<>();
            for(Voo batata: voos){
                if(batata.getRota().getCia().getCodigo().equals(CodComp)){
                    auxiliar.add(batata);
                }
            }
            return auxiliar;
        }
		public ArrayList<Geo> buscarPorDataAerop(DayOfWeek b){
		ArrayList<Geo> auxiliar = new ArrayList<>();
		for(Voo batata: voos){
			if(batata.getDatahora().getDayOfWeek().equals(b)){
			auxiliar.add(batata.getRota().getOrigem().getLocal());
			}
		}
		return auxiliar;
	}
}
