package pucrs.myflight.modelojojo;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Collections;
import java.util.Scanner;

public class GerenciadorPaises {
	private HashMap<String,Pais> paises;

	public GerenciadorPaises() {
		
		paises = new HashMap<>(); 
	}
	public void putPais(Pais novo){
		paises.put(novo.getCodigo(),novo);
	}
	public HashMap<String,Pais> listarTodos(){
		HashMap<String,Pais> copia= new HashMap<>();
		for(String key: paises.keySet()){
			copia.put(key,paises.get(key));
		}
		return copia;
	}
	public Pais buscarPorCodigo(String cod){
		Pais pais =  paises.get(cod);
		return pais;
	}
	
	//public void OrdenarAerov(){
		//Collections.sort(paises);
	//}
	
	public void carregaDados() throws IOException {
		Path path2 = Paths.get("countries.dat");
		try (Scanner sc = new Scanner(Files.newBufferedReader(path2, Charset.forName("utf8")))) {
			sc.useDelimiter("[;\n]");
			String header = sc.nextLine();
			String codigo, nome;
			while (sc.hasNext()) {
				codigo = sc.next();
				nome=sc.next();
				Pais novo = new Pais(codigo, nome);
				paises.put(codigo,novo);
				System.out.println(codigo +"    -    "+  nome);
			}
		} catch (IOException e) {
			System.err.format("Erro de E/S: %s%n", e);
		}
		
	}
	
}
