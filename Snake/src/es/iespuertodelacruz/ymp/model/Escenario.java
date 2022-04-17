/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package es.iespuertodelacruz.ymp.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

/**
 *
 * @author yared
 */
public class Escenario {

    Canvas mapaCanvas;
    String mapaMatriz[][];
    Comida comida;
    ArrayList<Muro> muros;
    Snake snake;
    int cantidadMurosMax;

    public Escenario() {

        this.mapaMatriz = null;
        this.muros = new ArrayList<Muro>();
        this.comida = null;
        this.mapaCanvas = null;
        this.snake = new Snake();
        this.cantidadMurosMax = 20;

    }
    
    public Escenario(int sizeEscenario){
        
        this.mapaMatriz = new String[sizeEscenario][sizeEscenario];
        this.muros = new ArrayList<Muro>();
        this.mapaCanvas = null;
        this.snake = new Snake();
        this.cantidadMurosMax = 20;
        
    }

    Random rnd = new Random();
    
    public void addComida() {

        int x = rnd.nextInt((int) mapaCanvas.getWidth());
        int y = rnd.nextInt((int) mapaCanvas.getHeight());
        
        
        this.comida = new Comida(x, y);
        boolean colocado = false;
        while (!colocado) {

            if (isOcupado(x, y)) {
                x = rnd.nextInt((int) mapaCanvas.getWidth());
                y = rnd.nextInt((int) mapaCanvas.getHeight());
            }else{
                colocado = true;
            }
        }

    }

    public void addMuro() {

        int size = rnd.nextInt(100) + 1;
        int direccion = rnd.nextInt(4);
        int x = 0;
        int y = 0;
        boolean colocado = false;

        switch (direccion) {
            case 0:

                x = rnd.nextInt((int) mapaCanvas.getWidth());
                y = rnd.nextInt((int) mapaCanvas.getHeight());
                while (!colocado) {

                    for (int i = 0; i < size; i++) {
                        if (!isOcupado(x, y - i) && y > 0) {

                            muros.add(new Muro(x, y - i));

                        }

                    }
                    colocado = true;
                }

                break;
            case 1:

                x = rnd.nextInt((int) mapaCanvas.getWidth());
                y = rnd.nextInt((int) mapaCanvas.getHeight());
                while (!colocado) {

                    for (int i = 0; i < size; i++) {
                        if (!isOcupado(x + i, y) && y > mapaCanvas.getWidth()) {

                            muros.add(new Muro(x + i, y));

                        }

                    }
                    colocado = true;
                }

                break;
            case 2:

                x = rnd.nextInt((int) mapaCanvas.getWidth());
                y = rnd.nextInt((int) mapaCanvas.getHeight());
                while (!colocado) {

                    for (int i = 0; i < size; i++) {
                        if (!isOcupado(x, y + i) && y > mapaCanvas.getHeight()) {

                            muros.add(new Muro(x, y + i));

                        }

                    }
                    colocado = true;
                }

                break;
            case 3:

                x = rnd.nextInt((int) mapaCanvas.getWidth());
                y = rnd.nextInt((int) mapaCanvas.getHeight());
                while (!colocado) {

                    for (int i = 0; i < size; i++) {
                        if (!isOcupado(x - i, y) && x > 0) {

                            muros.add(new Muro(x - i, y));

                        }

                    }
                    colocado = true;
                }

                break;

        }
        
        this.cantidadMurosMax += 1;

    }

    public boolean isOcupado(int x, int y) {

        boolean ocupado = false;
            if (comida.getX() == x && comida.getY() == y) {
                return true;
            }
        for (Muro muro : muros) {
            if (muro.getX() == x && muro.getY() == y) {
                return true;
            }
        }
        for (Parte parte : snake.getSnake()) {
            if (parte.getX() == x && parte.getY() == y) {
                return true;
            }
        }
        return ocupado;
    }

