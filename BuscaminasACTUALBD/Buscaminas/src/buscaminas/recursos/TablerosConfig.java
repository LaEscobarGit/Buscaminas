package buscaminas.recursos;

import java.util.List;

public class TablerosConfig {
    List<Tablero> tableros;
    
    public TablerosConfig(){}
    
    public List<Tablero> getTableros(){
        return tableros;
    }
    public void setTableros(List<Tablero> tableros){
        this.tableros = tableros;
    }
    
    public Tablero buscarPorNombre(String nombre){
        for (Tablero t : tableros) {
            if (t.getNombre().equals(nombre)){
                return t;
            }
        }
        return null;
    }
}
