using Godot;
using System;
using System.Collections.Generic;
using System.IO;

public partial class GuardarPuntaje : AcceptDialog
{
	[Export] private LineEdit campoNombre;

	private bool ganoPendiente;
	private int puntosPendientes;
	private bool yaGuardado;

	public override void _Ready()
	{
		GameScreen pantalla = GetParent<GameScreen>();
		pantalla.JuegoTerminado += OnJuegoTerminado;

		RegisterTextEnter(campoNombre);

		Confirmed += GuardarYSalir;
		Canceled += GuardarYSalir;
	}

	private void OnJuegoTerminado(bool gano, int puntos){
		if (puntos < 0)
			puntos = 0;

		ganoPendiente = gano;
		puntosPendientes = puntos;
		yaGuardado = false;

		campoNombre.Text = "";
		PopupCentered(new Vector2I(400, 130));
		campoNombre.GrabFocus();
	}
	//Esto tambien es lo del nombre 
	private void GuardarYSalir()
	{
		if (yaGuardado)
			return;
		yaGuardado = true;

		string nombre = campoNombre.Text.Trim();
		if (nombre == "")
			nombre = "Anonimo";

		PuntajeStorage.JugadorActual = nombre;

		Puntaje p = new Puntaje();
		p.Jugador = nombre;
		p.Puntos = puntosPendientes;
		p.Gano = ganoPendiente;
		p.Filas = GameManager.filas;
		p.Cols = GameManager.columnas;
		p.Minas = GameManager.minas;

		PuntajeStorage.Guardar(p);

		GetTree().ChangeSceneToFile("res://scenes/menu.tscn");
	}
}

public class Puntaje
{
	public string Jugador;
	public int Puntos;
	public bool Gano;
	public int Filas;
	public int Cols;
	public int Minas;
}

public class PuntajeStorage
{
	public static string JugadorActual = "Anonimo";

	private static string Ruta = ProjectSettings.GlobalizePath("user://puntajes.txt");

	public static void Guardar(Puntaje p)
	{
		string gano = "No";
		if (p.Gano)
			gano = "Si";

		string nombre = p.Jugador.Replace("|", "");

		string linea = nombre + "|" + p.Puntos + "|" + gano
			+ "|" + p.Filas + "|" + p.Cols + "|" + p.Minas;

		StreamWriter fichero;

		fichero = File.AppendText(Ruta);
		fichero.WriteLine(linea);
		fichero.Close();
	}

	public static List<Puntaje> LeerTodos()
	{
		List<Puntaje> lista = new List<Puntaje>();

		if (!File.Exists(Ruta))
			return lista;

		StreamReader fichero;
		string linea;

		fichero = File.OpenText(Ruta);
		linea = fichero.ReadLine();


		while (linea != null)
		{
			string[] datos = linea.Split('|');

			if (datos.Length == 6)
			{
				Puntaje p = new Puntaje();
				p.Jugador = datos[0];
				p.Puntos = int.Parse(datos[1]);
				p.Gano = (datos[2] == "Si");
				p.Filas = int.Parse(datos[3]);
				p.Cols = int.Parse(datos[4]);
				p.Minas = int.Parse(datos[5]);
				lista.Add(p);
			}

			linea = fichero.ReadLine();
		}

		fichero.Close();
		return lista;
	}

	public static List<Puntaje> RankingGeneral()
	{
		List<Puntaje> todos = LeerTodos();
		List<Puntaje> ranking = new List<Puntaje>();

		for (int i = 0; i < todos.Count; i++)
		{
			//if (todos[i].Gano)
				ranking.Add(todos[i]);
		}


		for (int i = 0; i < ranking.Count - 1; i++)
		{
			for (int j = 0; j < ranking.Count - 1 - i; j++)
			{
				if (ranking[j].Puntos < ranking[j + 1].Puntos)
				{
					Puntaje temp = ranking[j];
					ranking[j] = ranking[j + 1];
					ranking[j + 1] = temp;
				}
			}
		}
		return ranking;
	}
}
