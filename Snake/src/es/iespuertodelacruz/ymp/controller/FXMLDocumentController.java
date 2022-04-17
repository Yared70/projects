/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package es.iespuertodelacruz.ymp.controller;

import es.iespuertodelacruz.ymp.model.Comida;
import es.iespuertodelacruz.ymp.model.Escenario;
import es.iespuertodelacruz.ymp.model.Muro;
import es.iespuertodelacruz.ymp.model.Parte;
import es.iespuertodelacruz.ymp.model.Snake;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
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
    
    boolean juegoIniciado;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        gc = canvasEscenario.getGraphicsContext2D();
        mapa = new Escenario();
        snake = mapa.getSnake();
        mapa.addComida();
        timeline = obtenerTimeline();
 
    }


    Timeline obtenerTimeline() {

        Timeline tl = new Timeline(new KeyFrame(
                Duration.millis(50), movimiento -> jugar()
        ));

        tl.setCycleCount(Animation.INDEFINITE);

        return tl;
    }
    
    public void jugar(){
        
        Random rnd = new Random();
        
        gc.clearRect(0, 0, canvasEscenario.getWidth(), canvasEscenario.getHeight());
        
       
        LinkedList<Parte> snakeCompleta = this.snake.getSnake();
        
        for (Parte parte : snakeCompleta) {
            gc.fillRect(parte.getX()*10, parte.getY()*10, 10, 10);
            gc.setFill(Color.rgb(rnd.nextInt(100), rnd.nextInt(256), 100));
        }

        gc.setFill(Color.BISQUE);
       
       
        ArrayList<Muro> muros = mapa.getMuros();
        
            for (Muro muro : muros) {
                
                gc.fillRect(muro.getX()*10, muro.getY()*10, 10, 10);
            }
            
            
            

        gc.setFill(Color.RED);
        
            
        gc.fillOval(mapa.getComida().getX()*10, mapa.getComida().getY()*10, 10, 10);
        
           
        mapa.avanzar();
        
        if(mapa.isPerdido()){
            timeline.stop();
            Alert alerta = new Alert(Alert.AlertType.INFORMATION, "HAS PERDIDO!");
            alerta.show();
        }
        
        
    }

    @FXML
    private void start(MouseEvent event) {
        if(juegoIniciado){
            timeline.stop();
        }else{
        timeline.play();
        }
        juegoIniciado = !juegoIniciado;
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
