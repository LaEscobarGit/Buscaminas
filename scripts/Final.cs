using Godot;
using System;

public partial class Final : Control
{
	[Export] private Button volverButton;

	public override void _Ready(){
		volverButton.Pressed += OnVolverPressed;
	}

	private async void OnVolverPressed(){
		await Transicion.instancia.cambiarEscena("res://scenes/menu.tscn");
	}
}
