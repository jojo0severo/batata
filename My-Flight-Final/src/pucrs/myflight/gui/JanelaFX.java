package pucrs.myflight.gui;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import javax.swing.SwingUtilities;
import javax.swing.plaf.synth.SynthSeparatorUI;

import javafx.scene.control.*;
import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.viewer.GeoPosition;
import javafx.application.Application;
import javafx.beans.InvalidationListener;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingNode;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import pucrs.myflight.modelo.Aeroporto;
import pucrs.myflight.modelo.CiaAerea;
import pucrs.myflight.modelo.Geo;
import pucrs.myflight.modelo.GerenciadorAeronaves;
import pucrs.myflight.modelo.GerenciadorAeroportos;
import pucrs.myflight.modelo.GerenciadorCias;
import pucrs.myflight.modelo.GerenciadorPaises;
import pucrs.myflight.modelo.GerenciadorRotas;
import pucrs.myflight.modelo.Pais;
import pucrs.myflight.modelo.Rota;

public class JanelaFX extends Application {

	final SwingNode mapkit = new SwingNode();

	private GerenciadorCias gerCias;
	private GerenciadorAeroportos gerAero;
	private GerenciadorRotas gerRotas;
	private GerenciadorAeronaves gerAvioes;
	private GerenciadorMapa gerenciador;
	private EventosMouse mouse;
	private ObservableList<CiaAerea> comboCiasData;
	private ObservableList<Pais> comboPaisesData;
	private ComboBox<CiaAerea> comboCia;
	private ComboBox<Pais> comboPais;	
	private ComboBox<Aeroporto> comboAeroporto;
	private GerenciadorPaises gerPais;

	@Override
	public void start(Stage primaryStage) throws Exception {

		setup();

		GridPane leftTopPane = new GridPane();
		GridPane centerTopPane = new GridPane();
		GridPane rightTopPane = new GridPane();
		GridPane leftBottomPane = new GridPane();
		GridPane centerBottomPane = new GridPane();
		GridPane rightBottomPane = new GridPane();

		leftTopPane.setAlignment(Pos.CENTER);
		leftTopPane.setHgap(10);
		leftTopPane.setVgap(10);
		leftTopPane.setPadding(new Insets(10, 10, 10, 10));

		centerTopPane.setAlignment(Pos.CENTER);
		centerTopPane.setHgap(10);
		centerTopPane.setVgap(10);
		centerTopPane.setPadding(new Insets(10, 10, 10, 10));

		rightTopPane.setAlignment(Pos.CENTER);
		rightTopPane.setHgap(10);
		rightTopPane.setVgap(10);
		rightTopPane.setPadding(new Insets(10, 10, 10, 10));

		leftBottomPane.setAlignment(Pos.CENTER);
		leftBottomPane.setHgap(10);
		leftBottomPane.setVgap(10);
		leftBottomPane.setPadding(new Insets(10, 10, 10, 10));

		centerBottomPane.setAlignment(Pos.CENTER);
		centerBottomPane.setHgap(10);
		centerBottomPane.setVgap(10);
		centerBottomPane.setPadding(new Insets(10, 10, 10, 10));

		rightBottomPane.setAlignment(Pos.CENTER);
		rightBottomPane.setHgap(10);
		rightBottomPane.setVgap(10);
		rightBottomPane.setPadding(new Insets(10, 10, 10, 10));

		// Primeiro exercicio
		Text selecionar = new Text("Selecione o pais");
		Button btnConsulta1 = new Button("Exercicio 1");
		btnConsulta1.setOnAction(e -> {
			exercicio1();
		});

		// Segundo Exercicio
		Button btnConsulta2 = new Button("Exercicio 2");
		btnConsulta2.setOnAction(e -> {
			exercicio2();
		});

		// Terceiro Exercicio
		Text boniteza = new Text("Informe a distancia m�xima das rotas");
		TextField distancia = new TextField();
		Text boniteza2 = new Text("Selecione o aeroporto que deseja buscar");
		comboAeroporto = new ComboBox(ObservableListAeroportos());
		Button btnConsulta3 = new Button("Exercicio 3");
		btnConsulta3.setOnAction(e -> {
			exercicio3(comboAeroporto.getValue(), distancia.getText());
		});

		//Quarto Exercicio
		Button btnConsulta4 = new Button("Exercicio 4");
		btnConsulta4.setOnAction(e ->{
			calcularTrafego();
		});
		
		
		Button btnConsulta5 = new Button("Exercicio 5");
		Button btnConsulta6 = new Button("Exercicio 6");

		btnConsulta3.setOnAction(e -> {

		});
		btnConsulta4.setOnAction(e -> {

		});
		btnConsulta5.setOnAction(e -> {

		});
		btnConsulta6.setOnAction(e -> {

		});

		// Monta o GridPane do Exercicio 1
		leftTopPane.add(selecionar, 0, 0);
		leftTopPane.add(btnConsulta1, 0, 1);

		// Monta o GridPane do Exercicio 2
		centerTopPane.add(btnConsulta2, 1, 0);

		// Monta o GridPane do Exercicio 3
		rightTopPane.add(boniteza, 0, 0);
		rightTopPane.add(distancia, 0, 1);
		rightTopPane.add(boniteza2, 0, 2);
		rightTopPane.add(comboAeroporto, 0, 3);
		rightTopPane.add(btnConsulta3, 0, 4);

		// Monta o GridPane do Exercicio 4
		leftBottomPane.add(btnConsulta4, 0, 0);


		// chama o construtor da scene
		constroiScene(leftTopPane, leftBottomPane, rightTopPane, rightBottomPane, centerTopPane, centerBottomPane,
				primaryStage);

	}

