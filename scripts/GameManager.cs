using Godot;
using System;

public partial class GameManager : Node
{
	public static int filas;
	public static int columnas;
	public static int minas;
	public static int tiempo;
	public static int vidas;
	public static string buff;
	public static string debuff;
	
	public static bool[] jugados = {false, false, false};
	public static bool actual = 0;
	
	public static string dialogoActual = "introduccion";
	
	public static void completarNivel(int region){
		if(region == -1) return;
		jugados[region] = true;
		actual = region+1;
	}
	
	public static void setDialogoActual(string actual){
		dialogoActual = actual;
	}
	
	public static string getDialogoActual(){
		return dialogoActual;
	}
	
	public static bool getHabilitado(int indice){
		if(jugados[indice]==false)
			if(indice == 0)
				return true;
			return jugados[indice-1];
		return false;
	}
	
	public static bool getActual(){
		return actual;
	}
	
	public static void setJuego(int filas, int cols, int minas, int tiempo, int vidas){
		this.filas = filas;
		this.columnas = cols;
		this.minas = minas;
		
		if(buff.Equals("tiempo"))
			this.tiempo = tiempo + 50;
		else
			this.tiempo = tiempo;
		
		if(debuff.Equals("muerteSubita"))
			this.vidas = 1;
		else
			this.vidas = vidas;
	}
}
