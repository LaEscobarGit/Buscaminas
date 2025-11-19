package buscaminas.comunes;

import com.db4o.*;
import com.db4o.query.Query;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class BaseDatosBuscaminas {

    private static final String ARCHIVO_DB = "DatosBuscaminas.db4o";
    private ObjectContainer db;

    // Constructor — abre la BD
    public BaseDatosBuscaminas() {
        db = Db4o.openFile(ARCHIVO_DB);
    }

    
    
    public void guardarConfiguracion(ConfiguracionJuego config) {
        // Borra configuraciones previas para solo tener 1 activa
        List<ConfiguracionJuego> previas = db.query(ConfiguracionJuego.class);
        for (ConfiguracionJuego c : previas) db.delete(c);

        db.store(config);
        db.commit();
        System.out.println("Configuración guardada: " + config);
    }

    public ConfiguracionJuego obtenerConfiguracion() {
        List<ConfiguracionJuego> lista = db.query(ConfiguracionJuego.class);
        return lista.isEmpty() ? null : lista.get(0);
    }


    

    public void guardarJugador(String nombre, boolean gano, int tiempoPartida) {
        Jugador jugador = obtenerJugador(nombre);

        if (jugador == null) {
            jugador = new Jugador(nombre);
        }

        // Actualizar estadísticas
        if (gano) jugador.ganadas++;
        else jugador.perdidas++;

        jugador.tiempoTotal += tiempoPartida;

        db.store(jugador);
        db.commit();
        System.out.println("Jugador actualizado/guardado: " + jugador);
    }

    public Jugador obtenerJugador(String nombre) {
        Query q = db.query();
        q.constrain(Jugador.class);
        q.descend("nombre").constrain(nombre);

        List<Jugador> lista = q.execute();
        return lista.isEmpty() ? null : lista.get(0);
    }

    public List<Jugador> obtenerJugadores() {
        return db.query(Jugador.class);
    }

    public List<Jugador> obtenerTop10() {
        List<Jugador> jugadores = obtenerJugadores();

        Collections.sort(jugadores, new Comparator<Jugador>() {
            @Override
            public int compare(Jugador a, Jugador b) {
                return Integer.compare(b.ganadas, a.ganadas); // ordenar por más victorias
            }
        });

        return jugadores.size() > 10 ? jugadores.subList(0, 10) : jugadores;
    }

    


    public void guardarPartida(Partida partida) {
        db.store(partida);
        db.commit();
        System.out.println("Partida registrada: " + partida);
    }

    public List<Partida> obtenerHistorial() {
        return db.query(Partida.class);
    }



    public void cerrar() {
        if (db != null) {
            db.close();
            System.out.println("Base de datos cerrada.");
        }
    }
}
