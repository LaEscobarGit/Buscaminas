using Godot;
using System;

public partial class ConfigJuego : Control
{
	[Signal] public delegate void ClickEventHandler(Boton boton);
	[Export] private SpinBox filasBox;
	[Export] private SpinBox colsBox;
	[Export] private SpinBox minasBox;
	[Export] private Control textoTablero;
	[Export] private Control tableroContainer;
	[Export] private Control textoBD;
	[Export] private Control bdContainer;
	[Export] private CheckBox limiteCheck;
	[Export] private Control tiempoLabel;
	[Export] private SpinBox tiempoSpin;
	
	private bool limite;
	
	public override void _Ready()
	{
		foreach (Node nodo in GetTree().GetNodesInGroup("botones_menu"))
		{
			if (nodo is Boton boton)
				boton.Click += OnBotonClick;
		}
		
		limiteCheck.Toggled += onLimiteToggled;
		MostrarPestana(true);
	}
	
	private void MostrarPestana(bool tablero)
	{
	textoTablero.Visible = tablero;
	tableroContainer.Visible = tablero;
	textoBD.Visible = !tablero;
	bdContainer.Visible = !tablero;
	}
	
	private void OnBotonClick(Boton boton)
	{
		switch (boton.Name)
		{
			case "Tablero":
				MostrarPestana(true);
				break;
			case "B&D":
				MostrarPestana(false);
				break;
			case "¡A Jugar!":
				GameManager.filas = (int) filasBox.Value;
				GameManager.columnas = (int) colsBox.Value;
				GameManager.minas = (int) minasBox.Value;
				GameManager.vidas = 3;
				if(limite)
					GameManager.tiempo = (int) tiempoSpin.Value;
				else
					GameManager.tiempo = 0;
				int espacios = (GameManager.filas*GameManager.columnas)-10;
				if(GameManager.minas < espacios)
					GetTree().ChangeSceneToFile("res://scenes/game_screen.tscn");
				else{
					//
				}
				break;
			case "Extras":
				
				break;
			case "Regresar":
				GetTree().ChangeSceneToFile("res://scenes/menu.tscn");
				break;
		}
	}
	
	public void onLimiteToggled(bool toggledOn){
		tiempoLabel.Visible = toggledOn;
		tiempoSpin.Visible = toggledOn;
		limite = toggledOn;
	}
}
