/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

import Server.MulticastServer;
import Vista.TableroCartas;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Vanessa, Abner
 */
public class MulticastConnection extends Thread{

    MulticastSocket s;
    TableroCartas tableroCartas;

    public MulticastConnection(TableroCartas tableroCartas) {
        this.tableroCartas = tableroCartas;
        String grp = "228.5.6.7";
        InetAddress group;
        int port = 6789;

        try {
            //Se crea el socket multicast con el puerto indicado
            s = new MulticastSocket(port);
            //Se obtiene la direccion por el nombre
            group = InetAddress.getByName(grp);
            //El socket multicast se une al grupo
            s.joinGroup(group);
            start();
        } catch (SocketException e) {
            System.out.println("Socket: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("IO: " + e.getMessage());
        }
    }

    @Override
    public void run() {
        
        for (;;) {
            try {
                byte[] buffer = new byte[1000];
                
                DatagramPacket messageIn = new DatagramPacket(buffer, buffer.length);
                System.out.println("llega a ClientM");
                //Recibe los mensajes enviados por multicast
                s.receive(messageIn);
                
                //Actualiza el estado de los asientos en tableroCartas
                tableroCartas.update(new String(messageIn.getData(),"UTF-8").trim());
            } catch (IOException ex) {
                Logger.getLogger(MulticastServer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
