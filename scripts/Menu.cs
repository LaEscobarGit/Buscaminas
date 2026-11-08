using Godot;
using System;

public partial class Menu : Control
{
	[Export] private Control opciones;
	[Export] private HSlider sliderMaster;
	[Export] private HSlider sliderMusica;
	[Export] private HSlider sliderSFX;
	[Export] private Label labelMaster;
	[Export] private Label labelMusica;
	[Export] private Label labelSFX;
	[Export] private ColorRect blur;
	[Signal] public delegate void ClickEventHandler(Boton boton);
	
	private ShaderMaterial material;
	
	public override void _Ready()
	{
		foreach (Node nodo in GetTree().GetNodesInGroup("botones_menu"))
		{
			if (nodo is Boton boton)
				boton.Click += OnBotonClick;
		}
		sliderMaster.ValueChanged += onSliderMasterValueChanged;
		sliderMusica.ValueChanged += onSliderMusicaValueChanged;
		sliderSFX.ValueChanged += onSliderSFXValueChanged;
		
		material = blur.Material as ShaderMaterial;
	}
	
	private void OnBotonClick(Boton boton)
	{
		Tween entrada = CreateTween().SetTrans(Tween.TransitionType.Sine).SetEase(Tween.EaseType.Out);
		switch (boton.Name)
		{
			case "Aventura":
				GameManager.modoAventura = true;
				GetTree().ChangeSceneToFile("res://scenes/profecia.tscn");
				break;
			case "Solo":
				GameManager.modoAventura = false;
				GetTree().ChangeSceneToFile("res://scenes/configJuego.tscn");
				break;
			case "Puntajes":
				GetTree().ChangeSceneToFile("res://scenes/ranking.tscn");
				break;
			case "Opciones":
				opciones.Visible = true;
				blur.Visible = true;
				material.SetShaderParameter("lod", 0.0f);
				entrada.TweenProperty(this, "material:shader_parameter/lod", 3.0f, 1.0f);
				break;
			case "Cerrar":
				opciones.Visible = false;
				blur.Visible = false;
				break;
			case "Salir":
				GetTree().Quit();
				break;
		}
	}
	
	private void onSliderMasterValueChanged(double value){
		AudioServer.SetBusVolumeDb(AudioServer.GetBusIndex("Master"), Mathf.LinearToDb(Mathf.Max((float)value / 100f, 0.0001f)));
		labelMaster.Text = Math.Round(value).ToString();
	}
	private void onSliderSFXValueChanged(double value){
		AudioServer.SetBusVolumeDb(AudioServer.GetBusIndex("SFX"), Mathf.LinearToDb(Mathf.Max((float)value / 100f, 0.0001f)));
		labelSFX.Text = Math.Round(value).ToString();
	}
	private void onSliderMusicaValueChanged(double value){
		AudioServer.SetBusVolumeDb(AudioServer.GetBusIndex("Musica"), Mathf.LinearToDb(Mathf.Max((float)value / 100f, 0.0001f)));
		labelMusica.Text = Math.Round(value).ToString();
	}
}
