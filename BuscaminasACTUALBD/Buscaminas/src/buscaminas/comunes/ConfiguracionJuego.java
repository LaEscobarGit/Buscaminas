package buscaminas.comunes;

import java.util.Date;

public class ConfiguracionJuego {
    private String dificultad;
    private int filas;
    private int columnas;
    private int minas;
    private Date fecha;

    public ConfiguracionJuego() {}

    public ConfiguracionJuego(String dificultad, int filas, int columnas, int minas) {
        this.dificultad = dificultad;
        this.filas = filas;
        this.columnas = columnas;
        this.minas = minas;
        this.fecha = new Date();
    }

    public String getDificultad() { return dificultad; }
    public int getFilas() { return filas; }
    public int getColumnas() { return columnas; }
    public int getMinas() { return minas; }
    public Date getFecha() { return fecha; }
}
