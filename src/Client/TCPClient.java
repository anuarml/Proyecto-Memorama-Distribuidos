/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

import Vista.TableroCartas;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author 
 */
public class TCPClient {

    MulticastConnection multicast;
    DataOutputStream out;
    DataInputStream in;
    Socket s;
    
    String host = "localhost";
    int serverPort = 7896;

    public TCPClient(TableroCartas tableroCartas) {
        //Se une al grupo multicast para poder recibir los mensajes de otros clientes.
        if(tableroCartas != null)
            multicast = new MulticastConnection(tableroCartas);
        
        try {
            
            //Crea el socket para conectarse con el server.
            s = new Socket(host, serverPort);
            //Crea el stream para mandar mensajes.
            out = new DataOutputStream(s.getOutputStream());
            in = new DataInputStream(s.getInputStream());

        } catch (UnknownHostException e) {
            System.out.println("Sock:" + e.getMessage());
        } catch (EOFException e) {
            System.out.println("EOF:" + e.getMessage());
        } catch (IOException e) {
            System.out.println("IO:" + e.getMessage());
        }

    }

    void sendMessage(String msg) {
        try {
            //Limpia el stream.
            out.flush();
            //Envia un mensaje al server.
            out.writeUTF(msg + "\n");
        } catch (IOException ex) {
            Logger.getLogger(TCPClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public String recieveMessage(){
        String msg = null;
        try {

            msg = in.readUTF();
            
            return msg;
        } catch (IOException ex) {
            Logger.getLogger(TCPClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        return msg;
    }
    
    public void close(){
        try {
            s.close();
        } catch (IOException ex) {
            Logger.getLogger(TCPClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