	private void setup() {

		// Inicializando os dados aqui...
		gerCias = new GerenciadorCias();
		gerAero = new GerenciadorAeroportos();
		gerRotas = new GerenciadorRotas();
		gerAvioes = new GerenciadorAeronaves();
		gerPais = new GerenciadorPaises();

		try {
			gerAvioes.carregaDados();
			gerPais.carregaDados();
			gerCias.carregaDados();
			gerAero.carregaDados(gerPais);
			gerRotas.carregaDados(gerCias, gerAero, gerAvioes);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private class EventosMouse extends MouseAdapter {
		private int lastButton = -1;

		@Override
		public void mousePressed(MouseEvent e) {
			JXMapViewer mapa = gerenciador.getMapKit().getMainMap();
			GeoPosition loc = mapa.convertPointToGeoPosition(e.getPoint());
			// System.out.println(loc.getLatitude()+", "+loc.getLongitude());
			lastButton = e.getButton();
			// Botão 3: seleciona localização
			if (lastButton == MouseEvent.BUTTON3) {
				gerenciador.setPosicao(loc);
				gerenciador.getMapKit().repaint();
			}
		}
	}

	private void createSwingContent(final SwingNode swingNode) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				swingNode.setContent(gerenciador.getMapKit());
			}
		});
	}

	public static void main(String[] args) {
		launch(args);
	}

	public ObservableList<Pais> ObservableListPaises() {
		return FXCollections.observableList(gerPais.listarTodosArray());

	}
	public ObservableList<Aeroporto> ObservableListAeroportos() {
		return FXCollections.observableList(gerAero.listarTodosArray());

	}


	public void constroiScene(GridPane leftTopPane, GridPane leftBottomPane, GridPane rightTopPane,
			GridPane rightBottomPane, GridPane centerTopPane, GridPane centerBottomPane, Stage primaryStage) {
		GeoPosition poa = new GeoPosition(-30.05, -51.18);

		gerenciador = new GerenciadorMapa(poa, GerenciadorMapa.FonteImagens.VirtualEarth);
		mouse = new EventosMouse();
		gerenciador.getMapKit().getMainMap().addMouseListener(mouse);
		gerenciador.getMapKit().getMainMap().addMouseMotionListener(mouse);
		createSwingContent(mapkit);

		// organiza os gridpanes
		GridPane pane = new GridPane();
		pane.add(leftTopPane, 0, 0);
		pane.add(centerTopPane, 1, 0);
		pane.add(rightTopPane, 2, 0);
		pane.add(leftBottomPane, 0, 1);
		pane.add(centerBottomPane, 1, 1);
		pane.add(rightBottomPane, 2, 1);
		pane.add(mapkit,2,1);

		// cria e inicia a scene
		Scene scene = new Scene(pane, 500, 500);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Mapas com JavaFX");
		primaryStage.show();
	}

	// Exercicios

	private void exercicio1() {
		GeoPosition paisLoc = gerenciador.getPosicao();
		if (paisLoc != null) {
			Pais pais = null;
			// Lista para armazenar o resultado da consulta
			List<MyWaypoint> lstPoints = new ArrayList<>();

			double ds0 = 5;

			double longiMais = Math.ceil(paisLoc.getLongitude() + ds0);
			double longiMenos = Math.ceil(paisLoc.getLongitude() - ds0);

			double latiMais = Math.ceil(paisLoc.getLatitude() + ds0);
			double latiMenos = Math.ceil(paisLoc.getLatitude() - ds0);

			gerenciador.clear();

			for (Aeroporto aeroporto : gerAero.listarTodos().values()) {
				System.out.println(aeroporto.getPais());
				if ((aeroporto.getLocal().getLatitude() == paisLoc.getLatitude()
						|| (aeroporto.getLocal().getLatitude() < latiMais
								&& aeroporto.getLocal().getLatitude() > latiMenos))
						&& (aeroporto.getLocal().getLongitude() == paisLoc.getLongitude()
								|| (aeroporto.getLocal().getLongitude() < longiMais
										&& aeroporto.getLocal().getLongitude() > longiMenos))) {
					pais = aeroporto.getPais();
					break;
				}
			}

			for (Aeroporto aeroporto : gerAero.listarTodos().values()) {
				if (aeroporto.getPais().equals(pais)) {
					// adiciona os pontos
					lstPoints.add(new MyWaypoint(Color.RED, aeroporto.getNome(), aeroporto.getLocal(), 1));
				}
			}
			// Informa o resultado para o gerenciador
			gerenciador.setPontos(lstPoints);
			gerenciador.getMapKit().repaint();

		} else {
			Alert alert = new Alert(Alert.AlertType.WARNING);
			alert.setTitle("Nenhum país selecionado.");
			alert.setHeaderText("Nenhuma região do mapa foi selecionada.");
			alert.setContentText("Por favor, selecione algum lugar no mapa e tente novamente.");

			alert.showAndWait();
			System.out.println("passou aqui");
		}

	}

	private void exercicio2() {
		GeoPosition paisLoc = gerenciador.getPosicao();
		if (paisLoc != null) {
			Pais pais = null;
			// Lista para armazenar o resultado da consulta
			List<MyWaypoint> lstPoints = new ArrayList<>();
			List<Tracado> trcPoints = new ArrayList<>();

			double ds0 = 5;

			double longiMais = Math.ceil(paisLoc.getLongitude() + ds0);
			double longiMenos = Math.ceil(paisLoc.getLongitude() - ds0);

			double latiMais = Math.ceil(paisLoc.getLatitude() + ds0);
			double latiMenos = Math.ceil(paisLoc.getLatitude() - ds0);

			gerenciador.clear();

			for (Aeroporto aeroporto : gerAero.listarTodos().values()) {
				// System.out.println(rota.getOrigem().getPais().getCodigo().equals(pais.getCodigo()));
				// System.out.println(rota.getDestino().getPais().getCodigo().equals(pais.getCodigo()));
				if ((aeroporto.getLocal().getLatitude() == paisLoc.getLatitude()
						|| (aeroporto.getLocal().getLatitude() < latiMais
								&& aeroporto.getLocal().getLatitude() > latiMenos))
						&& (aeroporto.getLocal().getLongitude() == paisLoc.getLongitude()
								|| (aeroporto.getLocal().getLongitude() < longiMais
										&& aeroporto.getLocal().getLongitude() > longiMenos))) {
					pais = aeroporto.getPais();
					break;
				}
			}

			for (Rota rota : gerRotas.listarTodas()) {
				if (rota.getOrigem().getPais().equals(pais) || rota.getDestino().getPais().equals(pais)) {
					System.out.println(rota);

					lstPoints
							.add(new MyWaypoint(Color.RED, rota.getOrigem().getNome(), rota.getOrigem().getLocal(), 1));
					lstPoints.add(
							new MyWaypoint(Color.RED, rota.getDestino().getNome(), rota.getDestino().getLocal(), 1));
					Tracado tracado = new Tracado();
					tracado.addPonto(rota.getOrigem().getLocal());
					tracado.addPonto(rota.getDestino().getLocal());
					tracado.setWidth(1);
					trcPoints.add(tracado);
					gerenciador.addTracado(tracado);

				}
			}
			// Informa o resultado para o gerenciador
			gerenciador.setPontos(lstPoints);
			gerenciador.getMapKit().repaint();

		} else {
			Alert alert = new Alert(Alert.AlertType.WARNING);
			alert.setTitle("Nenhum país selecionado.");
			alert.setHeaderText("Nenhuma região do mapa foi selecionada.");
			alert.setContentText("Por favor, selecione algum lugar no mapa para que as rotas sejam marcadas.");

			alert.showAndWait();

		}

	}

	public void exercicio3(Aeroporto aero, String distancia) {
		List<MyWaypoint> lstPoints = new ArrayList<>();

		gerenciador.clear();

		double distanciaD = Double.parseDouble(distancia);

		Geo aux = new Geo(0, 0);
		Geo aux2 = new Geo(0, 0);

		ArrayList<Rota> rotasDentroDaDistanciaEOAeroportoDeOrigemIgualAoPassadoPorParametro = gerRotas
				.buscarPorOrigem(aero);

		for (Rota r : rotasDentroDaDistanciaEOAeroportoDeOrigemIgualAoPassadoPorParametro) {
			lstPoints.add(new MyWaypoint(Color.RED, r.getOrigem().getNome(), r.getOrigem().getLocal(), 1));
		}

		gerenciador.setPontos(lstPoints);
		gerenciador.getMapKit().repaint();

	}

	public void calcularTrafego() {

		// Map<Integer, Set<Aeroporto>> aux1 = new TreeMap().descendingMap();
		//
		// Set<Aeroporto> aeroportosJaAdicionados = new HashSet<>();
		//
		// for(Rota r: gerRotas.listarTodas()) {
		// Aeroporto origem= r.getOrigem();
		// if(aux1.containsValue(origem)) {
		// aeroportosJaAdicionados.add(origem);
		// aux1.put(aeroportosJaAdicionados.size(), aeroportosJaAdicionados);
		// }
		// }

		HashMap<Integer, Aeroporto> aeroportosComContador = (HashMap<Integer, Aeroporto>) new TreeMap().descendingMap();
		for (Aeroporto aero : gerAero.listarTodos().values()) {
			System.out.println("oi");
			for (Rota r : gerRotas.listarTodas()) {
				int cont = 0;
				if (r.getOrigem().equals(aero) || r.getDestino().equals(aero)) {
					aeroportosComContador.put(cont, aero);
				}
			}
		}

		System.out.println(aeroportosComContador.toString());
	}

}
