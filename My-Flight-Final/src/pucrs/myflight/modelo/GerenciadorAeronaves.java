package pucrs.myflight.modelo;

import java.util.HashMap;
import java.util.Scanner;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class GerenciadorAeronaves {
	private HashMap<String, Aeronave> aero;

	public GerenciadorAeronaves() {
		aero = new HashMap<>();
	}

	
	//public void OrdenarAerov(){
		//Collections.sort(aero);
	//}
	
	public void addAero(Aeronave nova){
		aero.put(nova.getCodigo(),nova);
	}
	public HashMap<String, Aeronave> listarTodas(){
		HashMap<String, Aeronave> copia = new HashMap<>();
		for (String key: aero.keySet()){
			copia.put(key,aero.get(key));			
		}
		return copia;		
	}

	public Aeronave buscarporCodigo(String cod) {
		Aeronave aerov = aero.get(cod);
		return aerov;
	}


	public HashMap<String, Aeronave> buscarPorDesc(String desc) {
		HashMap<String, Aeronave> aux = new HashMap<>();
		for (String key : aero.keySet()) {
			if (aero.get(key).getDescricao().equals(desc)) {
				aux.put(key, aero.get(key));
			}
		}
		return aux;
	}

	public void carregaDados() throws IOException {
		Path path2 = Paths.get("equipment.dat");
		try (Scanner sc = new Scanner(Files.newBufferedReader(path2, Charset.forName("utf8")))) {
			sc.useDelimiter("[;\n]");
			String header = sc.nextLine();
			String id, descricao, capacidade;
			while (sc.hasNext()) {
				id = sc.next();
				descricao = sc.next();
				capacidade = sc.next().replaceAll("\r", "");
				int cap = Integer.valueOf(capacidade);
				Aeronave nova = new Aeronave(id, descricao, cap);
				aero.put(id, nova);
				System.out.println(id + "    -    " + descricao + "    -    " + capacidade);
			}
		} catch (IOException e) {
			System.err.format("Erro de E/S: %s%n", e);
		}

	}
}
