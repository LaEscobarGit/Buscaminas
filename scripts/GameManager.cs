using Godot;
using System;

public partial class GameManager : Node
{
	public static int filas;
	public static int columnas;
	public static int minas;
	public static int tiempo;
	public static int vidas;
	public static Godot.Collections.Array buffs = new();
	public static Godot.Collections.Array debuffs = new();
	
	public static bool[] jugados = {false, false, false};
	public static int actual = 0;
	
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
	
	public static int getActual(){
		return actual;
	}
	
	public static void setJuego(int f, int c, int m, int t, int v){
		filas = f;
		columnas = c;
		minas = m;
		tiempo = t;
		vidas = v;
	}
	
	public static void setBuff(int b){
		buffs.Add(b);
		if(buffs.Contains(0))
			tiempo += 50;
	}
	
	public static void setDebuff(int db){
		debuffs.Add(db);
		if(debuffs.Contains(2))
			vidas = 1;
	}
}
