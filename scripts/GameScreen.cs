using Godot;
using System;

public partial class GameScreen : Control
{
	[Signal] public delegate void JuegoIniciadoEventHandler();
	[Signal] public delegate void VidaPerdidaEventHandler(int vidas);
	[Signal] public delegate void JuegoTerminadoEventHandler(bool gano, int puntos);
 
	[Export] private AudioStreamPlayer sfx;
	[Export] private AudioStreamPlayer musica;
	[Export] private AudioStream SonidoBomba;
	[Export] private TextureRect Vida1;
	[Export] private TextureRect Vida2;
	[Export] private TextureRect Vida3;
	[Export] private AudioStream[] canciones;
	[Export] private Boton botonRendirse;
	[Export] private AcceptDialog dialog;
	[Export] private PanelContainer menuMuerte;
	[Export] private TextureRect[] imgDB;
	[Export] private Button buffPista;
	[Export] private Label pistaLabel;
 
	public int Filas;
	public int Cols;
	public int NumMinas;
	private int VidasIniciales = 3;
	private int Tiempo = 0;
	private Tablero tablero;
	private Timer timer;
	private Label timerLabel;
 
	private int vidas;
	private int clicks;
	private int bvClicks;
	private int puntos;
	private int seg;
 
	private bool juegoIniciado;
	private bool juegoTerminado;
	private ulong inicioMs;
 
	private bool[,] marcado;
 
	//visuales
	private Vector2 tableroOrigen;
	private Tween tweenShake;

	//buffs
	private bool pistaToggle;
	private int cargasPistas = 0;
	private int cargasBandera = 0;
	private int cargasIncognita = 0;

	public override void _Ready()
	{
		tablero = GetNode<Tablero>("Tablero");
		tableroOrigen = tablero.Position;
		timer = GetNode<Timer>("Timer");
		timerLabel = GetNode<Label>("TimerLabel");
		foreach (Node nodo in GetTree().GetNodesInGroup("botones_menu")){
			if (nodo is Boton boton)
				boton.Click += OnBotonClick;
		}
		
		tablero.ClickIzquierdo += OnClickIzquierdo;
		tablero.ClickDerecho += OnClickDerecho;
		Filas = GameManager.filas;
		Cols = GameManager.columnas;
		NumMinas = GameManager.minas;
		Tiempo = GameManager.tiempo;
		VidasIniciales = GameManager.vidas;
		
		botonRendirse.Click += OnRendirseClick;
		
		//musica.Stream = canciones[GameManager.getActual()];
		if(GameManager.buffs.Contains(0)){
			imgDB[0].Visible = true;
		}if(GameManager.buffs.Contains(1)){
			imgDB[1].Visible = true;
			buffPista.Visible = true;
			pistaLabel.Visible = true;
			cargasPistas = 3;
		}if(GameManager.buffs.Contains(2)){
			imgDB[2].Visible = true;
			cargasBandera = 3;
		}if(GameManager.debuffs.Contains(0)){
			imgDB[3].Visible = true;
			cargasIncognita = 8;
		}if(GameManager.debuffs.Contains(1)){
			imgDB[4].Visible = true;
		}if(GameManager.debuffs.Contains(2)){
			imgDB[5].Visible = true;
		}
		
		buffPista.Toggled += onPistaToggled;
		
		configurarVidas();
		
		musica.Stream = canciones[GameManager.getActual()];
		musica.Play();
		
		NuevaPartida();
	}
 
	public void NuevaPartida()
	{
		tablero.Construir(Filas, Cols);
 
		vidas = VidasIniciales;
		clicks = 0;
		bvClicks = 0;
		puntos = 0;
		seg = 0;
		juegoIniciado = false;
		juegoTerminado = false;
	}
 
	// ---------- Entrada del jugador ----------
	private void OnClickIzquierdo(Celda celda)
	{
		if (juegoTerminado || celda.Banderado || celda.Revelado) return;
		if (!juegoIniciado)
			IniciarJuego(celda.Fila, celda.Col);
		RevealCelda(celda.Fila, celda.Col);
		clicks++;
	}
 
	private void OnClickDerecho(Celda celda)
	{
		if (juegoTerminado || !juegoIniciado || celda.Revelado) return;
		if(GameManager.debuffs.Contains(1))
			return;
		
		celda.AlternarBandera();
		clicks++;
	}
 
