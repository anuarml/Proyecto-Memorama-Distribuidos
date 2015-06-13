package Server;

import Model.Jugador;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Esta clase se conectará con el cliente y recibirá sus mensajes
 *
 * @author Vanessa, Abner
 */
public class TCPServer {
    
    ArrayList<Jugador> clients;
    
    public TCPServer(){
        clients = new ArrayList();
    }

    public static void main(String args[]) {
        TCPServer tcpS = new TCPServer();
        String clientAdd;
        Jugador temp;
        try {
            int serverPort = 7896;
            ServerSocket listenSocket = new ServerSocket(serverPort);
            while (true) {
                //Se acepta la solicitud de conexion del cliente
                Socket clientSocket = listenSocket.accept();
                if (tcpS.getClients().size() >= 8){ 
                    System.out.println("Ya se alcanzó la capacidad máxima de jugadores.");
                    clientSocket.close(); 
                    continue; 
                }
                temp = new Jugador(tcpS.getClients().size(), 0, clientSocket, false);
                tcpS.getClients().add(temp);
                clientAdd = clientSocket.getInetAddress().getHostAddress();
                System.out.println("Se ha conectado el cliente número " + tcpS.getClients().size() + " en la direccion: "
                                    + clientAdd);
                //Se procesa el mensaje en la clase Connection
                new Connection(clientSocket);
            }
        } catch (IOException e) {
            System.out.println("Listen :" + e.getMessage());
        }
    }
    
    public ArrayList<Jugador> getClients() {
        return clients;
    }

    public void setClients(ArrayList<Jugador> clients) {
        this.clients = clients;
    }
}
