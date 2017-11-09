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
	private ObservableList<Aeroporto> comboAeropData;
	private ObservableList<Aeronave> comboAeroData;
	private ComboBox<Aeroporto> comboAeroporto;
	private ComboBox<Aeronave> comboAero;
	private ComboBox<Aeroporto> comboAeroporto6Orig;
	private ComboBox<Aeroporto> comboAeroporto6Dest;
	private GerenciadorPaises gerPais;
	private GerenciadorTrafego gerTrafego;
	private int cont = 0;

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

		comboAeroporto = new ComboBox<Aeroporto>();
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
		comboAero = new ComboBox<Aeronave>(comboAeroData);
		comboAero.setMaxWidth(150);
		btnConsulta5.setOnAction(e -> {
			Aeronave a = comboAero.getValue();
			System.out.println(a);

			exercicio5(a);
		});
		comboAeroporto6Orig = new ComboBox<Aeroporto>(comboAeropData);
		comboAeroporto6Dest = new ComboBox<Aeroporto>(comboAeropData);
		btnConsulta6.setOnAction(e -> {
			exercicio6(comboAeroporto6Orig.getValue(), comboAeroporto6Dest.getValue());
		});

		// Monta o GridPane
		buttonsPane.add(selecionar, 0, 0);
		buttonsPane.add(btnConsulta1, 0, 1);
		buttonsPane.add(btnConsulta2, 0, 2);
		buttonsPane.add(btnConsulta3AbreDialog, 0, 3);
		buttonsPane.add(btnConsulta4, 0, 4);
		buttonsPane.add(comboAero, 0, 5);
		buttonsPane.add(btnConsulta5, 0, 6);

		// Chama o construtor da scene
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
			comboAeroData = ObservableListAeronaves();
			comboAeropData = ObservableListAeroportos();
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

	public ObservableList<Aeronave> ObservableListAeronaves() {
		return FXCollections.observableList(gerAvioes.listarTodosArray());
	}

	// ---------------CONSTROI SCENE----------------

	public void constroiScene(GridPane leftTopPane, Stage primaryStage) {
		GeoPosition poa = new GeoPosition(-30.05, -51.18);

		gerenciador = new GerenciadorMapa(poa, GerenciadorMapa.FonteImagens.VirtualEarth);
		mouse = new EventosMouse();
		gerenciador.getMapKit().getMainMap().addMouseListener(mouse);
		gerenciador.getMapKit().getMainMap().addMouseMotionListener(mouse);
		createSwingContent(mapkit);

		// Organiza os gridpanes
		BorderPane pane = new BorderPane();
		pane.setLeft(leftTopPane);
		pane.setCenter(mapkit);

		// Cria e inicia a scene
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
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			alert.close();
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
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			alert.close();

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

		gerTrafego.setTamanhoVetor(10);
		gerTrafego.carregaTamanhos();

		int count = 5;

		for (int i=0;i< gerTrafego.getAeroportos().length;i++) {
			lstPoints.add(new MyWaypoint(Color.RED, gerTrafego.getAeroportos()[ gerTrafego.getAeroportos().length-i-1].getNome(), gerTrafego.getAeroportos()[gerTrafego.getAeroportos().length-i-1].getLocal(), count));
			count= count +15;
		}

		gerenciador.setPontos(lstPoints);
		gerenciador.getMapKit().repaint();
	}

	// ================================================================================

	public void exercicio5(Aeronave aero) {
		HashMap<Rota, Aeroporto[]> rotas = new HashMap<>();
		Aeronave a = aero;
		gerenciador.clear();
		for (Rota rota : gerRotas.listarTodas()) {
			Aeroporto[] aeroDesOrig = new Aeroporto[2];
			if (rota.getAeronave() == aero) {
				aeroDesOrig[0] = rota.getOrigem();
				aeroDesOrig[1] = rota.getDestino();
				rotas.put(rota, aeroDesOrig);
			}
			if(aeroDesOrig[1]!=null) {
				System.out.println(Arrays.toString(aeroDesOrig));
			}
		}

		for (Rota rota : rotas.keySet()) {
			Tracado tracado = new Tracado();
			tracado.addPonto(rotas.get(rota)[0].getLocal());
			tracado.addPonto(rotas.get(rota)[1].getLocal());
			tracado.setWidth(2);
			gerenciador.addTracado(tracado);
		}
		gerenciador.getMapKit().repaint();
	}

	// ================================================================================

	public void exercicio6(Aeroporto inicialAerop, Aeroporto finalAerop) {
		ArrayList<Rota> rotas = new ArrayList<>();
		for (Rota rota : gerRotas.listarTodas()) {
			if (rota.getOrigem().equals(inicialAerop)) {
				for (Rota r : gerRotas.listarTodas()) {
					if (r.getDestino().equals(finalAerop)) {
						rotas.add(r);
						Tracado tracado = new Tracado();
						tracado.addPonto(r.getOrigem().getLocal());
						tracado.addPonto(r.getDestino().getLocal());
						tracado.setWidth(1);
						gerenciador.addTracado(tracado);
					} else {
						if (achaDestino(r, finalAerop) == null) {
							Alert alert = new Alert(Alert.AlertType.WARNING);
							alert.setTitle("Rota inexistente");
							alert.setHeaderText("Locais Sem Rota");
							alert.setContentText("Nao existe rota para esses locais");
							alert.showAndWait();

							try {
								Thread.sleep(2000);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
							alert.close();
						} else {
							rotas.add(achaDestino(r, finalAerop));
							Tracado tracado = new Tracado();
							tracado.addPonto(r.getOrigem().getLocal());
							tracado.addPonto(r.getDestino().getLocal());
							tracado.setWidth(1);
							gerenciador.addTracado(tracado);
						}
					}
				}
				break;
			}
		}
	}

	public Rota achaDestino(Rota r, Aeroporto aerop) {
		cont = 0;
		for (int i = 0; i < gerRotas.buscarPorOrigem(r.getDestino()).size(); i++) {
			if (gerRotas.buscarPorOrigem(r.getDestino()).get(i).getDestino().equals(aerop)) {
				return r;
			}
			cont++;
		}

		achaDestino(gerRotas.buscarUmaOrigem(r.getDestino()), aerop);
		if (cont == gerRotas.listarTodas().size()) {
			return null;
		}
		return null;
	}
}
