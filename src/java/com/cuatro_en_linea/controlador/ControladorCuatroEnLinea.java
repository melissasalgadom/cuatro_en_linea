/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cuatro_en_linea.controlador;

import com.cuatro_en_linea.controlador.util.JsfUtil;
import com.cuatro_en_linea.modelo.Usuario;
import com.cuatro_en_linea.modelo.grafo.Arista;
import com.cuatro_en_linea.modelo.grafo.Ficha;
import com.cuatro_en_linea.modelo.grafo.Grafo;
import com.cuatro_en_linea.modelo.grafo.Vertice;
import javax.inject.Named;
import org.primefaces.model.diagram.DefaultDiagramModel;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
import org.primefaces.model.diagram.Connection;
import org.primefaces.model.diagram.DefaultDiagramModel;
import org.primefaces.model.diagram.Element;
import org.primefaces.model.diagram.connector.StraightConnector;
import org.primefaces.model.diagram.endpoint.BlankEndPoint;
import org.primefaces.model.diagram.endpoint.EndPointAnchor;

/**
 *
 * @author melis
 */
@Named(value = "controladorCuatroEnLinea")
@ApplicationScoped
public class ControladorCuatroEnLinea implements Serializable {

    private boolean estadoJuego = false;
    private Date fechaSistema;
    private int numero ;
    private List<Vertice> vertices;

    private int ancho = 7;
    private int alto = ancho - 1;
    private int limiteSup = ancho * alto;

    private byte distancia = 4;
    private DefaultDiagramModel model;
    private Grafo tablero = new Grafo();

    public ControladorCuatroEnLinea() {
    }

    public void llenarAristas() {

        //Crear aristas
        for (int i = 0; i <= 6; i++) {
            for (Vertice vert : tablero.traerTablero("k" + i)) {
                if (vert.getId() % ancho != 0) {
                    tablero.adicionarArista(vert.getId(), vert.getId() + 1, 0);
                }
                if (vert.getId() + ancho <= limiteSup * i) {
                    tablero.adicionarArista(vert.getId(), vert.getId() + ancho, 0);

                    if (vert.getId() % ancho != 0) {
                        tablero.adicionarArista(vert.getId(), vert.getId() + ancho + 1, 0);
                        tablero.adicionarArista(vert.getId() + 1, vert.getId() + ancho, 0);
                    }
                }

            }
        }
    }

    @PostConstruct
    public void pintarTablero() {

        model = new DefaultDiagramModel();
        model.setMaxConnections(-1);
        model.setConnectionsDetachable(false);

        int pos = 2;
        for (int k = 0; k < 6; k++) {
            int x = pos;
            int y = 2;
            String color = "gris";
            String styleColor = "ui-diagram-element-ficha-gris";
            for (int i = 1; i <= alto; i++) {
                for (int j = 1; j <= ancho; j++) {
                    tablero.adicionarVertice(new Ficha(color, "k" + (k + 1)));
                    Element ceo = new Element(tablero.getVertices().size(), x + "em", y + "em");
                    ceo.setId(String.valueOf(tablero.getVertices().size()));
                    ceo.setDraggable(false);
                    ceo.setStyleClass(styleColor);
                    ceo.addEndPoint(new BlankEndPoint(EndPointAnchor.CENTER));
                    model.addElement(ceo);
                    x = x + 5;
                }
                y = y + 5;
                x = pos;
            }
            pos = pos + 40;
        }
        llenarAristas();
        //pintarFila();
        StraightConnector connector = new StraightConnector();
        connector.setPaintStyle("{strokeStyle:'transparent', lineWidth:3}");
        connector.setHoverPaintStyle("{strokeStyle:'#4f4f4f'}");
        model.setDefaultConnector(connector);

        //recorrer aristas
        for (Arista arista : tablero.getAristas()) {
            Element origen = model.getElements().get(arista.getOrigen() - 1);
            Element destino = model.getElements().get(arista.getDestino() - 1);
            model.connect(new Connection(origen.getEndPoints().get(0), destino.getEndPoints().get(0)));

        }
    }

    public void activarJuego() {
        estadoJuego = true;
        JsfUtil.addSuccessMessage("Se ha habilitado el juego");
    }

    public void pintarFila() {
        model = new DefaultDiagramModel();
        model.setMaxConnections(-1);
        model.setConnectionsDetachable(false);

        for (int k = 0; k < 1; k++) {
            int x = 2;
            int y = 2;
            String color = "Rojo";

            for (int j = 1; j <= ancho; j++) {
                tablero.adicionarVertice(new Ficha(color, "k" + (k + 1)));
                Element ceo = new Element(tablero.getVertices().size(), x + "em", y + "em");
                ceo.setDraggable(false);
              
        
          ceo.addEndPoint(new BlankEndPoint(EndPointAnchor.CENTER));
                model.addElement(ceo);

            }
        }
    }

    public void multipo(){
        for(Vertice vert:tablero.getVertices()){
        if(vert.getId() % numero ==0 ){
            String styleColor = "ui-diagram-element-ficha-rosado";
            model.getElements().get(vert.getId()-1).setStyleClass(styleColor);
        }else{
            String styleColor = "ui-diagram-element-ficha-gris";
            model.getElements().get(vert.getId()-1).setStyleClass(styleColor);
        }
    }
    }
    
    public void jugada(int idFicha) {
        Vertice vert=tablero.getVertices().get(idFicha-1);
        
        
//        int cont = 0;
//        for (Vertice vert : vertices) {
//
//            if (vert.getId() == idFicha && tablero.getVertices().get(idFicha - 1).getFicha().getColor().compareTo("gris") == 0) {
//
//                cont = idFicha + ancho;
//            } else {
//                cont = idFicha - ancho;
//                tablero.getVertices().get(idFicha - 1).getFicha().setColor("rosado");
//            }
//            JsfUtil.addSuccessMessage("Elija otra jugada");
//        }
        
    }

    public byte getDistancia() {
        return distancia;
    }

    public void setDistancia(byte distancia) {
        this.distancia = distancia;
    }

    public DefaultDiagramModel getModel() {
        return model;
    }

    public void setModel(DefaultDiagramModel model) {
        this.model = model;
    }

    public int getAncho() {
        return ancho;
    }

    public void setAncho(int ancho) {
        this.ancho = ancho;
    }

    public int getAlto() {
        return alto;
    }

    public void setAlto(int alto) {
        this.alto = alto;
    }

    public Grafo getTablero() {
        return tablero;
    }

    public void setTablero(Grafo tablero) {
        this.tablero = tablero;
    }

    public int getLimiteSup() {
        return limiteSup;
    }

    public void setLimiteSup(int limiteSup) {
        this.limiteSup = limiteSup;
    }

    public boolean isEstadoJuego() {
        return estadoJuego;
    }

    public void setEstadoJuego(boolean estadoJuego) {
        this.estadoJuego = estadoJuego;
    }

    public Date getFechaSistema() {
        return new Date();
    }

    public void setFechaSistema(Date fechaSistema) {
        this.fechaSistema = fechaSistema;
    }

    public List<Vertice> getVertices() {
        return vertices;
    }

    public void setVertices(List<Vertice> vertices) {
        this.vertices = vertices;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }
    

}
