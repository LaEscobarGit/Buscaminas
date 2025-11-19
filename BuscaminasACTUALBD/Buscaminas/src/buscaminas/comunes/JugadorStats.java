package buscaminas.comunes;

public class JugadorStats {
    private String nombre;
    private int mejorPuntaje;
    private int partidasJugadas;
    private int partidasGanadas;
    private int partidasPerdidas;
    private int tiempoTotal;

    public JugadorStats() {}

    public JugadorStats(String nombre) {
        this.nombre = nombre;
        this.mejorPuntaje = 0;
    }

    public String getNombre() { 
        return nombre; 
    }
    public int getMejorPuntaje() { 
        return mejorPuntaje; 
    }
    public int getPartidasJugadas() { 
        return partidasJugadas; 
    }
    public int getPartidasGanadas() { 
        return partidasGanadas; 
    }
    public int getPartidasPerdidas() { 
        return partidasPerdidas; 
    }
    public int getTiempoTotal() { 
        return tiempoTotal;
     }

    public void actualizarEstadisticas(boolean gano, int puntaje, int tiempo) {
        partidasJugadas++;
        tiempoTotal += tiempo;

        if (gano) partidasGanadas++;
        else partidasPerdidas++;

        if (puntaje > mejorPuntaje)
            mejorPuntaje = puntaje;
    }
}
