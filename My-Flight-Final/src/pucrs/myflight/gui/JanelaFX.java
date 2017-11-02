package pucrs.myflight.gui;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.SwingUtilities;
import javax.swing.plaf.synth.SynthSeparatorUI;

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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
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
	private GerenciadorPaises gerPais;

	@Override
	public void start(Stage primaryStage) throws Exception {

		setup();

		/*//GeoPosition poa = new GeoPosition(-30.05, -51.18);
		//gerenciador = new GerenciadorMapa(poa, GerenciadorMapa.FonteImagens.VirtualEarth);
		//mouse = new EventosMouse();
		//gerenciador.getMapKit().getMainMap().addMouseListener(mouse);
		//gerenciador.getMapKit().getMainMap().addMouseMotionListener(mouse);

		//createSwingContent(mapkit);*/

		/*//BorderPane pane = new BorderPane();*/
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

		
		Button btnConsulta1 = new Button("Exercicio 1");
		
		Button btnConsulta2 = new Button("Exercicio 2");
		Button btnConsulta3 = new Button("Exercicio 3");
		Button btnConsulta4 = new Button("Exercicio 4");
		Button btnConsulta5 = new Button("Exercicio 5");
		Button btnConsulta6 = new Button("Exercicio 6");
	
		
		
		btnConsulta1.setOnAction(e -> {

		});
		btnConsulta2.setOnAction(e -> {

		});
		btnConsulta3.setOnAction(e -> {

		});
		btnConsulta4.setOnAction(e -> {

		});
		btnConsulta5.setOnAction(e -> {

		});
		btnConsulta6.setOnAction(e -> {

		});
		
		/*//pane.setCenter(mapkit);
		//pane.setRight(rightBottomPane);		
		//Scene scene = new Scene(pane, 500, 500);
		//primaryStage.setScene(scene);
		//primaryStage.setTitle("Mapas com JavaFX");
		//primaryStage.show();*/

	}

	private void consulta(String origem, String destino) {
		
		// Lista para armazenar o resultado da consulta
				List<MyWaypoint> lstPoints = new ArrayList<>();
				
				//Pega os aeroportos
				Aeroporto aeroporigem = gerAero.buscarPorNome(origem);
				Aeroporto aeropdestino = gerAero.buscarPorNome(destino);
				System.out.println(aeroporigem);
				System.out.println(aeropdestino);
				
				
				gerenciador.clear();
				Tracado tr = new Tracado();
				tr.setLabel("Teste");
				tr.setWidth(5);
				tr.setCor(new Color(0,0,0,60));
				tr.addPonto(aeroporigem.getLocal());
				tr.addPonto(aeropdestino.getLocal());

				gerenciador.addTracado(tr);
				
				// Adiciona os locais de cada aeroporto (sem repetir) na lista de
				// waypoints
				
				lstPoints.add(new MyWaypoint(Color.RED, aeroporigem.getCodigo(), aeroporigem.getLocal(), 5));
				lstPoints.add(new MyWaypoint(Color.RED, aeropdestino.getCodigo(), aeropdestino.getLocal(), 5));

				// Para obter um ponto clicado no mapa, usar como segue:
				// GeoPosition pos = gerenciador.getPosicao();

				// Informa o resultado para o gerenciador
				gerenciador.setPontos(lstPoints);

				gerenciador.getMapKit().repaint();
	}
    //Sochi International Airport
	//Kazan International Airport
	// Inicializando os dados aqui...
	private void setup() {
		
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

	/*private void consulta1() {

		// Lista para armazenar o resultado da consulta
		List<MyWaypoint> lstPoints = new ArrayList<>();

		Aeroporto poa = new Aeroporto("POA", "Salgado Filho", new Geo(-29.9939, -51.1711));
		Aeroporto gru = new Aeroporto("GRU", "Guarulhos", new Geo(-23.4356, -46.4731));
		Aeroporto lis = new Aeroporto("LIS", "Lisbon", new Geo(-38.772, -9.1342));
		Aeroporto mia = new Aeroporto("MIA", "Miami International", new Geo(25.7933, -80.2906));

		gerenciador.clear();
		Tracado tr = new Tracado();
		tr.setLabel("Teste");
		tr.setWidth(5);
		tr.setCor(new Color(0, 0, 0, 60));
		tr.addPonto(poa.getLocal());
		tr.addPonto(gru.getLocal());

		gerenciador.addTracado(tr);

		// Adiciona os locais de cada aeroporto (sem repetir) na lista de
		// waypoints

		lstPoints.add(new MyWaypoint(Color.RED, poa.getCodigo(), poa.getLocal(), 5));
		lstPoints.add(new MyWaypoint(Color.RED, gru.getCodigo(), gru.getLocal(), 5));
		lstPoints.add(new MyWaypoint(Color.RED, lis.getCodigo(), lis.getLocal(), 5));
		lstPoints.add(new MyWaypoint(Color.RED, mia.getCodigo(), mia.getLocal(), 5));

		// Para obter um ponto clicado no mapa, usar como segue:
		// GeoPosition pos = gerenciador.getPosicao();

		// Informa o resultado para o gerenciador
		gerenciador.setPontos(lstPoints);

		// Quando for o caso de limpar os traçados...
		// gerenciador.clear();

		gerenciador.getMapKit().repaint();
	}*/

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
	public ObservableList<Pais> ObservableListPaises(){
		return FXCollections.observableList(gerPais.listarTodos());

	}
}
