package pucrs.myflight.modelo;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class GerenciadorCias {
	private ArrayList<CiaAerea> empresas;
	
	public GerenciadorCias() {
		empresas = new ArrayList<>();
	}
	
	public void adicionar(CiaAerea nova) {
		empresas.add(nova);
	}
	
	public void carregaCias() throws IOException {
		Path path2 = Paths.get("airlines.dat");
		try (Scanner sc = new Scanner(Files.newBufferedReader(path2, Charset.forName("utf8")))) {
		  sc.useDelimiter("[;\n]"); // separadores: ; e nova linha
		  String header = sc.nextLine(); // pula cabeçalho
		  String codigo, nome;
		  while (sc.hasNext()) {
		    codigo = sc.next();
		    nome = sc.next();		    
		    CiaAerea nova = new CiaAerea(codigo, nome);
		    empresas.add(nova);
		    System.out.format("%s - %s%n", codigo, nome);
		  }
		}
		catch (IOException x) {
		  System.err.format("Erro de E/S: %s%n", x);
		}
	}
	
	public ArrayList<CiaAerea> listarTodas() {
		ArrayList<CiaAerea> copia = new ArrayList<>();
		for(CiaAerea cia: empresas)
			copia.add(cia);
		return copia;				
	}
	
	public CiaAerea buscarCodigo(String cod) {
		for(CiaAerea cia: empresas)
			if(cod.equals(cia.getCodigo()))
				return cia;
		return null; // Não achei!
	}
	
	public CiaAerea buscarNome(String nome) {
		for(CiaAerea cia: empresas)
			if(nome.equals(cia.getNome()))
				return cia;
		return null; // Não achei!
	}
	
	
	
	
	
	
	
	
	
}
