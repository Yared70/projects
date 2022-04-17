/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package es.iespuertodelacruz.ymp.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

/**
 *
 * @author yared
 */
public class Escenario {

    String mapaMatriz[][];
    Comida comida;
    ArrayList<Muro> muros;
    Snake snake;
    int cantidadMuros;
    boolean perdido;
    int cantidadComidas;

    public Escenario() {

        this.mapaMatriz = new String[50][50];
        this.muros = new ArrayList<Muro>();
        this.comida = null;
        this.snake = new Snake();
        this.cantidadMuros = 0;
        this.perdido = false;
        this.cantidadComidas = 0;

    }

    Random rnd = new Random();

    public void addComida() {

        setComida(null);

        int x = rnd.nextInt(mapaMatriz.length - 1) + 1;
        int y = rnd.nextInt(mapaMatriz.length - 1) + 1;

        this.comida = new Comida(x, y);
        boolean colocado = false;
        while (!colocado) {

            if (isOcupado(x, y)) {
                x = rnd.nextInt(mapaMatriz.length - 1) + 1;
                y = rnd.nextInt(mapaMatriz.length - 1) + 1;
            } else {
                colocado = true;

            }
        }

    }

    public void addMuro() {

        if (cantidadMuros <= 40 && cantidadComidas == 3) {
            int murosPuestos = 0;
            while(murosPuestos < 3){
            
            int size = rnd.nextInt(5) + 5;
            int direccion = rnd.nextInt(4);
            int x = 0;
            int y = 0;
            boolean colocado = false;
            
            switch (direccion) {
                case 0:

                    x = rnd.nextInt(mapaMatriz.length - 4) + 2;
                    y = rnd.nextInt(mapaMatriz.length - 4) + 2;
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

                    x = rnd.nextInt(mapaMatriz.length - 4) + 2;
                    y = rnd.nextInt(mapaMatriz.length - 4) + 2;
                    while (!colocado) {

                        for (int i = 0; i < size; i++) {
                            if (!isOcupado(x + i, y) && y > mapaMatriz.length) {

                                muros.add(new Muro(x + i, y));

                            }

                        }
                        colocado = true;
                    }

                    break;
                case 2:

                    x = rnd.nextInt(mapaMatriz.length - 4) + 2;
                    y = rnd.nextInt(mapaMatriz.length - 4) + 2;
                    while (!colocado) {

                        for (int i = 0; i < size; i++) {
                            if (!isOcupado(x, y + i) && y > mapaMatriz.length) {

                                muros.add(new Muro(x, y + i));

                            }

                        }
                        colocado = true;
                    }

                    break;
                case 3:

                    x = rnd.nextInt(mapaMatriz.length - 4) + 2;
                    y = rnd.nextInt(mapaMatriz.length - 4) + 2;
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
            this.cantidadMuros += 1;
            cantidadComidas = 0;
            murosPuestos += 1;
            }
        }

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

        boolean choque = false;

        for (int i = snake.getSnake().size() - 1; i >= 1; i--) {

            if (snake.getSnake().get(i).getX() == x
                    && snake.getSnake().get(i).getY() == y) {
                choque = true;
            }

        }

        for (Muro muro : muros) {
            if (muro.getX() == x && muro.getY() == y) {
                choque = true;
            }
        }

        return choque;

    }

    public boolean encontradaComida(int x, int y) {

        boolean comidaEncontrada = false;

        if (comida.getX() == x && comida.getY() == y) {
            comidaEncontrada = true;
            cantidadComidas += 1;
            addMuro();
            addMuro();
        }

        return comidaEncontrada;

    }

    public void avanzar() {

        Parte cabeza = this.snake.getSnake().getFirst();
        int xCabeza = cabeza.getX();
        int yCabeza = cabeza.getY();

        Parte cola = this.snake.getSnake().getLast();

        int tamanoSnake = this.snake.getSnake().size();
        LinkedList<Parte> snakeCompleta = this.snake.getSnake();

        for (int i = tamanoSnake - 1; i >= 1; i--) {
            snakeCompleta.get(i).setX(snakeCompleta.get(i - 1).getX());
            snakeCompleta.get(i).setY(snakeCompleta.get(i - 1).getY());

        }

        switch (this.snake.getDireccion()) {
            case "UP":
                this.snake.getSnake().getFirst().setY(yCabeza - 1);
                this.snake.setDireccion("UP");
                if (yCabeza < 1) {

                    cabeza.setY(mapaMatriz.length-1);

                }
                if (encontradaComida(xCabeza, yCabeza)) {

                    this.snake.addParte(new Parte(cola.getX(), cola.getY() + 1));
                    addComida();

                }
                break;

            case "DOWN":
                this.snake.getSnake().getFirst().setY(yCabeza + 1);
                this.snake.setDireccion("DOWN");

                if (yCabeza > mapaMatriz.length-1) {

                    cabeza.setY(1);

                }
                if (encontradaComida(xCabeza, yCabeza)) {

                    this.snake.addParte(new Parte(cola.getX(), cola.getY() - 1));
                    addComida();

                }

                break;

            case "RIGHT":
                this.snake.getSnake().getFirst().setX(xCabeza + 1);
                this.snake.setDireccion("RIGHT");

                if (xCabeza > mapaMatriz.length-1) {

                    cabeza.setX(1);

                }
                if (encontradaComida(xCabeza, yCabeza)) {

                    this.snake.addParte(new Parte(cola.getX() - 1, cola.getY()));
                    addComida();
                }

                break;

            case "LEFT":
                this.snake.getSnake().getFirst().setX(xCabeza - 1);
                this.snake.setDireccion("LEFT");

                if (xCabeza < 1) {

                    cabeza.setX(mapaMatriz.length - 1);

                }
                if (encontradaComida(xCabeza, yCabeza)) {

                    this.snake.addParte(new Parte(cola.getX() + 1, cola.getY()));
                    addComida();
                }

                break;
        }

        cabeza = this.snake.getSnake().getFirst();

        if (habraChoque(cabeza.getX(), cabeza.getY())) {

            perdido = true;

        }

    }

    public int getCantidadMuros() {
        return cantidadMuros;
    }

    public void setCantidadMuros(int cantidadMuros) {
        this.cantidadMuros = cantidadMuros;
    }

    public int getCantidadComidas() {
        return cantidadComidas;
    }

    public void setCantidadComidas(int cantidadComidas) {
        this.cantidadComidas = cantidadComidas;
    }

    
    
    public boolean isPerdido() {
        return perdido;
    }

    public void setPerdido(boolean perdido) {
        this.perdido = perdido;
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

}
