using Godot;
using System;

public partial class ConfigJuego : Control
{
	[Signal] public delegate void ClickEventHandler(Boton boton);
	[Export] private SpinBox filasBox;
	[Export] private SpinBox colsBox;
	[Export] private SpinBox minasBox;
	[Export] private SpinBox vidasBox;
	[Export] private Control textoTablero;
	[Export] private Control tableroContainer;
	[Export] private Control textoBD;
	[Export] private Control bdContainer;
	[Export] private CheckBox limiteCheck;
	[Export] private Control tiempoLabel;
	[Export] private SpinBox tiempoSpin;
	[Export] private Label buffDesc;
	[Export] private Button pista;
	[Export] private Button bandera;
	[Export] private Button incognita;
	[Export] private Button flagless;
	
	private bool limite;
	
	public override void _Ready()
	{
		foreach (Node nodo in GetTree().GetNodesInGroup("botones_menu")){
			if (nodo is Boton boton)
				boton.Click += OnBotonClick;
		}
		foreach (Node nodo in GetTree().GetNodesInGroup("botones_buff")){
			if (nodo is Button boton){
				boton.MouseEntered += () => onBuffMouseEntered(boton);
				boton.MouseExited += () => onBuffMouseExited(boton);
			}
		}
		
		pista.Toggled += onPistaToggled;
		bandera.Toggled += onBanderaToggled;
		incognita.Toggled += onIncognitaToggled;
		flagless.Toggled += onFlaglessToggled;
		
		limiteCheck.Toggled += onLimiteToggled;
		onLimiteToggled(false);
		MostrarPestana(true);
		
		GameManager.clearBuffsDebuffs();
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
				GameManager.setJuego((int) filasBox.Value,(int) colsBox.Value,(int) minasBox.Value,0,(int) vidasBox.Value);
				if(limite)
					GameManager.tiempo = (int) tiempoSpin.Value;
				int espacios = (GameManager.filas*GameManager.columnas)-10;
				if(GameManager.minas < espacios)
					GetTree().ChangeSceneToFile("res://scenes/game_screen.tscn");
				else{
					//
				}
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
	
	//botones buff y debuff
	public void onPistaToggled(bool toggledOn){
		if(toggledOn)
			GameManager.setBuff(1);
		else
			GameManager.removeBuff(1);
	}
	public void onBanderaToggled(bool toggledOn){
		if(toggledOn){
			flagless.Disabled = true;
			GameManager.setBuff(2);
		}else{
			flagless.Disabled = false;
			GameManager.removeBuff(2);
		}
	}
	public void onIncognitaToggled(bool toggledOn){
		if(toggledOn)
			GameManager.setDebuff(0);
		else
			GameManager.removeDebuff(0);
	}
	public void onFlaglessToggled(bool toggledOn){
		if(toggledOn){
			bandera.Disabled = true;
			GameManager.setDebuff(1);
		}else{
			bandera.Disabled = false;
			GameManager.removeDebuff(1);
		}
	}
	public void onBuffMouseEntered(Button boton){
		switch(boton.Name){
			case "CeldasPista":
				buffDesc.Text = "+3 cargas de pistas. Haz click en la celda deseada para descubrir su contenido. En caso de contener una mina, no se recibe daño.";
				break;
			case "BanderaEscudo":
				buffDesc.Text = "+3 cargas de Bandera Protectora. Si revelas una mina con una bandera en las celdas de alrededor, no sufres daño.";
				break;
			case "CeldasMisteriosas":
				buffDesc.Text = "Algunas celdas reveladas aparecerán con una incógnita (?) en vez de el número de minas adyacentes.";
				break;
			case "Flagless":
				buffDesc.Text = "Deshabilita las banderas.";
				break;
		}
	}
	public void onBuffMouseExited(Button boton){
		buffDesc.Text = "";
	}
}
