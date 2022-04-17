/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package es.iespuertodelacruz.ymp.controller;

import es.iespuertodelacruz.ymp.model.Comida;
import es.iespuertodelacruz.ymp.model.Escenario;
import es.iespuertodelacruz.ymp.model.Snake;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;
import javafx.scene.paint.Color;

/**
 * FXML Controller class
 *
 * @author yared
 */
public class FXMLDocumentController implements Initializable {

    @FXML
    public Canvas canvasEscenario;
    @FXML
    public Button btnIniciar;
    
    Escenario mapa;
    
    Timeline timeline;
    
    Snake snake;

    GraphicsContext gc;
    
    int contador = 0;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        gc = canvasEscenario.getGraphicsContext2D();
        mapa = new Escenario();
        mapa.setMapaCanvas(canvasEscenario);
        snake = mapa.getSnake();
        mapa.addComida();
        timeline = obtenerTimeline();
 
    }


    Timeline obtenerTimeline() {

        Timeline tl = new Timeline(new KeyFrame(
                Duration.millis(200), movimiento -> jugar()
        ));

        tl.setCycleCount(Animation.INDEFINITE);

        return tl;
    }
    
    public void jugar(){
        

        gc.clearRect(snake.getSnake().getLast().getX(), snake.getSnake().getLast().getY(), 10, 10);
        gc.setFill(Color.rgb(40, 50, 0));
        switch (snake.getDireccion()) {
            case "UP":
                gc.fillRect(snake.getSnake().getFirst().getX(), snake.getSnake().getFirst().getY()-10, 10, 10);//coordenadas (X, Y, ancho, largo)
                break;
            case "DOWN":
                gc.fillRect(snake.getSnake().getFirst().getX(), snake.getSnake().getFirst().getY()+10, 10, 10);//coordenadas (X, Y, ancho, largo)
                break;

            case "RIGHT":
                gc.fillRect(snake.getSnake().getFirst().getX()+10, snake.getSnake().getFirst().getY(), 10, 10);//coordenadas (X, Y, ancho, largo)
                break;

            case "LEFT":
                
                gc.fillRect(snake.getSnake().getFirst().getX()-10, snake.getSnake().getFirst().getY(), 10, 10);//coordenadas (X, Y, ancho, largo)
                break;
        }
        


        //mapa.addMuro();
        

        
        gc.setFill(Color.BISQUE);
       
        /*
        ArrayList<Muro> muros = mapa.getMuros();
        
            for (Muro muro : muros) {
                
                gc.fillRect(muro.getX(), muro.getY(), 15, 15);
            }
         */   
        
        gc.fillRect(mapa.getComida().getX(), mapa.getComida().getY(), 10, 10);
        System.out.println("Comida: " + mapa.getComida().getX() + ", " + mapa.getComida().getY());
           
        mapa.avanzar();
        System.out.println(snake.getSnake().getFirst().getX() + ", " + 
                snake.getSnake().getFirst().getY() + " SNAKE");
        
    }

    @FXML
    private void start(MouseEvent event) {
        
        timeline.play();
        
    }

    @FXML
    private void girarSnake(KeyEvent event) {
        
        KeyCode code = event.getCode();
        
        if(code == code.UP){
            snake.setDireccion("UP");
        }
        if(code == code.RIGHT){
            snake.setDireccion("RIGHT");
        }
        if(code == code.DOWN){
            snake.setDireccion("DOWN");
        }
        if(code == code.LEFT){
            snake.setDireccion("LEFT");
        }
        
    }
    
}
