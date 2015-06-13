/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import Model.Carta;
import Model.Jugador;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;

/**
 *
 * @author x
 */
public class Reglas {
    static ArrayList<Carta> ordenCartas;
    Jugador  jugador;
    Random r = new Random();
    static int NUM_IMAGENES=25;
    static int NUM_VECES_IMAGEN=4;
    static Carta cartaNueva;
    static ArrayList<Carta> posicionCartas;
    
    public static ArrayList<Carta> getOrdenCartas() {
       if(ordenCartas == null){
            //ArrayList<Carta> posicionCartas=new ArrayList<Carta>();
            posicionCartas=new ArrayList<Carta>();
            //Carta cartaNueva;
            boolean volteada=false;

           for (int idCarta=1;idCarta<=NUM_IMAGENES;idCarta++){
               for (int num_veces=1;num_veces<=NUM_VECES_IMAGEN;num_veces++ ){

                   cartaNueva=new Carta(idCarta,volteada);
                   posicionCartas.add(cartaNueva);             

               }         

            }

           Collections.shuffle(posicionCartas);
           ordenCartas=posicionCartas;
           System.out.println("GETOR " + ordenCartas.get(0).getId());
       }
        return ordenCartas;
    }

    public Jugador getJugador() {
        return jugador;
    }
     
}
