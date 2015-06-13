/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

import Model.Carta;
import Model.RequestMessage;
import Vista.TableroCartas;
import com.google.gson.Gson;
import java.util.ArrayList;

/**
 *
 * @author 
 */
public class ClientController {
    private TCPClient tcpClient;
    private Gson gson = new Gson();
    private ArrayList<Carta> arregloCartasP;
    
    public ClientController(TableroCartas tableroCartas){
        gson = new Gson();
        tcpClient = new TCPClient(tableroCartas);
    }
    
    public ClientController(){
        gson = new Gson();
        tcpClient = new TCPClient(null);
    }
    
    
       public ArrayList<Carta> obtenerTablero(){
        
        RequestMessage rm = new RequestMessage(RequestMessage.OBTENER_TABLERO);

        String jsonMsg = gson.toJson(rm);

        //Envía petición al servidor
        tcpClient.sendMessage(jsonMsg);

        //Recibe respeusta del servidor
        String msg = tcpClient.recieveMessage();

        ArrayList<Carta> arregloCartas = gson.fromJson(msg, ArrayList.class);
        
        ArrayList<Carta> arregloCartasFinal = new ArrayList();
        for (int i = 0; i < arregloCartas.size(); i++) {
           Carta carta1;
           carta1 = gson.fromJson(arregloCartas.get(i)+"", Carta.class);
           arregloCartasFinal.add(i,carta1);
           
       }
           System.out.println(arregloCartasFinal.get(0).getId());
        
        
        return arregloCartasFinal;
    }
       
    public boolean actualizarCarta(int indiceCarta){
        String jsonCarta = gson.toJson(indiceCarta);
        
        RequestMessage rm = new RequestMessage(RequestMessage.ACTUALIZAR_CARTA_JUGADA, jsonCarta);
        
        String jsonMsg = gson.toJson(rm);
        
        //Envía petición al servidor
        tcpClient.sendMessage(jsonMsg);
        
        //Recibe respeusta del servidor
        String msg = tcpClient.recieveMessage();
        
        boolean actualizada = gson.fromJson(msg, boolean.class);
        
        return actualizada;
    }
       
   
    
    public void close(){
        tcpClient.close();
    }
}
