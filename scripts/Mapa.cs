using Godot;
using System;

public partial class Mapa : Control
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
	
	private void OnBotonClick(Boton boton)
	{
		switch (boton.Name)
		{
			case "Regresar":
				GetTree().ChangeSceneToFile("res://scenes/menu.tscn");
				break;
		}
	}
}
