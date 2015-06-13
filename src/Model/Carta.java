/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author x
 */
public class Carta {
    int id;
    boolean volteada;
   
    
    public Carta(int num_veces, boolean b) {
         this.id=num_veces;
        this.volteada=b;
    }

    
    public int getId() {
        return id;
    }

  
    public String getUrlCarta() {
            String urlCarta="src/images/"+this.id+".jpg";
        return urlCarta;
    }

  

    public boolean estaBolteada() {
        return volteada;
    }

    public void setEstaBolteada(boolean estaBolteada) {
        this.volteada = estaBolteada;
    }

    public boolean isVolteada() {
        return volteada;
    }

    public void setVolteada(boolean volteada) {
        this.volteada = volteada;
    }

   
    
    
}
