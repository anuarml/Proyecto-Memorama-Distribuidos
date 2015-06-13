/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import Model.*;
import com.google.gson.Gson;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Yussel,Ramon,Anuar
 */
public class ServerController {
    
    private static ServerController serverController;
    private int session_id=0;
    private Gson gson;
    private ArrayList<Carta> tableroCartas;
    
    private ServerController(){
        gson = new Gson();
        tableroCartas = Reglas.getOrdenCartas();
    }
    
    public static ServerController getInstance(){
        if(serverController == null){
            serverController = new ServerController();
            return serverController;
        }
        else{
            return serverController; 
        }
    }
    
   
    public void processMessage(String message,DataOutputStream out){
        try {
            System.out.print("Mensaje process "+message);
            RequestMessage reqMsg = gson.fromJson(message, RequestMessage.class);
            
            switch(reqMsg.getMessageType()){               
                case RequestMessage.OBTENER_TABLERO:
                {
                    //Reglas tablero = new Reglas();
                    //ArrayList<Carta> tableroCartas = Reglas.getOrdenCartas();
                    //tableroCartas = Reglas.getOrdenCartas();
                    System.out.println(tableroCartas.get(0).getId());
                    out.writeUTF(gson.toJson(tableroCartas)+'\n');
                    break;
                }
                case RequestMessage.ACTUALIZAR_CARTA_JUGADA:
                {
                    String data1 = reqMsg.getData1();
                    
                    int indiceCarta = gson.fromJson(data1, int.class);
                    System.out.println("Antes de mostrar");
                    boolean mostrada = mostrarCarta(indiceCarta);
                    
                    out.writeUTF(gson.toJson(mostrada)+'\n');
                    
                    break;
                }
                
            }
        } catch (IOException ex) {
            Logger.getLogger(ServerController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private synchronized boolean mostrarCarta(int indiceCarta){
        //Carta cartaVolteada = tableroCartas.get(carta.getId());
        //int indiceCartaVolteada = tableroCartas.indexOf(carta);
        //Carta cartaVolteada = tableroCartas.get(indiceCartaVolteada);
        //Carta cartaVolteada = carta;
        Carta cartaVolteada = tableroCartas.get(indiceCarta);
        System.out.println("En mostrar");
        if(cartaVolteada.estaBolteada() == false){
            
            String jsonCarta = gson.toJson(indiceCarta);
            
            RequestMessage rm = new RequestMessage(RequestMessage.ACTUALIZAR_CARTA_JUGADA, jsonCarta);
            
            String jsonMsg = gson.toJson(rm);
            //Carta cartaVolteada = tableroCartas.get(indiceCartaVolteada);
            cartaVolteada.setEstaBolteada(true);
            
            //Se obtiene la instancia del MulticasServer
            MulticastServer ms = MulticastServer.getInstance();
            //Se difunde el mensaje a todo el grupo
            System.out.println("Despues validacion");
            ms.sendMulticast(jsonMsg);
            
            return true;
        }
        return false;
    }
    
    
    
}
