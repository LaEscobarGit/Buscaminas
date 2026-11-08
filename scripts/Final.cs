using Godot;
using System;

public partial class Final : Control
{
	[Export] private Button volverButton;

	public override void _Ready(){
		volverButton.Pressed += OnVolverPressed;
	}

	private void OnVolverPressed(){
		GetTree().ChangeSceneToFile("res://scenes/menu.tscn");
	}
}
