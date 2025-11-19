package buscaminas.servidor;

import buscaminas.comunes.BaseDeDatos;
import buscaminas.comunes.Mensaje;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ManejoCliente extends Thread{
    List<ObjectOutputStream> clientes = new ArrayList<>();
    Socket cliente;
    ObjectInputStream entradaDatos;
    ObjectOutputStream salidaDatos;
    int noJugador;
    static String[] personajes = new String[2];
    static boolean[] vivir = new boolean[2];
    
    public ManejoCliente(List<ObjectOutputStream> clientes, Socket cliente, ObjectInputStream entradaDatos, ObjectOutputStream salidaDatos, int noJugador){
        this.clientes = clientes;
        this.cliente = cliente;
        this.entradaDatos = entradaDatos;
        this.salidaDatos = salidaDatos;
        this.noJugador = noJugador;
    }
    
    @Override
    public void run(){
        try{
            while(true){
                Object recibido = entradaDatos.readObject();
                Mensaje msj = (Mensaje) recibido;
                decodificar(msj);
            }
        }catch(IOException | ClassNotFoundException e){
            desconectar();
        }
    }
    
    public void enviarMensajeGrupal(Mensaje msj){
        for(ObjectOutputStream c: clientes){
            try{
                c.writeObject(msj);
            }catch(IOException e){
                //
            }
        }
    }
    
    public void enviarMensaje(Mensaje msj){
        try {
            salidaDatos.writeObject(msj);
        } catch (IOException ex) {
            Logger.getLogger(ManejoCliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void enviarMensajeOpuesto(Mensaje msj){
        try{
            if(noJugador==0){
                clientes.get(1).writeObject(msj);
            }else if(noJugador==1){
                clientes.get(0).writeObject(msj);
            }
        }catch (IOException ex){
            Logger.getLogger(ManejoCliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void decodificar(Mensaje msj){
        switch(msj.getTipo()){
            case "CONEXION":enviarMensajeGrupal(new Mensaje("CONEXION", clientes.size()));
                            System.out.println(clientes.size());
            break;
            case "TABLERO": enviarMensajeGrupal(new Mensaje("TABLERO", msj.getContenido()));
                            System.out.println(msj.getContenido());
            break;
            case "OPCIONES":enviarMensajeGrupal(new Mensaje("OPCIONES", msj.getContenido()));
                            System.out.println(msj.getContenido());
            break;
            case "PERSONAJE":personajes[noJugador]= String.valueOf(msj.getContenido());
                            System.out.println(msj.getContenido());
            break;
            case "SELECCION":boolean lleno = false;
                            for(int i=0; i<personajes.length;i++){
                                if(personajes[i]==null){
                                    lleno=false;
                                    break;
                                }else{
                                    lleno=true;
                                }
                            }
                            if(lleno){
                                System.out.println(personajes[0]);
                                System.out.println(personajes[1]);
                                try {
                                    clientes.get(0).writeObject(new Mensaje("OPONENTE", personajes[1]));
                                    clientes.get(1).writeObject(new Mensaje("OPONENTE", personajes[0]));
                                    enviarMensajeGrupal(new Mensaje("SELECCION", lleno));
                                } catch (IOException ex) {
                                    Logger.getLogger(ManejoCliente.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }      
            break;
            case "INICIAR": 
            break;
            case "VIDAS": enviarMensajeOpuesto(new Mensaje("VIDAS",msj.getContenido()));
            break;
            case "VIVIR":   if((Boolean) msj.getContenido()){
                                //
                            }else{
                                //
                            }
            break;
            case "EXIT":try{
                            entradaDatos.close();
                            salidaDatos.close();
                            cliente.close();
                            clientes.remove(salidaDatos);
                        }catch (IOException ex) {
                            Logger.getLogger(ManejoCliente.class.getName()).log(Level.SEVERE, null, ex);
                        }
        }
    }
    
    public void desconectar(){
        System.out.println("Cliente desconectado");
        enviarMensajeOpuesto(new Mensaje("DESCONECTADO",true));
        try{
            entradaDatos.close();
            salidaDatos.close();
            cliente.close();
            clientes.remove(salidaDatos);
        }catch (IOException ex) {
            Logger.getLogger(ManejoCliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