    public boolean habraChoque(int x, int y) {

        boolean movPosible = true;

        for (int i = 1; i < snake.getSnake().size(); i++) {

            if (snake.getSnake().get(i).getX() == x
                    && snake.getSnake().get(i).getY() == y) {
                return false;
            }

        }
        for (Muro muro : muros) {
            if (muro.getX() == x && muro.getY() == y) {
                return false;
            }
        }

        return movPosible;

    }
    
    public boolean encontradaComida(int x, int y){
        
        boolean comidaEncontrada = false;
        
        
        if(comida.getX() == x && comida.getY() == y){
            return true;
        }
        
       
        
        return comidaEncontrada;
        
    }
    
    public void avanzar() {
        
        
        int xCabeza = this.snake.getSnake().getFirst().getX();
        int yCabeza = this.snake.getSnake().getFirst().getY();

        switch (this.snake.getDireccion()) {
            case "UP":
                this.snake.addParte(new Parte(xCabeza, yCabeza-10));
                this.snake.setDireccion("UP");
                if(yCabeza < 0){
                    
                    this.snake.getSnake().getFirst().setY((int)mapaCanvas.getHeight());
                    
                    
                }
                if(encontradaComida(xCabeza, yCabeza-10)){
                    
                    this.snake.addParte(new Parte(xCabeza, yCabeza-20));
                    addComida();
                }
                break;

            case "DOWN":
                this.snake.addParte(new Parte(xCabeza, yCabeza+10));
                this.snake.setDireccion("DOWN");
                
                if(yCabeza > (int)mapaCanvas.getHeight()){
                    
                    this.snake.getSnake().getFirst().setY(0);
                    
                }
                if(encontradaComida(xCabeza, yCabeza+10)){
                    
                    this.snake.addParte(new Parte(xCabeza, yCabeza+20));
                    addComida();
                    
                }
                
                break;

            case "RIGHT":
                this.snake.addParte(new Parte(xCabeza+10, yCabeza));
                this.snake.setDireccion("RIGHT");
                
                if(xCabeza > (int)mapaCanvas.getWidth()){
                    
                    this.snake.getSnake().getFirst().setX(0);
                    
                }
                if(encontradaComida(xCabeza+10, yCabeza)){
                    
                    this.snake.addParte(new Parte(xCabeza+20, yCabeza));
                    addComida();
                }
                
                break;

            case "LEFT":
                this.snake.addParte(new Parte(xCabeza-10, yCabeza));
                this.snake.setDireccion("LEFT");
                
                
                if(xCabeza < 0){
                    
                    this.snake.getSnake().getFirst().setX((int)mapaCanvas.getWidth());
                    
                }
                if(encontradaComida(xCabeza-10, yCabeza)){
                    
                    this.snake.addParte(new Parte(xCabeza-20, yCabeza));
                    addComida();
                }
                
                break;
        }
        
        this.snake.getSnake().removeLast();
        
      

        

    }

    public Canvas getMapaCanvas() {
        return mapaCanvas;
    }

    public void setMapaCanvas(Canvas mapaCanvas) {
        this.mapaCanvas = mapaCanvas;
    }

    public String[][] getMapaMatriz() {
        return mapaMatriz;
    }

    public void setMapaMatriz(String[][] mapaMatriz) {
        this.mapaMatriz = mapaMatriz;
    }

    public Comida getComida() {
        return comida;
    }

    public void setComida(Comida comida) {
        this.comida = comida;
    }

    public ArrayList<Muro> getMuros() {
        return muros;
    }

    public void setMuros(ArrayList<Muro> muros) {
        this.muros = muros;
    }

    public Snake getSnake() {
        return snake;
    }

    public void setSnake(Snake snake) {
        this.snake = snake;
    }

    public int getCantidadMurosMax() {
        return cantidadMurosMax;
    }

    public void setCantidadMurosMax(int cantidadMurosMax) {
        this.cantidadMurosMax = cantidadMurosMax;
    }

    
    
}
