package buscaminas;

import com.db4o.*;
import com.db4o.query.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class BaseDatosBuscaminas {

    private static final String ARCHIVO_DB = "DatosJugadores.db4o";
    private ObjectContainer db;

    // Constructor: abre la base de datos
    public BaseDatosBuscaminas() {
        db = Db4o.openFile(ARCHIVO_DB); // Compatible con db4o 7.4
    }

    // Clase interna Jugador
    public static class Jugador {
        private String nombre;
        private int puntaje;
        private String fechaHora;

        public Jugador() {
            // Constructor vacío necesario para db4o
        }

        public Jugador(String nombre, int puntaje) {
            this.nombre = nombre;
            this.puntaje = puntaje;
            this.fechaHora = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date());
        }

        public String getNombre() {
            return nombre;
        }

        public int getPuntaje() {
            return puntaje;
        }

        public String getFechaHora() {
            return fechaHora;
        }

        @Override
        public String toString() {
            return "Jugador: " + nombre + " | Puntaje: " + puntaje + " | Fecha: " + fechaHora;
        }
    }

    // Guarda un jugador nuevo o actualiza si ya existe con mejor puntaje
    public void guardarJugador(String nombre, int puntaje) {
        Query query = db.query();
        query.constrain(Jugador.class);
        query.descend("nombre").constrain(nombre);
        @SuppressWarnings("unchecked")
        List<Jugador> resultados = query.execute();

        if (!resultados.isEmpty()) {
            Jugador existente = resultados.get(0);
            if (puntaje > existente.getPuntaje()) {
                db.delete(existente);
                db.store(new Jugador(nombre, puntaje));
                System.out.println("✔ Puntaje actualizado para " + nombre);
            } else {
                System.out.println("⚠ El puntaje no superó el anterior. No se actualizó.");
            }
        } else {
            db.store(new Jugador(nombre, puntaje));
            System.out.println("✔ Jugador guardado: " + nombre);
        }

        db.commit();
    }

    // Devuelve todos los jugadores
    public List<Jugador> obtenerJugadores() {
        Query query = db.query();
        query.constrain(Jugador.class);
        @SuppressWarnings("unchecked")
        List<Jugador> jugadores = query.execute();
        return jugadores;
    }

    // Devuelve el Top 10 de jugadores por puntaje
    public List<Jugador> obtenerTop10() {
        List<Jugador> jugadores = obtenerJugadores();

        // Crear lista editable
        List<Jugador> listaEditable = new ArrayList<>();
        for (Jugador j : jugadores) {
            listaEditable.add(j);
        }

        // Ordenar por puntaje descendente
        Collections.sort(listaEditable, new Comparator<Jugador>() {
            @Override
            public int compare(Jugador a, Jugador b) {
                return Integer.compare(b.getPuntaje(), a.getPuntaje());
            }
        });

        // Retornar solo los 10 mejores
        if (listaEditable.size() > 10) {
            return new ArrayList<>(listaEditable.subList(0, 10));
        } else {
            return listaEditable;
        }
    }

    // Cierra la base de datos
    public void cerrar() {
        if (db != null) {
            db.close();
        }
    }

   /*  // --- Ejemplo de uso ---
    public static void main(String[] args) {
        BaseDatosBuscaminas bd = new BaseDatosBuscaminas();

        bd.guardarJugador("Roberto", 1200);
        bd.guardarJugador("Natalia", 980);
        bd.guardarJugador("Miguel", 1500);

        System.out.println("\n📋 Todos los jugadores:");
        for (Jugador j : bd.obtenerJugadores()) {
            System.out.println(j);
        }

        System.out.println("\n🏆 Top 10 jugadores:");
        for (Jugador j : bd.obtenerTop10()) {
            System.out.println(j);
        }

        bd.cerrar();
    }*/
}
