
package pucrs.myflight.modelojojo;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Scanner;

public class GerenciadorCias {
	private HashMap<String,CiaAerea> empresas;

	public GerenciadorCias() {
		empresas = new HashMap<>();
		try {
			carregaDados();
		} catch (IOException e) {
			System.err.format("Erro de E/S: %s%n", e);
		}
	}

	public void addCia(CiaAerea nova) {
		empresas.put(nova.getCodigo(),nova);
	}

	public HashMap<String,CiaAerea> listarTodas() {
		HashMap<String,CiaAerea> copia = new HashMap<>();
		for (String key: empresas.keySet()) {
			copia.put(key,empresas.get(key));
		}
		return copia;
	}

	public CiaAerea buscarCod(String cod) {
		CiaAerea cia = empresas.get(cod);
		return cia;
	}

	public CiaAerea buscarNome(String nome) {
		for (String key: empresas.keySet()) {
			if (nome.equalsIgnoreCase(empresas.get(key).getNome())) {
				return empresas.get(key);
			}
		}
		return null;
	}

	public void carregaDados() throws IOException {
		Path path2 = Paths.get("airlines.dat");
		try (Scanner sc = new Scanner(Files.newBufferedReader(path2, Charset.forName("utf8")))) {
			sc.useDelimiter("[;\n]");
			String header = sc.nextLine();
			String codigo, nome;
			while (sc.hasNext()) {
				codigo = sc.next();
				nome = sc.next();
				CiaAerea nova = new CiaAerea(codigo, nome);
				empresas.put(codigo,nova);
				System.out.println(codigo +"    -    "+  nome);
			}
		} catch (IOException e) {
			System.err.format("Erro de E/S: %s%n", e);
		}
		
	}
}
