/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista;



import Client.ClientController;
import Model.Carta;
import Model.Jugador;
import Model.RequestMessage;
import com.google.gson.Gson;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author x
 */
public class TableroCartas implements ActionListener, Observer {
public int NUM_INTENTOS=2;
private int NUM_CARTAS=100;
int HEIGHT;
int WIDTH;

//public ControladorComunicacion ctrlComunicacion;
public ClientController clientControl;
public boolean esMiTurno=false;
public int numIntechosHechos=0;
public ArrayList<Carta> ordenImagenes;
public ArrayList<Integer> posicionCartasVolteadas;

JButton []arregloCartas = new JButton[NUM_CARTAS];//arreglo de botones
JPanel contenedorCartas;
JFrame ventanaCartas;
Jugador jugadorMemorama;


    public TableroCartas(boolean turno, ArrayList<Carta> ordenImagenes) {
        this.ventanaCartas=new JFrame("Memorama");
        this.ventanaCartas.setLayout(new BorderLayout(10, 20));
        this.ordenImagenes=ordenImagenes;
        this.esMiTurno=turno;
        this.posicionCartasVolteadas=new  ArrayList<Integer>();
        clientControl = new ClientController(this);
        MostrarCartas(ordenImagenes);
        
        ventanaCartas.add(this.contenedorCartas, BorderLayout.NORTH);
        ventanaCartas.setSize(1400, 600);
        ventanaCartas.setLocation(0, 0);
        ventanaCartas.setVisible(true);
        ventanaCartas.setResizable(true);
        ventanaCartas.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                           
    }
    
      
    @Override
    public void actionPerformed(ActionEvent e) {
        if(esMiTurno){
            
            if(this.numIntechosHechos<2){
             
            int idImage=Integer.parseInt(e.getActionCommand());
              
            this.posicionCartasVolteadas.add(idImage);
            this.voltearCarta(idImage);
            clientControl.actualizarCarta(idImage-1);
            this.numIntechosHechos++;
                
            }else{
                JOptionPane.showMessageDialog(ventanaCartas, "No es tu turno.");
            }
        }else{
            JOptionPane.showMessageDialog(ventanaCartas, "No es tu turno.");
        }
        if(this.numIntechosHechos==2){
            this.checarCartasVolteadas();
        }
        
        
        
            
    }
    
    private void MostrarCartas(ArrayList <Carta> ordenCartas){
        this.contenedorCartas = new JPanel(new GridLayout(10, 10, 1 , 1));    
     
        for(int i=0; i<arregloCartas.length; i++){//ciclo para crear, añadir, establecer propiedades a los botones
            this.anadirCarta(i,ordenCartas); 
        }
    }
    
    private void anadirCarta(int i,ArrayList <Carta> ordenCartas){
        File file;
        ImageIcon imagen;
        if(ordenCartas.get(i).estaBolteada()){
           
            file =new File (ordenCartas.get(i).getUrlCarta());
            imagen=new ImageIcon(file.getAbsolutePath()); 
        }else{
            String defaultCarta="src/images/0.jpg";
            file=new File(defaultCarta);
            imagen=new ImageIcon(file.getAbsolutePath()); 
        }
        
            arregloCartas[i] = new JButton("" + (i + 1), imagen);
            arregloCartas[i].setSize(40, 40);
            this.contenedorCartas.add(arregloCartas[i]);
            arregloCartas[i].setMargin(new Insets(0, 0, 0, 0));
            arregloCartas[i].addActionListener(this);
    }
    
    private void MostrarGanador(){
        JOptionPane.showMessageDialog(ventanaCartas, "Ganaste");
        ventanaCartas.setVisible(false);
        MenuInicio inicio=new MenuInicio();
        inicio.setVisible(true);
    }

   
private void voltearCarta(int idImage){
    
        Carta cartaPorVoltear=this.ordenImagenes.get(idImage-1);
        System.out.println(cartaPorVoltear.getId());
        File file=new File(cartaPorVoltear.getUrlCarta());
        ImageIcon imagen=new ImageIcon(file.getAbsolutePath());
        
        arregloCartas[idImage-1].setIcon(imagen);
       
}

private void desvoltearCarta(int idImage){
    
        String defaultCarta="src/images/0.jpg";
        File file=new File(defaultCarta);
        ImageIcon imagen=new ImageIcon(file.getAbsolutePath());

        arregloCartas[idImage-1].setIcon(imagen);
     
}

    @Override
    public void update(Observable o, Object arg) {
        
    }
    
    public void update(String msg){
        
        Gson gson = new Gson();
        System.out.println("Mensaje update " + msg);
        RequestMessage reqMsg = gson.fromJson(msg, RequestMessage.class);
        switch (reqMsg.getMessageType()){
            case RequestMessage.ACTUALIZAR_CARTA_JUGADA:{
                String data1 = reqMsg.getData1();
                
                int indiceCarta = gson.fromJson(data1, int.class);
                System.out.println("Indice: " + indiceCarta );
                voltearCarta(indiceCarta+1);
                
                break;
            }
        }
    }

    private void checarCartasVolteadas() {
    
        int idPrimeraCarta = this.posicionCartasVolteadas.get(0);       
        int idSegundaCarta = this.posicionCartasVolteadas.get(1);
        
        Carta primeraCarta = this.ordenImagenes.get(idPrimeraCarta-1);
        Carta segundaCarta = this.ordenImagenes.get(idSegundaCarta-1);
     

        if (primeraCarta.getId() == segundaCarta.getId()) {
            if(!(primeraCarta.estaBolteada())&& !(segundaCarta.estaBolteada())){
            JOptionPane.showMessageDialog(ventanaCartas, "Es Par");
            //this.ctrlComunicacion.notificarCartasVolteadas(this.posicionCartasVolteadas);
            this.ordenImagenes.get(idPrimeraCarta).setEstaBolteada(true);
            this.ordenImagenes.get(idSegundaCarta).setEstaBolteada(true);
            JOptionPane.showMessageDialog(ventanaCartas,"Termino turno");
            }
            
        }else{
            JOptionPane.showMessageDialog(ventanaCartas,"Termino turno");
           this.desvoltearCarta(idPrimeraCarta);
           this.desvoltearCarta(idSegundaCarta);
            this.esMiTurno=false;
        }
         this.posicionCartasVolteadas.clear();
        this.numIntechosHechos=0;
        this.esMiTurno=true;
        
    }
  
}
