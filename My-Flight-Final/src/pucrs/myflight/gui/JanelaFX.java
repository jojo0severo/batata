package pucrs.myflight.gui;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.SwingUtilities;

import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.viewer.GeoPosition;

import javafx.application.Application;
import javafx.collections.FXCollections;
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
	private ComboBox<CiaAerea> comboCia;
	private GerenciadorPaises gerPais;

	@Override
	public void start(Stage primaryStage) throws Exception {

		setup();

		GeoPosition poa = new GeoPosition(-30.05, -51.18);
		gerenciador = new GerenciadorMapa(poa, GerenciadorMapa.FonteImagens.VirtualEarth);
		mouse = new EventosMouse();
		gerenciador.getMapKit().getMainMap().addMouseListener(mouse);
		gerenciador.getMapKit().getMainMap().addMouseMotionListener(mouse);

		createSwingContent(mapkit);

		BorderPane pane = new BorderPane();
		GridPane leftPane = new GridPane();

		leftPane.setAlignment(Pos.CENTER);
		leftPane.setHgap(10);
		leftPane.setVgap(10);
		leftPane.setPadding(new Insets(10, 10, 10, 10));

		Button btnConsulta = new Button("Consulta");
		TextField origem = new TextField(); 
		Label origemlabel = new Label("Digite o Aeroporto de Origem");
		TextField destino = new TextField();
		Label destinoLabel = new Label("Digite o Aeroporto de Destino");
		
		leftPane.add(origemlabel, 0, 0);
		leftPane.add(origem, 0, 1);
		leftPane.add(destinoLabel, 0, 2);
		leftPane.add(destino, 0, 3);		
		leftPane.add(btnConsulta, 1, 5);

		btnConsulta.setOnAction(e -> {
			consulta(origem.getText(), destino.getText());
		});

		pane.setCenter(mapkit);
		pane.setLeft(leftPane);

		Scene scene = new Scene(pane, 500, 500);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Mapas com JavaFX");
		primaryStage.show();

	}

	private void consulta(String origem, String destino) {
		
		// Lista para armazenar o resultado da consulta
				List<MyWaypoint> lstPoints = new ArrayList<>();
				
				//Pega os aeroportos
				Aeroporto aeroporigem = gerAero.buscarPorNome(origem);
				Aeroporto aeropdestino = gerAero.buscarPorNome(destino);
				
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
}
