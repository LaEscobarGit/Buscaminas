using Godot;
using System;
using System.Threading.Tasks;

public partial class Historia : Control
{
	[Export] private PanelContainer burbuja;
	[Export] private Texto texto;
	[Export] private Label nombre;
	[Export] private TextureRect spritePersonaje;
	[Export] private ColorRect Portal;
	[Export] AudioStream[] canciones;
	[Export] AudioStreamPlayer musica;
	[Export] TextureRect fondo;
	[Export] Texture2D[] fondos;
	
	private DialogManager dialogManager;
	private GameManager gameManager;
	private string personajeActual = "";
	Godot.Collections.Dictionary sprites = new(){
		{ "celda", GD.Load<Texture2D>("res://assets/imgs/celda.png") },
		{ "banderella", GD.Load<Texture2D>("res://assets/imgs/banderella.png") },
		{ "velaria", GD.Load<Texture2D>("res://assets/imgs/velaria.png") },
		{ "reina detonia", GD.Load<Texture2D>("res://assets/imgs/detonia.png") }
	};
	
	private Tween tweenSprite;
	
	public override void _Ready()
	{
		dialogManager = GetNode<DialogManager>("/root/DialogManager");
		gameManager = GetNode<GameManager>("/root/GameManager");
		var material = spritePersonaje.Material as ShaderMaterial;
		material?.SetShaderParameter("progress", 0.0f);

		dialogManager.DialogoActualizado += onDialogoActualizado;
		dialogManager.DialogoTerminado += onDialogoTerminado;
		dialogManager.PersonajeSale += onPersonajeSale;
		dialogManager.PersonajeEntra += onPersonajeEntra;
		
		dialogManager.iniciarDialogo(GameManager.dialogoActual);
		actualizarPresentacion();
		
		GameManager.clearBuffsDebuffs();
	}
	
	public override void _ExitTree(){
		dialogManager.DialogoActualizado -= onDialogoActualizado;
		dialogManager.DialogoTerminado -= onDialogoTerminado;
		dialogManager.PersonajeSale -= onPersonajeSale;
		dialogManager.PersonajeEntra -= onPersonajeEntra;
	}

	public override void _Process(double delta)
	{
		if(Input.IsActionJustPressed("avanzar")){
			if (texto.estaEscribiendo())
				texto.completarTexto();
			else
				dialogManager.avanzar();
		}
	}
	
	
	public async void onDialogoActualizado(String quien, String textoDialogo){
		burbuja.Visible = true;
		nombre.Text = quien;
		texto.mostrarTexto(textoDialogo);
		if (textoDialogo.Contains("Uhhh")){
			Portal.Visible = true;
			await animarDistorsion(0.25f, 2.0f, 0.8f);
		}
		else if (textoDialogo.Contains("WOWOWOW")){
			await animarDistorsion(0.65f, 9.0f, 0.4f);
		}
		else if (textoDialogo.Contains("AHHH")){
			await animarDistorsion(1.0f, 20.0f, 0.2f);
		}
	}

