using Godot;
using System;

public partial class Menu : Control
{
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
			case "Jugar":
				GetTree().ChangeSceneToFile("res://scenes/configJuego.tscn");
				break;
			case "Puntajes":
				//
				break;
			case "Opciones":
				//
				break;
			case "Exit":
				GetTree().Quit();
				break;
		}
	}
}
