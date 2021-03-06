package pucrs.myflight.gui;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.*;
import javax.swing.SwingUtilities;

import javafx.scene.control.*;
import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.viewer.GeoPosition;
import javafx.application.Application;
import javafx.collections.FXCollections;
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
//import pucrs.myflight.modelo.CiaAerea;
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
	private GerenciadorTrafego gerTrafego1;
	private GerenciadorTrafego gerTrafego;
	private GerenciadorPaises gerPais;
	private ArrayList<Color> cores;
	private ArrayList<Aeroporto> aeroportosMundial;
	private ArrayList<Aeroporto> aeros;
	private ArrayList<Rota> rotas = new ArrayList<>();
	private int cont = 0;

	@Override
	public void start(Stage primaryStage) throws Exception {

		setup();

		GridPane buttonsPane = new GridPane();

		buttonsPane.setAlignment(Pos.CENTER);
		buttonsPane.setHgap(50);
		buttonsPane.setVgap(30);
		buttonsPane.setPadding(new Insets(10, 10, 10, 10));

		/*
		 * PRIMEIRO EXERCICIO
		 */

		//Text selecionar = new Text("Selecione o pais");
		Button btnConsulta1 = new Button("Exercicio 1");
		btnConsulta1.setOnAction(e -> {
			exercicio1();
		});

		/*
		 * SEGUNDO EXERCICIO
		 */

		Button btnConsulta2 = new Button("Exercicio 2");
		btnConsulta2.setOnAction(e -> {
			exercicio2();
		});

		/*
		 * TERCEIRO EXERCICIO
		 */

		TextInputDialog dialogoInputs = new TextInputDialog();
		Text distanciaText = new Text("Distancia para as Rotas");
		TextField distancia = new TextField();
		distancia.setMaxWidth(150);
		Text codAeropText = new Text("Codigo do Aeroporto");
		TextField codAerop = new TextField();
		Button btnConsulta3 = new Button("Exercicio 3");

		btnConsulta3.setOnAction(a -> {
			exercicio3(gerAero.buscarPorCodigo(codAerop.getText().toString()), distancia.getText());
			dialogoInputs.close();
		});

		Button btnConsulta3AbreDialog = new Button("Exercicio 3");
		btnConsulta3AbreDialog.setOnAction(e -> {
			dialogoInputs.setTitle("Insercao de Dados");
			dialogoInputs.setHeaderText("Insira as informacoes necessarias para o exercicio 3");
			dialogoInputs.getDialogPane().getChildren().get(1).setVisible(false);
			dialogoInputs.getDialogPane().getChildren().get(2).setVisible(false);

			GridPane contents = new GridPane();
			contents.setVgap(15);
			contents.setHgap(15);
			contents.add(distanciaText, 0, 0);
			contents.add(distancia, 0, 1);
			contents.add(codAeropText, 0, 2);
			contents.add(codAerop, 0, 3);
			contents.add(btnConsulta3, 1, 4);

			dialogoInputs.getDialogPane().setContent(contents);
			dialogoInputs.showAndWait();
		});

		/*
		 * QUARTO EXERCICIO
		 */

		TextField tamanho = new TextField();
		TextField codPais = new TextField();
		TextField tamanho1 = new TextField();

		Button pais = new Button("Pais");
		Button paisConsulta = new Button("Consultar");
		Button mundo = new Button("Mundo");
		pais.setOnAction(e -> {
			GridPane contents = new GridPane();
			contents.setVgap(15);
			contents.setHgap(15);
			contents.add(pais, 0, 0);
			contents.add(codPais, 1, 0);
			contents.add(paisConsulta, 2, 1);

			dialogoInputs.setTitle("Escolha Pais");
			dialogoInputs.setHeaderText("Por favor, informe o codigo do pais");
			dialogoInputs.getDialogPane().setContent(contents);

		});
		paisConsulta.setOnAction(e -> {

			String codigoPais = codPais.getText();
			exercicio4Pais(codigoPais);

		});
		mundo.setOnAction(e -> {
			exercicio4Mundo();
		});

		Button btnConsulta4AbreInputs = new Button("Exercicio 4");
		btnConsulta4AbreInputs.setOnAction(e -> {
			dialogoInputs.setTitle("Insercao de Dados");
			dialogoInputs.setHeaderText("Insira as informacoes necessarias para o exercicio 4");
			dialogoInputs.getDialogPane().getChildren().get(1).setVisible(false);
			dialogoInputs.getDialogPane().getChildren().get(2).setVisible(false);

			GridPane contents = new GridPane();
			contents.add(pais, 0, 1);
			contents.add(mundo, 0, 2);
			contents.setVgap(15);
			contents.setHgap(15);
			dialogoInputs.getDialogPane().setContent(contents);
			dialogoInputs.showAndWait();
		});

		/*
		 * QUINTO EXERCICIO
		 */

		Text codAeronaveText = new Text("Codigo da Aeronave");
		TextField codAeronave = new TextField();
		Button btnConsulta5 = new Button("Exercicio 5");
		btnConsulta5.setOnAction(e -> {
			exercicio5(gerAvioes.buscarporCodigo(codAeronave.getText().toString()));
		});

		Button btnConsulta5AbreInputs = new Button("Exercicio 5");
		btnConsulta5AbreInputs.setOnAction(e -> {
			dialogoInputs.setTitle("Insercao de Dados");
			dialogoInputs.setHeaderText("Insira as informacoes necessarias para o exercicio 3");
			dialogoInputs.getDialogPane().getChildren().get(1).setVisible(false);
			dialogoInputs.getDialogPane().getChildren().get(2).setVisible(false);

			GridPane contents = new GridPane();
			contents.setVgap(15);
			contents.setHgap(15);
			contents.add(codAeronaveText, 0, 0);
			contents.add(codAeronave, 0, 1);
			contents.add(btnConsulta5, 3, 4);

			dialogoInputs.getDialogPane().setContent(contents);
			dialogoInputs.showAndWait();

		});

		/*
		 * SEXTO EXERCICIO
		 */

		TextField origem = new TextField();
		TextField destino = new TextField();

		Button btnConsulta6 = new Button("Exercicio 6");
		btnConsulta6.setOnAction(e -> {
			exercicio6(origem.getText(), destino.getText());
		});

		Button btnConsulta6AbreInputs = new Button("Exercicio 6");
		btnConsulta6AbreInputs.setOnAction(e -> {
			dialogoInputs.setTitle("Insercao de Dados");
			dialogoInputs.setHeaderText("Insira as informacoes necessarias para o exercicio 3");
			dialogoInputs.getDialogPane().getChildren().get(1).setVisible(false);
			dialogoInputs.getDialogPane().getChildren().get(2).setVisible(false);

			GridPane contents = new GridPane();
			contents.setVgap(15);
			contents.setHgap(15);
			contents.add(origem, 0, 0);
			contents.add(destino, 0, 1);
			contents.add(btnConsulta6, 3, 3);

			dialogoInputs.getDialogPane().setContent(contents);
			dialogoInputs.showAndWait();
		});

		// Monta o GridPane
		//buttonsPane.add(selecionar, 0, 0);
		buttonsPane.add(btnConsulta1, 0, 1);
		buttonsPane.add(btnConsulta2, 0, 2);
		buttonsPane.add(btnConsulta3AbreDialog, 0, 3);
		buttonsPane.add(btnConsulta4AbreInputs, 0, 4);
		buttonsPane.add(btnConsulta5AbreInputs, 0, 5);
		buttonsPane.add(btnConsulta6AbreInputs, 0, 6);

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
		gerTrafego1 = new GerenciadorTrafego(gerRotas, 10);
		gerTrafego = new GerenciadorTrafego(gerRotas, 10);
		cores = new ArrayList<>();

		try {
			gerAvioes.carregaDados();
			gerPais.carregaDados();
			gerCias.carregaDados();
			gerAero.carregaDados(gerPais);
			gerRotas.carregaDados(gerCias, gerAero, gerAvioes);
			gerRotas.carregaTamanhos();
			aeros = gerTrafego1.maiores();
			comboAeroData = ObservableListAeronaves();
			comboAeropData = ObservableListAeroportos();
			// ====maior====
			cores.add(Color.white);
			cores.add(Color.RED);
			cores.add(Color.ORANGE);
			cores.add(Color.YELLOW);
			cores.add(Color.pink);
			cores.add(Color.GREEN);
			cores.add(Color.cyan);
			cores.add(Color.blue);
			cores.add(Color.magenta);
			cores.add(Color.black);
			// ====menor====
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

	public void constroiScene(GridPane buttonsPane, Stage primaryStage) {
		GeoPosition poa = new GeoPosition(-30.05, -51.18);

		gerenciador = new GerenciadorMapa(poa, GerenciadorMapa.FonteImagens.VirtualEarth);
		mouse = new EventosMouse();
		gerenciador.getMapKit().getMainMap().addMouseListener(mouse);
		gerenciador.getMapKit().getMainMap().addMouseMotionListener(mouse);
		createSwingContent(mapkit);

		BorderPane pane = new BorderPane();
		pane.setLeft(buttonsPane);
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
				Aeroporto aux = gerAero.listarTodos().get(aeroporto);
				if ((aux.getLocal().getLatitude() == paisLoc.getLatitude()
						|| (aux.getLocal().getLatitude() < latiMais && aux.getLocal().getLatitude() > latiMenos))
						&& (aux.getLocal().getLongitude() == paisLoc.getLongitude()
								|| (aux.getLocal().getLongitude() < longiMais
										&& aux.getLocal().getLongitude() > longiMenos))) {
					pais = aux.getPais();
					break;
				}
			}
			if (pais == null) {
				Alert alert = new Alert(Alert.AlertType.WARNING);
				alert.setTitle("Nenhum pais encontrado.");
				alert.setHeaderText("Nenhuma regiao valida do mapa foi selecionada.");
				alert.setContentText("Por favor, selecione algum lugar valido no mapa e tente novamente.");
				alert.showAndWait();
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				alert.close();
			} else {
				for (String aeroporto : gerAero.listarTodos().keySet()) {

					Aeroporto aux = gerAero.listarTodos().get(aeroporto);
					if (aux.getPais().equals(pais)) {
						lstPoints.add(new MyWaypoint(Color.RED, aux.getNome(), aux.getLocal(), 1));
					}
				}

				gerenciador.setPontos(lstPoints);
				gerenciador.getMapKit().repaint();
			}

		} else {
			Alert alert = new Alert(Alert.AlertType.WARNING);
			alert.setTitle("Nenhum pais selecionado.");
			alert.setHeaderText("Nenhuma regiao do mapa foi selecionada.");
			alert.setContentText("Por favor, selecione algum lugar no mapa e tente novamente.");
			alert.showAndWait();
			try {
				Thread.sleep(1000);
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
				Aeroporto aux = gerAero.listarTodos().get(aeroporto);
				if ((aux.getLocal().getLatitude() == paisLoc.getLatitude()
						|| (aux.getLocal().getLatitude() < latiMais && aux.getLocal().getLatitude() > latiMenos))
						&& (aux.getLocal().getLongitude() == paisLoc.getLongitude()
								|| (aux.getLocal().getLongitude() < longiMais
										&& aux.getLocal().getLongitude() > longiMenos))) {
					pais = aux.getPais();
					break;
				}
			}

			if (pais == null) {
				Alert alert = new Alert(Alert.AlertType.WARNING);
				alert.setTitle("Nenhum pais selecionado.");
				alert.setHeaderText("Nenhuma regiao valida do mapa foi selecionada.");
				alert.setContentText("Por favor, selecione algum lugar valido no mapa e tente novamente.");
				alert.showAndWait();
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				alert.close();
			}

			else {
				for (Aeroporto aero : gerRotas.getHashMap().keySet()) {
					if (aero.getPais().equals(pais) || aero.getPais().equals(pais)) {
						for (Rota rota : gerRotas.getHashMap().get(aero)) {
							lstPoints.add(new MyWaypoint(Color.RED, rota.getOrigem().getNome(),
									rota.getOrigem().getLocal(), 1));
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
			}

		} else {
			Alert alert = new Alert(Alert.AlertType.WARNING);
			alert.setTitle("Nenhum pais selecionado.");
			alert.setHeaderText("Nenhuma regiao do mapa foi selecionada.");
			alert.setContentText("Por favor, selecione algum lugar no mapa para que as rotas sejam marcadas.");
			alert.showAndWait();
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			alert.close();

		}

	}

	// ================================================================================

	public void exercicio3(Aeroporto aero, String distancia) {
		List<MyWaypoint> lstPoints = new ArrayList<>();

		if (aero == null || distancia.length() == 0) {
			Alert alert = new Alert(Alert.AlertType.WARNING);
			alert.setTitle("Nenhum aeroporto ou distancia.");
			alert.setHeaderText("Nenhuma aeroporto ou distancia foi informado(a) ou encontrado.");
			alert.setContentText("Por favor, digite um aeroporto ou distancia valido(a) para a pesquisa.");
			alert.showAndWait();
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			alert.close();
		} else {
			gerenciador.clear();

			double distanciaD = Double.parseDouble(distancia);

			Geo aux = new Geo(0, 0);
			Geo aux2 = new Geo(0, 0);

			ArrayList<Rota> rotas = gerRotas.buscarPorOrigem(aero);

			for (Rota r : rotas) {
				aux = r.getOrigem().getLocal();
				aux2 = r.getDestino().getLocal();
				if (aux.distancia(aux2) < distanciaD) {
					lstPoints.add(new MyWaypoint(Color.RED, r.getOrigem().getNome(), r.getOrigem().getLocal(), 10));
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

	}

	// ================================================================================

	public void exercicio4Pais(String codPais) {
		Pais pais1;
		pais1 = gerPais.buscarPorCodigo(codPais);
		GerenciadorTrafego gerTrafego1 = new GerenciadorTrafego(gerRotas, 10);
		List<MyWaypoint> lstPoints = new ArrayList<>();

		if (pais1 == null) {
			Alert alert = new Alert(Alert.AlertType.WARNING);
			alert.setTitle("Pais nao encontrado.");
			alert.setHeaderText("Nenhuma regiao do mapa foi informada.");
			alert.setContentText("Por favor, informe um pais valido para que as rotas sejam marcadas.");
			alert.showAndWait();
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			alert.close();
		} else {
			gerenciador.clear();

			ArrayList<Aeroporto> aux = gerTrafego1.aeroportosDeUmPais(gerenciador, gerAero, pais1);

			gerTrafego1.carregaTamanhosArray(aux);
			gerTrafego1.carregaValores();

			int count = 5;
			int tamanho = gerTrafego1.getAeroportos().size() - 1;
			int tamanho1 = gerTrafego1.getAeroportos1().length - 1;
			for (int i = 0; i < gerTrafego1.getAeroportos().size(); i++) {
				lstPoints.add(new MyWaypoint(cores.get(tamanho - i), gerTrafego1.getAeroportos().get(i).getNome(),
						gerTrafego1.getAeroportos().get(tamanho - i).getLocal(), count));
				count = count + 4;
			}

			gerenciador.setPontos(lstPoints);
			gerenciador.getMapKit().repaint();
		}
	}

	// ================================================================================

	public void exercicio4Mundo() {
		List<MyWaypoint> lstPoints = new ArrayList<>();

		gerenciador.clear();

		int count = 150;

		for (int i = 0; i < gerTrafego1.getTamanhoVetor(); i++) {
			lstPoints.add(new MyWaypoint(cores.get(i), aeros.get(i).getNome(), aeros.get(i).getLocal(), count));
			count = count - 15;
		}

		gerenciador.setPontos(lstPoints);
		gerenciador.getMapKit().repaint();
	}

	// ================================================================================

	public void exercicio5(Aeronave aero) {

		if (aero == null) {
			Alert alert = new Alert(Alert.AlertType.WARNING);
			alert.setTitle("Aeronave nao encontrada.");
			alert.setHeaderText("Nenhuma aeronave encontrada ou informada.");
			alert.setContentText("Por favor, informe uma aeronave valida para que as rotas sejam marcadas.");
			alert.showAndWait();
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			alert.close();
		} else {
			HashMap<Rota, Aeroporto[]> rotas = new HashMap<>();

			gerenciador.clear();
			gerenciador.setPontos(new ArrayList<>());

			for (Rota rota : gerRotas.listarTodas()) {
				Aeroporto[] aeroDesOrig = new Aeroporto[2];

				if (rota.getAeronave() == aero) {
					aeroDesOrig[0] = rota.getOrigem();
					aeroDesOrig[1] = rota.getDestino();
					rotas.put(rota, aeroDesOrig);
				}
			}

			for (Rota rota : rotas.keySet()) {
				Tracado tracado = new Tracado();
				tracado.addPonto(rotas.get(rota)[0].getLocal());
				tracado.addPonto(rotas.get(rota)[1].getLocal());
				tracado.setWidth(1);
				gerenciador.addTracado(tracado);
			}
			gerenciador.getMapKit().repaint();
		}
	}

	// ================================================================================

	public void exercicio6(String SinicialAerop, String SfinalAerop) {
		ArrayList<Rota> rotasLocal = new ArrayList<>();
		Aeroporto inicialAerop = gerAero.buscarPorCodigo(SinicialAerop);
		Aeroporto finalAerop = gerAero.buscarPorCodigo(SfinalAerop);

		gerenciador.clear();

		for (Rota rota : gerRotas.buscarPorOrigem(inicialAerop)) {
			if (rota.getDestino().equals(finalAerop)) {
				Tracado tracado = new Tracado();
				tracado.addPonto(rota.getOrigem().getLocal());
				tracado.addPonto(rota.getDestino().getLocal());
				tracado.setWidth(1);
				gerenciador.addTracado(tracado);
			}
		}

		if (gerenciador.totalTracados() == 0) {
			Aeroporto destino = gerRotas.buscarUmaOrigem(inicialAerop).getDestino();
			Rota atual = gerRotas.buscarUmaOrigem(inicialAerop);
			rotasLocal = achaDestinoMultiplo(atual, destino);
		}
		if (rotasLocal.isEmpty() || rotasLocal == null) {
			Alert alert = new Alert(Alert.AlertType.WARNING);
			alert.setTitle("Rota inexistente");
			alert.setHeaderText("Locais Sem Rota");
			alert.setContentText("Nao existe rota para esses locais");
			alert.showAndWait();
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			alert.close();
		} else {
			for (Rota r : rotasLocal) {
				Tracado tracado = new Tracado();
				tracado.addPonto(r.getOrigem().getLocal());
				tracado.addPonto(r.getDestino().getLocal());
				tracado.setWidth(1);
				gerenciador.addTracado(tracado);
			}
		}

		gerenciador.getMapKit().repaint();

		// for (Rota r : gerRotas.listarTodas()) {
		// if (r.getOrigem().equals(inicialAerop)) {
		// if (r.getDestino().equals(finalAerop)) {
		// rotas.add(rota);
		// Tracado tracado = new Tracado();
		// tracado.addPonto(rota.getOrigem().getLocal());
		// tracado.addPonto(rota.getDestino().getLocal());
		// tracado.setWidth(1);
		// gerenciador.addTracado(tracado);
		// break;
		// } else {
		// Rota achou = achaDestino(finalAerop);
		// if (achaDestino(finalAerop) == null) {
		// Alert alert = new Alert(Alert.AlertType.WARNING);
		// alert.setTitle("Rota inexistente");
		// alert.setHeaderText("Locais Sem Rota");
		// alert.setContentText("Nao existe rota para esses locais");
		// alert.showAndWait();
		//
		// try {
		// Thread.sleep(2000);
		// } catch (InterruptedException e) {
		// e.printStackTrace();
		// }
		// alert.close();
		// } else {
		// rotas.add(achou);
		// Tracado tracado = new Tracado();
		// tracado.addPonto(r.getOrigem().getLocal());
		// tracado.addPonto(r.getDestino().getLocal());
		// tracado.setWidth(1);
		// gerenciador.addTracado(tracado);
		// }
		// }
		// }
		// }
	}

	public Rota achaDestinoDiretoDoido(Aeroporto aeropInicial, Aeroporto aeropFinal) {
		for (Aeroporto aero : gerRotas.getHashMap().keySet()) {
			if (aero.getPassouAqui() == false) {
				for (Rota rota : gerRotas.getHashMap().get(aero)) {
					if (rota.getDestino().equals(aeropFinal) && rota.getOrigem().equals(aeropInicial)) {
						return rota;
					}
				}
				aero.setPassouAqui(true);
			}
		}
		return null;
	}

	public ArrayList<Rota> achaDestinoMultiplo(Rota atual, Aeroporto destino) {
		cont++;
		if (destino.getPassouAqui() == false) {
			destino.setPassouAqui(true);
			if (gerRotas.buscarUmaOrigem(destino).getDestino().equals(destino)) {
				rotas.add(atual);
				return rotas;
			}
			rotas.add(atual);
		} else {
			atual = gerRotas.buscarUmaOrigem(gerRotas.buscarUmaOrigem(destino).getDestino());
			destino = gerRotas.buscarUmaOrigem(destino).getDestino();
			achaDestinoMultiplo(atual, destino);
		}
		if (cont == gerRotas.listarTodas().size()) {
			// Nao possui rota(s) entre os destinos;
			return null;
		}
		return null;
	}

}