	public async void onDialogoTerminado(){
		burbuja.Visible = false;
		string actual = GameManager.getDialogoActual();
		switch(actual){
			case "introduccion": 
				await animarDistorsion(0.0f, 0.0f, 1.2f);
				fondo.Texture = fondos[0];
				Portal.Visible = false;
				GameManager.setDialogoActual("numeria");
				dialogManager.iniciarDialogo("numeria");
				musica.Stream = canciones[0];
				musica.Play();
				break;
			case "numeria": GameManager.setDialogoActual("region1_antes");
				GetTree().ChangeSceneToFile("res://scenes/mapa.tscn");
				break;
			case "region1_antes": GameManager.setDialogoActual("region1_despues");
				fondo.Texture = fondos[2];
				GameManager.setJuego(10,10,20,200,3);
				GameManager.setDebuff(0);
				break;
			case "region1_despues": GameManager.setDialogoActual("region2_antes");
				fondo.Texture = fondos[3];
				GameManager.completarNivel(0);
				break;
			case "region2_antes": GameManager.setDialogoActual("region2_despues");
				fondo.Texture = fondos[4];
				GameManager.setJuego(12,12,30,200,3);
				GameManager.setDebuff(1);
				break;
			case "region2_despues": GameManager.setDialogoActual("region3_antes");
				fondo.Texture = fondos[5];
				GameManager.completarNivel(1);
				break;
			case "region3_antes": GameManager.setDialogoActual("region3_despues");
				fondo.Texture = fondos[6];
				GameManager.setJuego(14,14,40,150,3);
				GameManager.setDebuff(2);
				break;
			case "region3_despues":
				GameManager.completarNivel(2);
				GetTree().ChangeSceneToFile("res://scenes/final.tscn");
				return;
		}
		if(actual.Contains("antes"))
			GetTree().ChangeSceneToFile("res://scenes/seleccion.tscn");
		else if(actual.Contains("despues"))
			GetTree().ChangeSceneToFile("res://scenes/mapa.tscn");
	}
	
	private void actualizarPresentacion(){
		string actual = GameManager.getDialogoActual();
		switch (actual){
			case "introduccion": break;
			case "numeria": fondo.Texture = fondos[0];
				musica.Stream = canciones[1];
				break;
			case "region1_antes": fondo.Texture = fondos[1];
				musica.Stream = canciones[2];
				break;
			case "region1_despues": fondo.Texture = fondos[2];
				musica.Stream = canciones[1];
				break;
			case "region2_antes": fondo.Texture = fondos[3];
				musica.Stream = canciones[2];
				break;
			case "region2_despues": fondo.Texture = fondos[4];
				musica.Stream = canciones[1];
				break;
			case "region3_antes": fondo.Texture = fondos[5];
				musica.Stream = canciones[2];
				break;
			case "region3_despues": fondo.Texture = fondos[6];
				musica.Stream = canciones[3];
				break;
		}

		musica.Play();
	}
	
	public async void onPersonajeSale(string personaje){
		string saliendo = personaje.ToLower();
		if (personajeActual != saliendo) return;

		await desvanecer();

		if (personajeActual != saliendo) return;
		spritePersonaje.Visible = false;
		spritePersonaje.Texture = null;
		personajeActual = "";
	}

	public async void onPersonajeEntra(string personaje){
		personajeActual = personaje.ToLower();
		spritePersonaje.Texture = sprites[personajeActual].As<Texture2D>();
		await aparecer();
	}

	public async Task aparecer(){
		spritePersonaje.Visible = true;
		var material = spritePersonaje.Material as ShaderMaterial;
		material?.SetShaderParameter("progress", 0.0f);

		tweenSprite?.Kill();
		tweenSprite = CreateTween();
		tweenSprite.TweenMethod(Callable.From<float>(v =>
			material?.SetShaderParameter("progress", v)), 0.0f, 1.0f, 0.5);
		await ToSignal(tweenSprite, Tween.SignalName.Finished);
	}

	public async Task desvanecer(){
		if (!spritePersonaje.Visible) return;

		var material = spritePersonaje.Material as ShaderMaterial;
		tweenSprite?.Kill();
		tweenSprite = CreateTween();
		tweenSprite.TweenMethod(Callable.From<float>(v =>
			material?.SetShaderParameter("progress", v)), 1.0f, 0.0f, 0.5);
		await ToSignal(tweenSprite, Tween.SignalName.Finished);
	}
	//---- FIN CAMBIAR SPRITE ----
	
	
	//distorsion en la introduccion
	private async Task animarDistorsion(float intensidad, float velocidad, float duracion){
		ShaderMaterial material = (ShaderMaterial)Portal.Material;

		Tween tween = CreateTween();
		tween.SetParallel(true);
		tween.TweenProperty(material, "shader_parameter/intensidad", intensidad, duracion);
		tween.TweenProperty(material, "shader_parameter/velocidad",	velocidad, duracion);

		await ToSignal(tween, Tween.SignalName.Finished);
	}
}
