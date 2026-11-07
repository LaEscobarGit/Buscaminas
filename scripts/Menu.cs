using Godot;
using System;

public partial class Menu : Control
{
	[Export] private Control opciones;
	[Signal] public delegate void ClickEventHandler(Boton boton);
	
	public override void _Ready()
	{
		foreach (Node nodo in GetTree().GetNodesInGroup("botones_menu"))
		{
			if (nodo is Boton boton)
				boton.Click += OnBotonClick;
		}
	}
	
	public override void _Process(double delta)
	{
	}
	
	private void OnBotonClick(Boton boton)
	{
		switch (boton.Name)
		{
			case "Aventura":
				GetTree().ChangeSceneToFile("res://scenes/profecia.tscn");
				break;
			case "Solo":
				GetTree().ChangeSceneToFile("res://scenes/configJuego.tscn");
				break;
			case "Puntajes":
				GetTree().ChangeSceneToFile("res://scenes/ranking.tscn");
				break;
			case "Opciones":
				opciones.Visible = true;
				break;
			case "Cerrar":
				opciones.Visible = false;
				break;
			case "Exit":
				GetTree().Quit();
				break;
		}
	}
}
