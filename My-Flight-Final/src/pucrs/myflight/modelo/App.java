package pucrs.myflight.modelo;

import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.Arrays;
import java.io.IOException;
import java.lang.reflect.Array;
import java.time.Duration;

public class App {

	public static void main(String[] args) {

		// Cria todos os gerenciadores
		GerenciadorCias gerCias = new GerenciadorCias();
		GerenciadorAeronaves gerAvioes = new GerenciadorAeronaves();
		GerenciadorAeroportos gerAero = new GerenciadorAeroportos();
		GerenciadorRotas gerRotas = new GerenciadorRotas();
		GerenciadorVoos gerVoos = new GerenciadorVoos();

		try {
			gerCias.carregaCias();
		} catch (IOException e) {
			System.out.println("Impossível ler airlines.dat");
			System.exit(1);
		}
		
		// Cria cias. aéreas
		// Demonstra uso do método de classe (static) getTotalCias
		//System.out.println("Total de empresas: " + CiaAerea.getTotalCias());
		//CiaAerea cia1 = new CiaAerea("JJ", "LATAM");
		//System.out.println("Total de empresas: " + CiaAerea.getTotalCias());
		//CiaAerea cia2 = new CiaAerea("G3", "Gol");
		//System.out.println("Total de empresas: " + CiaAerea.getTotalCias());
		//CiaAerea cia3 = new CiaAerea("TP", "TAP");
		//System.out.println("Total de empresas: " + CiaAerea.getTotalCias());
		
		CiaAerea cia1 = gerCias.buscarCodigo("JJ");
		CiaAerea cia2 = gerCias.buscarCodigo("G3");
		CiaAerea cia3 = gerCias.buscarCodigo("TP");

		// ...e adiciona elas no gerenciador
		//gerCias.adicionar(cia1);
		//gerCias.adicionar(cia2);
		//gerCias.adicionar(cia3);
		
		// Teste: buscando cia pelo código
		System.out.println("Buscando código G3...");
		CiaAerea result = gerCias.buscarCodigo("G3");
		if (result != null) {
			System.out.println("cia: " + result.getNome());
		}
		System.out.println();

		// Cria dois aeroportos
		Aeroporto origem = new Aeroporto("POA", "Salgado Filho", new Geo(-29.9939, -51.1711));
		Aeroporto destino = new Aeroporto("GRU", "Guarulhos", new Geo(-23.4356, -46.4731));
		Aeroporto lis = new Aeroporto("LIS", "Lisbon", new Geo(-38.772,-9.1342));
		Aeroporto mia = new Aeroporto("MIA", "Miami International", new Geo(25.7933, -80.2906));
		// ...e adiciona eles no gerenciador
		gerAero.adicionar(origem);
		gerAero.adicionar(destino);
		gerAero.adicionar(lis);
		gerAero.adicionar(mia);

		// Cria 3 aeronaves
		Aeronave aeronave = new Aeronave("733", "Boeing 737-300", 140);
		Aeronave aeronave2 = new Aeronave("73G", "Boeing 737-700", 126);
		Aeronave aeronave3 = new Aeronave("380", "Airbus A380", 644);
		System.out.println("Total aeronaves: " + aeronave.getTotal());
		// ...e adiciona elas no gerenciador
		gerAvioes.adicionar(aeronave);
		gerAvioes.adicionar(aeronave2);
		gerAvioes.adicionar(aeronave3);
		System.out.println();

		// Para criar vôos, é preciso data+hora e duração
		LocalDateTime datahora1 = LocalDateTime.of(2017, 8, 14, 22, 3);
		System.out.println("Data e hora: " + datahora1);

		Duration duracao1 = Duration.ofMinutes(90); // 1:30
		System.out.println("Duração: " + duracao1);

		// Cria uma rota
		Rota r1 = new Rota(cia1, origem, destino, aeronave);
		// ...e adiciona ela no gerenciador
		gerRotas.adicionar(r1);
		Rota r2 = new Rota(cia2, origem, mia, aeronave3);
		Rota r3 = new Rota(cia3, destino, lis, aeronave2);
		Rota r4 = new Rota(cia3, lis, mia, aeronave2);
		gerRotas.adicionar(r2);
		gerRotas.adicionar(r3);
		gerRotas.adicionar(r4);

		// ...e dois vôos usando a mesma rota, mas data+hora diferentes
		// (explorando o conceito de sobrecarga nos construtores)
		VooDireto voo1 = new VooDireto(datahora1, r1);
		VooDireto voo2 = new VooDireto(r1);
		gerVoos.adicionar(voo1);
		gerVoos.adicionar(voo2);

		// Exemplo de método de classe: o método distancia
		// pode ser usado através da classe Geo, recebendo
		// dois objetos
		Geo poa = origem.getLocal();
		Geo gru = destino.getLocal();
		double dist = Geo.distancia(poa, gru);
		System.out.println("Dist. POA-GRU: " + dist);
		// ... mas como há uma versão sobrecarregada,
		// também pode ser usado com objetos
		double dist2 = poa.distancia(gru);
		double dist3 = gru.distancia(poa);
		System.out.println("Dist. POA-GRU: " + dist2);
		System.out.println("Dist. GRU-POA: " + dist3);

		// Exemplo de ordenação implementando a interface Comparable<>:
		for (Aeronave a : gerAvioes.listarTodas())
			System.out.println("Aeronave: " + a.getDescricao());

		System.out.println("*** Ordenando por descrição...");
		gerAvioes.ordenarDescricao();

		for (Aeronave a : gerAvioes.listarTodas())
			System.out.println("Aeronave: " + a.getDescricao());

		// Demonstração de compareTo com tipos primitivos (int)
		// e classes (Strings)
		// String a = "arvore";
		// String b = "bola";
		//
		// int res = a.compareTo(b);
		// System.out.println("compareTo = "+res);
		// res = b.compareTo(a);
		// System.out.println("compareTo = "+res);
		//
		// int[] vet = { 9, 8, 7, 6, 5, 4, 3, 2, 1 };
		// Arrays.sort(vet);
		// for(int v: vet)
		// System.out.print(v+" ");
		// System.out.println();
		
		System.out.println("\nOrdenando aeroportos pelo nome...");
		gerAero.ordenarNome();
		for(Aeroporto a: gerAero.listarTodos())
			System.out.println(a.getNome());
		
		System.out.println("\nOrdenando aeroportos pelo código...");
		gerAero.ordenarCodigo();
		for(Aeroporto a: gerAero.listarTodos())
			System.out.println(a.getNome());
		
		System.out.println("\nLista de rotas:\n");
		for(Rota r: gerRotas.listarTodas())
			System.out.println(r);
		
		/*
		System.out.println("\nOrdenação pelo nome da cia:\n");
		gerRotas.ordenarCias();
		for(Rota r: gerRotas.listarTodas())
			System.out.println(r);
		
		System.out.println("\nOrdenação pelo aeroporto de origem:\n");
		gerRotas.ordenarOrigem();
		for(Rota r: gerRotas.listarTodas())
			System.out.println(r);
			*/
		
		System.out.println("\nOrdenação pelo aeroporto de origem, desempate pelo nome da cia:\n");
		gerRotas.ordenarOrigemCias();
		for(Rota r: gerRotas.listarTodas())
			System.out.println(r);
		
		// Exibindo os vôos com toString:
		System.out.println("\nVôos:\n");
		System.out.println(voo1);
		
		LocalDateTime datahora3 = LocalDateTime.of(2017, 9, 6, 22, 30);
		Duration duracao3 = Duration.ofHours(22);
		VooEscalas voo3 = new VooEscalas(r1, datahora3, duracao3);
		voo3.adicionarRota(r3);
		voo3.adicionarRota(r4);
		System.out.println();
		System.out.println(voo3);	
		
		// Polimorfismo de variáveis: o ArrayList<Voo>
		// suporta também objetos derivados de Voo (subclasses)
		// ou seja, VooEscalas
		gerVoos.adicionar(voo3);
		
		for(Voo v: gerVoos.listarTodos())
		{
			if(v instanceof VooEscalas)
			{
				VooEscalas aux = (VooEscalas) v;
				System.out.println("Voo com escalas: "+aux.totalRotas());
			}
			else {
				System.out.println("Voo sem escalas");
			}
		}
		
		// Polimorfismo de métodos: o for irá
		// chamar o toString do tipo do objeto
		// armazenado em cada posição
		System.out.println();
		for(Voo v: gerVoos.listarTodos())
		{
			System.out.println(v);
		}
	}
}