	// ---------- Inicio de partida ----------
	private void IniciarJuego(int fFila, int fCol)
	{
		var mineMap = new MineMap(fFila, fCol, Filas, Cols);
		mineMap.NewMineMap(NumMinas);
 
		for (int fila = 0; fila < Filas; fila++)
			for (int col = 0; col < Cols; col++)
				tablero.GetCelda(fila, col).NewGame(mineMap.EsMina[fila, col]);
 
		juegoIniciado = true;
		seg = 0;
		timerLabel.Text = "0";
		timer.Start();
 
		EmitSignal(SignalName.JuegoIniciado);
		Calcular3BV();
	}
 
	private int GetSurroundingMines(int srcRow, int srcCol)
	{
		int countMines = 0;
		for (int fila = srcRow - 1; fila <= srcRow + 1; fila++)
		{
			for (int col = srcCol - 1; col <= srcCol + 1; col++)
			{
				if (tablero.EnRango(fila, col) && tablero.GetCelda(fila, col).EsMina)
					countMines++;
			}
		}
		return countMines;
	}
 
	private void RevealCelda(int srcRow, int srcCol)
	{
		Celda celda = tablero.GetCelda(srcRow, srcCol);
		if (celda.Revelado || celda.Banderado || juegoTerminado) return;
 
		if (celda.EsMina){
			celda.RevelarMina();
			if(GameManager.buffs.Contains(2) && cargasBandera > 0){
				cargasBandera--;
				celda.mostrarProteccion();
				return;
			}
			if(GameManager.buffs.Contains(1) && pistaToggle && cargasPistas > 0){
				cargasPistas--;
				pistaLabel.Text = "Cargas: " + cargasPistas.ToString();
				if(cargasPistas == 0){
					buffPista.ButtonPressed = false;
					buffPista.Disabled = true;
				}
				return;
			}
			perderVida();
			return;
		}
 
		int countMines = GetSurroundingMines(srcRow, srcCol);
		bool incognita = false;
		if(GameManager.debuffs.Contains(0) && cargasIncognita > 0 && countMines > 0){
			incognita = GD.Randf() < 0.25f;
			if (incognita)
				cargasIncognita--;
		}
		
		celda.Revelar(countMines,incognita);
		if(GameManager.buffs.Contains(1) && pistaToggle && cargasPistas > 0){
			cargasPistas--;
			pistaLabel.Text = "Cargas: " + cargasPistas.ToString();
			if(cargasPistas == 0){
				buffPista.ButtonPressed = false;
				buffPista.Disabled = true;
			}
			return;
		}
		
		if (countMines == 0){
			for (int fila = srcRow - 1; fila <= srcRow + 1; fila++)
			{
				for (int col = srcCol - 1; col <= srcCol + 1; col++)
				{
					if (tablero.EnRango(fila, col) && !tablero.GetCelda(fila, col).Revelado)
						RevealCelda(fila, col);
				}
			}
		}
 
		if (!juegoTerminado && CompletadoTablero())
			TerminarJuego(true);
	}
 
	public bool CompletadoTablero()
	{
		for (int fila = 0; fila < Filas; fila++)
			for (int col = 0; col < Cols; col++)
			{
				Celda c = tablero.GetCelda(fila, col);
				if (!c.EsMina && !c.Revelado)
					return false;
			}
		return true;
	}
 
	private void TerminarJuego(bool vivir){
		if (juegoTerminado) return;
		juegoTerminado = true;
 		timer.Stop();
 
		CalcularPuntaje();
		if(!GameManager.modoAventura){
			dialog.Visible = true;
			EmitSignal(SignalName.JuegoTerminado, vivir, puntos);
		}else{
			if(vivir)
				GetTree().ChangeSceneToFile("res://scenes/historia.tscn");
			else{
				menuMuerte.Visible = true;
			}
		}
	}
 
	// ---------- 3BV ----------
	private void Calcular3BV()
	{
		marcado = new bool[Filas, Cols];
		bvClicks = 0;
 
		for (int fila = 0; fila < Filas; fila++)
		{
			for (int col = 0; col < Cols; col++)
			{
				if (!tablero.GetCelda(fila, col).EsMina && !marcado[fila, col])
				{
					if (GetSurroundingMines(fila, col) == 0)
					{
						marcado[fila, col] = true;
						Flood(fila, col);
						bvClicks++;
					}
					if (!AlrededorCero(fila, col))
					{
						marcado[fila, col] = true;
						bvClicks++;
					}
				}
			}
		}
	}
 
	private void Flood(int fila, int col)
	{
		for (int r = fila - 1; r <= fila + 1; r++)
		{
			for (int c = col - 1; c <= col + 1; c++)
			{
				if (tablero.EnRango(r, c) && !tablero.GetCelda(r, c).EsMina && !marcado[r, c])
				{
					marcado[r, c] = true;
					if (GetSurroundingMines(r, c) == 0)
						Flood(r, c);
				}
			}
		}
	}
 
