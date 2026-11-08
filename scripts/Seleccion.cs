using Godot;
using System;

public partial class Seleccion : Control
{
	[Export] ItemList selector;
	[Export] TextureRect sprite;
	[Export] Label nombre;
	[Export] Label desc;
	[Export] Label buff;
	[Export] Label buffDesc;
	[Export] TextureRect imgBuff;
	
	private string seleccionada = "";
	Godot.Collections.Dictionary sprites = new(){
		{ "Celda", GD.Load<Texture2D>("res://assets/imgs/celda.png") },
		{ "Banderella", GD.Load<Texture2D>("res://assets/imgs/banderella.png") },
		{ "Velaria", GD.Load<Texture2D>("res://assets/imgs/velaria.png") }
	};
	private Godot.Collections.Dictionary datos;
	
	public override void _Ready()
	{
		using FileAccess archivo = FileAccess.Open("res://assets/data/datos.json",FileAccess.ModeFlags.Read);
		if(archivo!=null){
			datos = Json.ParseString(archivo.GetAsText()).AsGodotDictionary();
		}
		
		foreach (Node nodo in GetTree().GetNodesInGroup("botones_menu"))
		{
			if (nodo is Boton boton)
				boton.Click += OnBotonClick;
		}
		selector.ItemSelected += onItemSelected;
		selector.SetItemDisabled(1, !GameManager.jugados[0]);
		selector.SetItemDisabled(2, !GameManager.jugados[1]);
		
		onItemSelected(0l);
	}

	public override void _Process(double delta)
	{
	}
	
	private void OnBotonClick(Boton boton)
	{
		if(boton.Name.Equals("Seleccionar")){
			switch(seleccionada){
				case "Celda": GameManager.setBuff(0);
					break;
				case "Velaria": GameManager.setBuff(1);
					break;
				case "Banderella": GameManager.setBuff(2);
					break;
			}
		}
		GetTree().ChangeSceneToFile("res://scenes/game_screen.tscn");
	}
	
	private void onItemSelected(long index){
		switch((int)index){
			case 0: seleccionada = "Celda";
				break;
			case 1: seleccionada = "Velaria";
				break;
			case 2: seleccionada = "Banderella";
				break;
			default: seleccionada = "";
				return;
		}
		GD.Print(seleccionada);
		sprite.Texture = sprites[seleccionada].As<Texture2D>();
		var personaje = datos[seleccionada].AsGodotDictionary();
		nombre.Text = personaje["nombre"].AsString();
		desc.Text = personaje["descripcion"].AsString();
		buff.Text = personaje["buff"].AsString();
		buffDesc.Text = personaje["descBuff"].AsString();
		imgBuff.Texture = GD.Load<Texture2D>(personaje["imgBuff"].AsString());
	}
}
