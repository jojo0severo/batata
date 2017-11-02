package pucrs.myflight.modelo;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class GerenciadorRotas {
	private ArrayList<Rota> rotas;

	public GerenciadorRotas() {
		rotas = new ArrayList<>();
	}

	public void putRota(Rota nova) {
		rotas.add(nova);	
	}


	public ArrayList<Rota> listarTodas() {
		ArrayList<Rota> copia = new ArrayList<>();
		for (Rota r : rotas) {
			copia.add(r);
		}
		return copia;
	}


	public ArrayList<Rota> buscarPorOrigem(Aeroporto aero) {
		ArrayList<Rota> auxiliar = new ArrayList<>();
		for (Rota r : rotas) {
			if (r.getOrigem().equals(aero)) {
				auxiliar.add(r);
			}
		}
		return auxiliar;
	}

	public ArrayList<CiaAerea> buscarCiaporCodAerov(String codaerov) {
		ArrayList<CiaAerea> auxiliar = new ArrayList<>();
		for (Rota r : rotas) {
			if (r.getAeronave().getCodigo().equals(codaerov)) {
				auxiliar.add(r.getCia());
			}
		}
		return auxiliar;
	}

	public ArrayList<Rota> buscarPorCodOrigem(String cod) {
		ArrayList<Rota> aux = new ArrayList<>();
		for (Rota r : rotas) {
			if (r.getOrigem().getCodigo().equals(cod)) {
				aux.add(r);
			}
		}
		return aux;
	}
	
	public ArrayList<Rota> buscarPorCodCia(String cod) {
		ArrayList<Rota> aux = new ArrayList<>();		
		for (Rota r: rotas) {
			if (r.getCia().getCodigo().equals(cod)) {
				aux.add(r);
			}
		}
		return aux;
	}
	
	public void carregaDados(GerenciadorCias gerCia, GerenciadorAeroportos gerAerop, GerenciadorAeronaves gerAerov) throws IOException {
		Path path2 = Paths.get("routes.dat");
		try (Scanner sc = new Scanner(Files.newBufferedReader(path2, Charset.forName("utf8")))) {
			sc.useDelimiter("[;\n]");
			String header = sc.nextLine();
			String nada;
			String cia, origem, destino,paradas, aeronave;
			int Paradas;
			while (sc.hasNext()) {
				cia = sc.next();
				origem = sc.next();
				destino = sc.next();
				nada = sc.next();
				paradas = sc.next();
				aeronave = sc.next();
				Paradas = Integer.valueOf(paradas);
				Rota nova = new Rota(gerCia.buscarCod(cia),gerAerop.buscarPorCodigo(origem),Paradas, gerAerop.buscarPorCodigo(destino),gerAerov.buscarporCodigo(aeronave));
				rotas.add(nova);				
				System.out.println(cia +"    -    "+  origem+"    -    "+destino+"    -    "+aeronave);
			}
		} catch (IOException e) {
			System.err.format("Erro de E/S: %s%n", e);
		}
		
	}
}











