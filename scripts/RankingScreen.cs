using Godot;
using System;
using System.Collections.Generic;

public partial class RankingScreen : Control
{
	private Label listaLabel;
	private Button volverButton;

	public override void _Ready()
	{
		listaLabel = GetNode<Label>("CenterContainer/VBoxContainer/ListaLabel");
		volverButton = GetNode<Button>("CenterContainer/VBoxContainer/VolverButton");


		volverButton.Pressed += OnVolverPressed;

		MostrarRanking();
	}

	private void MostrarRanking()
	{
		List<Puntaje> ranking = PuntajeStorage.RankingGeneral();

		if (ranking.Count == 0)
		{
			listaLabel.Text = "Todavia no hay puntajes";
			return;
		}

		string texto = "";
		int limite = 10;

		if (ranking.Count < limite)
			limite = ranking.Count;

		for (int i = 0; i < limite; i++)
		{
			texto = texto + (i + 1) + ". " + ranking[i].Jugador + "  -  " + ranking[i].Puntos + "\n";
		}

		listaLabel.Text = texto;
	}

	private void OnVolverPressed()
	{
		GetTree().ChangeSceneToFile("res://scenes/menu.tscn");
	}
}
