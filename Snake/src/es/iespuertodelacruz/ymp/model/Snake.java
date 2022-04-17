/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package es.iespuertodelacruz.ymp.model;

import java.util.LinkedList;

/**
 *
 * @author yared
 */
public class Snake {
    
    LinkedList<Parte> snake;
    String direccion;
    
    public Snake() {
    
        snake = new LinkedList<>();
        direccion = "RIGHT";
        snake.add(new Parte(25, 25));
        
        
    }
    
    public void addParte(Parte parte){
        
        snake.addFirst(parte);
        
    }

    public LinkedList<Parte> getSnake() {
        return snake;
    }

    public void setSnake(LinkedList<Parte> snake) {
        this.snake = snake;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
    
    
    
    
    
}