	private bool AlrededorCero(int fila, int col)
	{
		for (int r = fila - 1; r <= fila + 1; r++)
		{
			for (int c = col - 1; c <= col + 1; c++)
			{
				if (tablero.EnRango(r, c) && GetSurroundingMines(r, c) == 0)
					return true;
			}
		}
		return false;
	}
 
	// ---------- Puntaje ----------
	private void CalcularPuntaje()
	{
		double bvs = (double)bvClicks / seg;
		puntos = (int)Math.Ceiling(bvs * 1000);
 
		if (vidas == 2)
			puntos = (int)Math.Ceiling(puntos * 0.95);
		else if (vidas == 1)
			puntos = (int)Math.Ceiling(puntos * 0.85);
		else if (vidas == 0)
			puntos = (int)Math.Ceiling(puntos * 0.50);
	}
	
	
	private void perderVida(){
		sfx.Stream = SonidoBomba;
		sfx.Play();
		ShakeTablero();
		
		vidas--;
		EmitSignal(SignalName.VidaPerdida, vidas);
		TextureRect vida = vidas switch{
			2 => Vida3,
			1 => Vida2,
			0 => Vida1,
			_ => null
		};
		if (vida != null){
			vida.Texture = GD.Load<Texture2D>("res://assets/imgs/vidaX.png");
			float x = vida.Position.X;
			Tween t = CreateTween().SetTrans(Tween.TransitionType.Back).SetEase(Tween.EaseType.Out);
			t.TweenProperty(vida, "position:x", x + 2.0f, 0.1f);
			t.TweenProperty(vida, "position:x", x, 0.1f);
		}
		if (vidas == 0){
			GD.Print("murio");
			explotarTablero();
			TerminarJuego(false);
		}
	}
	private void ShakeTablero(){
		float intensidad = 20.0f;
		int sacudidas = 6;
		float duracion = 0.04f;
		tweenShake?.Kill();
		tablero.Position = tableroOrigen;

		tweenShake = CreateTween();
		for (int i = 0; i < sacudidas; i++){
			float fuerza = intensidad * (1.0f - (float)i / sacudidas);
			Vector2 offset = new Vector2((float)GD.RandRange(-1.0, 1.0),(float)GD.RandRange(-1.0, 1.0)) * fuerza;
			tweenShake.TweenProperty(tablero, "position", tableroOrigen + offset, duracion);
		}
		tweenShake.TweenProperty(tablero, "position", tableroOrigen, duracion);
	}
	
	private void explotarTablero(){
		tweenShake?.Kill();

		tablero.Position = tableroOrigen;
		tablero.Rotation = 0;

		tweenShake = CreateTween();
		tweenShake.SetParallel(true);

		tweenShake.TweenProperty(tablero,"position",new Vector2(tableroOrigen.X + 150, GetViewportRect().Size.Y + tablero.Size.Y),0.8f)
		.SetTrans(Tween.TransitionType.Expo).SetEase(Tween.EaseType.In);

		tweenShake.TweenProperty(tablero,"rotation",Mathf.DegToRad(35), 0.8f)
		.SetTrans(Tween.TransitionType.Quad).SetEase(Tween.EaseType.In);

		tweenShake.TweenProperty(tablero,"scale",new Vector2(0.7f, 0.7f),0.8f)
		.SetTrans(Tween.TransitionType.Quad).SetEase(Tween.EaseType.In);
	}
	
	private void configurarVidas(){
		Vida1.Visible = true;
		if(VidasIniciales>1)
			Vida2.Visible = true;
		else
			Vida2.Visible = false;
		if(VidasIniciales == 3)
			Vida3.Visible = true;
		else
			Vida3.Visible = false;
	}
	
	private void _on_timer_timeout(){
		seg++;
		if(Tiempo!=0){
			timerLabel.Text = Convert.ToString(Tiempo - seg);
			if(seg == Tiempo){
				//se acabo
			}
		}else
			timerLabel.Text = Convert.ToString(seg);
	}
	
	// agregué esto aquí - miguel (o 3 o)
	private void OnRendirseClick(Boton boton)
	{
		timer.Stop();
		GetTree().ChangeSceneToFile("res://scenes/menu.tscn");
	private void onPistaToggled(bool toggledOn){
		pistaToggle = toggledOn;
	}
	
	private void OnBotonClick(Boton boton){
		switch (boton.Name){
			case "Reiniciar":
				GetTree().ChangeSceneToFile("res://scenes/seleccion.tscn");
				break;
			case "Regresar":
				GetTree().ChangeSceneToFile("res://scenes/menu.tscn");
				break;
		}
	}
}
