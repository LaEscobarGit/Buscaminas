package buscaminas.comunes;

import com.db4o.*;
import com.db4o.query.Query;
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
        
        List<ConfiguracionJuego> previas = db.query(ConfiguracionJuego.class);
        for (ConfiguracionJuego c : previas) db.delete(c);

        db.store(config);
        db.commit();
        System.out.println("Configuración guardada: " + config.getDificultad());
    }

    public ConfiguracionJuego obtenerConfiguracion() {
        List<ConfiguracionJuego> lista = db.query(ConfiguracionJuego.class);
        return lista.isEmpty() ? null : lista.get(0);
    }


    public void guardarJugador(String nombre, boolean gano, int puntaje, int tiempoPartida) {
        JugadorStats jugador = obtenerJugador(nombre);

        if (jugador == null) {
            jugador = new JugadorStats(nombre);
        }

        jugador.actualizarEstadisticas(gano, puntaje, tiempoPartida);

        db.store(jugador);
        db.commit();
        System.out.println("Jugador actualizado/guardado: " + jugador.getNombre());
    }

    public JugadorStats obtenerJugador(String nombre) {
        Query q = db.query();
        q.constrain(JugadorStats.class);
        q.descend("nombre").constrain(nombre);

        List<JugadorStats> lista = q.execute();
        return lista.isEmpty() ? null : lista.get(0);
    }

    public List<JugadorStats> obtenerJugadores() {
        return db.query(JugadorStats.class);
    }

    public List<JugadorStats> obtenerTop10() {
        List<JugadorStats> jugadores = obtenerJugadores();

        Collections.sort(jugadores, new Comparator<JugadorStats>() {
            @Override
            public int compare(JugadorStats a, JugadorStats b) {
                return Integer.compare(b.getMejorPuntaje(), a.getMejorPuntaje());
            }
        });

        return jugadores.size() > 10 ? jugadores.subList(0, 10) : jugadores;
    }


    public void guardarPartida(HistorialPartida partida) {
        db.store(partida);
        db.commit();
        System.out.println("Partida registrada de: " + partida.getJugador());
    }

    public List<HistorialPartida> obtenerHistorial() {
        return db.query(HistorialPartida.class);
    }


    public void cerrar() {
        if (db != null) {
            db.close();
            System.out.println("Base de datos cerrada.");
        }
    }
}