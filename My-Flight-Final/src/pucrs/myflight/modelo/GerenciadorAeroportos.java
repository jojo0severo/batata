package pucrs.myflight.modelo;



import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Set;

public class GerenciadorAeroportos {
	private HashMap<String,Aeroporto> aeroporto;	
	private int count;

	public GerenciadorAeroportos() {
		aeroporto = new HashMap<>(); 
		count = 0;
	}
	public void addAeroporto(Aeroporto novo){
		aeroporto.put(novo.getCodigo(),novo);
	}
	
	public HashMap<String,Aeroporto> listarTodos(){
		HashMap<String,Aeroporto> copia= new HashMap<>();
		for(String key: aeroporto.keySet()){
			copia.put(key,aeroporto.get(key));
		}
		return copia;
	}
	
	public ArrayList<Aeroporto> listarTodosArray(){
		ArrayList<Aeroporto> copia= new ArrayList<>();
		for(String key: aeroporto.keySet()){
			copia.add(aeroporto.get(key));
		}
		return copia;
	}
	
	public Aeroporto buscarPorCodigo(String cod){
		return aeroporto.get(cod.toUpperCase());
	}
	
	public Aeroporto buscarPorNome(String nome) {
		for(Aeroporto aero: aeroporto.values()){
			if(aero.getNome().equals(nome)){
				return aero;
			}
		}
		return null;
	}
	
	public void carregaDados(GerenciadorPaises gerPais) throws IOException {

		Path path2 = Paths.get("airports.dat");
		try (Scanner sc = new Scanner(Files.newBufferedReader(path2, Charset.forName("utf8")))) {
			sc.useDelimiter("[;\n]");
			String header = sc.nextLine();
			String codigo, latitude, longitude,nome,codigop;
			Pais pais;
			Geo geo;
			while (sc.hasNext()) {
				codigo = sc.next();
				latitude = sc.next();
				longitude = sc.next();	
				nome = sc.next();
				codigop = sc.next();
				pais = gerPais.buscarPorCodigo(codigop.replaceAll("\r", ""));
				geo = new Geo(Double.parseDouble(latitude),Double.parseDouble(longitude));				
				Aeroporto nova = new Aeroporto(codigo,nome, geo, pais);
				aeroporto.put(nova.getCodigo(),nova);
			}
		}
	}
}
	
	

