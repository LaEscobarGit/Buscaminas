package buscaminas.recursos;

public class Tablero {
    private String nombre;
    private int filas;
    private int columnas;
    private int minas;

    public Tablero(){}
    public Tablero(String nombre, int filas, int columnas, int minas) {
        this.nombre = nombre;
        this.filas = filas;
        this.columnas = columnas;
        this.minas = minas;
    }

    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public int getFilas() {
        return filas;
    }
    public void setFilas(int filas) {
        this.filas = filas;
    }
    public int getColumnas() {
        return columnas;
    }
    public void setColumnas(int columnas) {
        this.columnas = columnas;
    }
    public int getMinas() {
        return minas;
    }
    public void setMinas(int minas) {
        this.minas = minas;
    }
}

