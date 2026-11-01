using Godot;
using System;

public partial class ConfigJuego : Control
{
	[Signal] public delegate void ClickEventHandler(Boton boton);
	[Export] private SpinBox filasBox;
	[Export] private SpinBox colsBox;
	[Export] private SpinBox minasBox;
	
	public override void _Ready()
	{
		foreach (Node nodo in GetTree().GetNodesInGroup("botones_menu"))
		{
			if (nodo is Boton boton)
				boton.Click += OnBotonClick;
		}
	}
	
	private void OnBotonClick(Boton boton)
	{
		switch (boton.Name)
		{
			case "Tablero"://
				break;
			case "B&D":
				//
				break;
			case "¡A Jugar!":
				GameManager.filas = (int) filasBox.Value;
				GameManager.columnas = (int) colsBox.Value;
				GameManager.minas = (int) minasBox.Value;
				int espacios = (GameManager.filas*GameManager.columnas)-10;
				if(GameManager.minas < espacios)
					GetTree().ChangeSceneToFile("res://scenes/game_screen.tscn");
				else{
					//
				}
				break;
			case "Extras":
				
				break;
		}
	}
}
