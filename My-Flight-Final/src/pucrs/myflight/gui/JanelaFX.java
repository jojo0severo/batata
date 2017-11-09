package pucrs.myflight.gui;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.*;
import java.util.Map.Entry;

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
import pucrs.myflight.modelo.Aeronave;
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
	private GerenciadorTrafego gerTrafego;

	@Override
	public void start(Stage primaryStage) throws Exception {

		setup();

		GridPane buttonsPane = new GridPane();

		buttonsPane.setAlignment(Pos.CENTER);
		buttonsPane.setHgap(50);
		buttonsPane.setVgap(30);
		buttonsPane.setPadding(new Insets(10, 10, 10, 10));

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
		TextInputDialog dialogoInputs = new TextInputDialog();

		TextField distancia = new TextField();
		distancia.setMaxWidth(150);

		Button btnConsulta3 = new Button("Exercicio 3");
		btnConsulta3.setOnAction(a -> {
			exercicio3(comboAeroporto.getValue(), distancia.getText());
			dialogoInputs.close();
		});

		comboAeroporto = new ComboBox<Aeroporto>(ObservableListAeroportos());
		comboAeroporto.setMaxWidth(150);

		Button btnConsulta3AbreDialog = new Button("Exercicio 3");
		btnConsulta3AbreDialog.setOnAction(e -> {
			// Cria um TextInputDialog para melhorar a interacao com usuario
			dialogoInputs.setTitle("Inser��o de Dados");
			dialogoInputs.setHeaderText("Insira as informa��es necess�rias para o exerc�cio 3");

			// Cria um GridPane com os itens que vao no TextInputDialog
			GridPane contents = new GridPane();
			contents.add(distancia, 0, 0);
			contents.add(comboAeroporto, 0, 1);
			contents.add(btnConsulta3, 0, 2);

			// Adiciona o GridPane ao TextInputDialog
			dialogoInputs.getDialogPane().setContent(contents);
			dialogoInputs.showAndWait();
		});

		// Quarto Exercicio
		Button btnConsulta4 = new Button("Exercicio 4");
		btnConsulta4.setOnAction(e -> {
			exercicio4();
		});

		Button btnConsulta5 = new Button("Exercicio 5");
		Button btnConsulta6 = new Button("Exercicio 6");

		// Quinto Exercicio
		btnConsulta5.setOnAction(e -> {

		});
		btnConsulta6.setOnAction(e -> {

		});

		// Monta o GridPane
		buttonsPane.add(selecionar, 0, 0);
		buttonsPane.add(btnConsulta1, 0, 1);
		buttonsPane.add(btnConsulta2, 0, 2);
		buttonsPane.add(btnConsulta3AbreDialog, 0, 3);
		buttonsPane.add(btnConsulta4, 0, 4);

		// chama o construtor da scene
		constroiScene(buttonsPane, primaryStage);

	}

	// -----------INICIA OS PRIVATES E CARREGA OS DADOS----------

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

			gerTrafego = new GerenciadorTrafego(gerRotas, 0);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// -----------METODOS DO PROFESSOR------------

	private class EventosMouse extends MouseAdapter {
		private int lastButton = -1;

		@Override
		public void mousePressed(MouseEvent e) {
			JXMapViewer mapa = gerenciador.getMapKit().getMainMap();
			GeoPosition loc = mapa.convertPointToGeoPosition(e.getPoint());
			lastButton = e.getButton();
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

	// -----------MAIN---------
	public static void main(String[] args) {
		launch(args);
	}

	// -------------CRIA AS LISTAS PARA OS COMBO BOX---------------
	public ObservableList<Pais> ObservableListPaises() {
		return FXCollections.observableList(gerPais.listarTodosArray());

	}

	public ObservableList<Aeroporto> ObservableListAeroportos() {
		return FXCollections.observableList(gerAero.listarTodosArray());

	}

	// ---------------CONSTROI SCENE----------------

	public void constroiScene(GridPane leftTopPane, Stage primaryStage) {
		GeoPosition poa = new GeoPosition(-30.05, -51.18);

		gerenciador = new GerenciadorMapa(poa, GerenciadorMapa.FonteImagens.VirtualEarth);
		mouse = new EventosMouse();
		gerenciador.getMapKit().getMainMap().addMouseListener(mouse);
		gerenciador.getMapKit().getMainMap().addMouseMotionListener(mouse);
		createSwingContent(mapkit);

		// organiza os gridpanes
		BorderPane pane = new BorderPane();
		pane.setLeft(leftTopPane);
		pane.setCenter(mapkit);

		// cria e inicia a scene
		Scene scene = new Scene(pane, 500, 500);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Mapas com JavaFX");
		primaryStage.show();
	}

	// -------------EXERCICIOS-------------

	private void exercicio1() {
		GeoPosition paisLoc = gerenciador.getPosicao();
		if (paisLoc != null) {
			Pais pais = null;

			List<MyWaypoint> lstPoints = new ArrayList<>();

			double ds0 = 5;

			double longiMais = Math.ceil(paisLoc.getLongitude() + ds0);
			double longiMenos = Math.ceil(paisLoc.getLongitude() - ds0);

			double latiMais = Math.ceil(paisLoc.getLatitude() + ds0);
			double latiMenos = Math.ceil(paisLoc.getLatitude() - ds0);

			gerenciador.clear();

			for (String aeroporto : gerAero.listarTodos().keySet()) {
				if ((gerAero.listarTodos().get(aeroporto).getLocal().getLatitude() == paisLoc.getLatitude()
						|| (gerAero.listarTodos().get(aeroporto).getLocal().getLatitude() < latiMais
								&& gerAero.listarTodos().get(aeroporto).getLocal().getLatitude() > latiMenos))
						&& (gerAero.listarTodos().get(aeroporto).getLocal().getLongitude() == paisLoc.getLongitude()
								|| (gerAero.listarTodos().get(aeroporto).getLocal().getLongitude() < longiMais
										&& gerAero.listarTodos().get(aeroporto).getLocal()
												.getLongitude() > longiMenos))) {
					pais = gerAero.listarTodos().get(aeroporto).getPais();
					break;
				}
			}

			for (String aeroporto : gerAero.listarTodos().keySet()) {
				if (gerAero.listarTodos().get(aeroporto).getPais().equals(pais)) {
					lstPoints.add(new MyWaypoint(Color.RED, gerAero.listarTodos().get(aeroporto).getNome(),
							gerAero.listarTodos().get(aeroporto).getLocal(), 1));
				}
			}

			gerenciador.setPontos(lstPoints);
			gerenciador.getMapKit().repaint();

		} else {
			Alert alert = new Alert(Alert.AlertType.WARNING);
			alert.setTitle("Nenhum país selecionado.");
			alert.setHeaderText("Nenhuma região do mapa foi selecionada.");
			alert.setContentText("Por favor, selecione algum lugar no mapa e tente novamente.");
			alert.showAndWait();
		}

	}

	// ================================================================================

	private void exercicio2() {
		GeoPosition paisLoc = gerenciador.getPosicao();
		if (paisLoc != null) {
			Pais pais = null;

			List<MyWaypoint> lstPoints = new ArrayList<>();

			double ds0 = 5;

			double longiMais = Math.ceil(paisLoc.getLongitude() + ds0);
			double longiMenos = Math.ceil(paisLoc.getLongitude() - ds0);

			double latiMais = Math.ceil(paisLoc.getLatitude() + ds0);
			double latiMenos = Math.ceil(paisLoc.getLatitude() - ds0);

			gerenciador.clear();

			for (String aeroporto : gerAero.listarTodos().keySet()) {
				if ((gerAero.listarTodos().get(aeroporto).getLocal().getLatitude() == paisLoc.getLatitude()
						|| (gerAero.listarTodos().get(aeroporto).getLocal().getLatitude() < latiMais
								&& gerAero.listarTodos().get(aeroporto).getLocal().getLatitude() > latiMenos))
						&& (gerAero.listarTodos().get(aeroporto).getLocal().getLongitude() == paisLoc.getLongitude()
								|| (gerAero.listarTodos().get(aeroporto).getLocal().getLongitude() < longiMais
										&& gerAero.listarTodos().get(aeroporto).getLocal()
												.getLongitude() > longiMenos))) {
					pais = gerAero.listarTodos().get(aeroporto).getPais();
					break;
				}
			}
			for (Aeroporto aero : gerRotas.getHashMap().keySet()) {
				if (aero.getPais().equals(pais) || aero.getPais().equals(pais)) {
					for (Rota rota : gerRotas.getHashMap().get(aero)) {
						lstPoints.add(
								new MyWaypoint(Color.RED, rota.getOrigem().getNome(), rota.getOrigem().getLocal(), 1));
						lstPoints.add(new MyWaypoint(Color.RED, rota.getDestino().getNome(),
								rota.getDestino().getLocal(), 1));
						Tracado tracado = new Tracado();
						tracado.addPonto(rota.getOrigem().getLocal());
						tracado.addPonto(rota.getDestino().getLocal());
						tracado.setWidth(1);
						gerenciador.addTracado(tracado);
					}
				}
			}

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

	// ================================================================================

	public void exercicio3(Aeroporto aero, String distancia) {
		List<MyWaypoint> lstPoints = new ArrayList<>();

		gerenciador.clear();

		double distanciaD = Double.parseDouble(distancia);

		Geo aux = new Geo(0, 0);
		Geo aux2 = new Geo(0, 0);

		ArrayList<Rota> rotas = gerRotas.buscarPorOrigem(aero);

		for (Rota r : rotas) {
			aux = r.getOrigem().getLocal();
			aux2 = r.getDestino().getLocal();
			if (aux.distancia(aux2) < distanciaD) {
				lstPoints.add(new MyWaypoint(Color.RED, r.getOrigem().getNome(), r.getOrigem().getLocal(), 1));

				Tracado tracado = new Tracado();
				tracado.addPonto(r.getOrigem().getLocal());
				tracado.addPonto(r.getDestino().getLocal());
				tracado.setWidth(1);
				gerenciador.addTracado(tracado);
			}
		}

		gerenciador.setPontos(lstPoints);
		gerenciador.getMapKit().repaint();

	}

	// ================================================================================

	public void exercicio4() {
		List<MyWaypoint> lstPoints = new ArrayList<>();

		gerenciador.clear();

		gerTrafego.setPosicoes(10);
		gerTrafego.carregaTamanhos();
		
		int count = 0;

		for (Aeroporto aero : gerTrafego.getAeroportos()) {
			lstPoints.add(new MyWaypoint(Color.RED, aero.getNome(), aero.getLocal(), count = +10));
		}

		gerenciador.setPontos(lstPoints);
		gerenciador.getMapKit().repaint();
	}

	// ================================================================================

	public void exercicio5(Aeronave aero) {
		HashMap<Aeronave, Rota> rotas = new HashMap<>();

		for (Rota rota : gerRotas.listarTodas()) {
			if (rota.getAeronave().equals(aero)) {
				rotas.put(aero, rota);
			}
		}

		for (Aeronave aeronave : rotas.keySet()) {
			Tracado tracado = new Tracado();
			tracado.addPonto(rotas.get(aeronave).getOrigem().getLocal());
			tracado.addPonto(rotas.get(aeronave).getDestino().getLocal());
			tracado.setWidth(1);
			gerenciador.addTracado(tracado);
		}
	}
}
