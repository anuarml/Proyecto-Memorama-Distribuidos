/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.net.Socket;

/**
 *
 * @author Vanessa, Abner
 */
public class Jugador {
    int id;
    Socket cs;
    int score;
    boolean isPlaying;

    public Jugador(int id, int score,Socket cs, boolean isPlaying) {
        this.id = id;
        this.cs = cs;
        this.score = score;
        this.isPlaying = isPlaying;
    }
    
    
}
