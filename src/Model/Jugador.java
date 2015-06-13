/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author Vanessa
 */
public class Jugador {
    int id;
    String name;
    int score;
    boolean isPlaying;

    public Jugador(int id, String name, int score, boolean isPlaying) {
        this.id = id;
        this.name = name;
        this.score = score;
        this.isPlaying = isPlaying;
    }
    
    
}
