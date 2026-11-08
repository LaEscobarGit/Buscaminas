using Godot;
using System;

public partial class GameScreen : Control
{
	[Signal] public delegate void JuegoIniciadoEventHandler();
	[Signal] public delegate void VidaPerdidaEventHandler(int vidas);
	[Signal] public delegate void JuegoTerminadoEventHandler(bool gano, int puntos);
 
	[Export] private AudioStreamPlayer sfx;
	[Export] private AudioStreamPlayer musica;
	[Export] private AudioStream SonidoBandera;
	[Export] private AudioStream SonidoClick;
	[Export] private AudioStream SonidoHover;
	[Export] private AudioStream SonidoBomba;
	[Export] private AudioStream SonidoMorir;
	[Export] private TextureRect Vida1;
	[Export] private TextureRect Vida2;
	[Export] private TextureRect Vida3;
	[Export] private AudioStream[] canciones;
	[Export] private Boton botonRendirse;
 
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

	public override void _Ready()
	{
		tablero = GetNode<Tablero>("Tablero");
		tableroOrigen = tablero.Position;
		timer = GetNode<Timer>("Timer");
		timerLabel = GetNode<Label>("TimerLabel");
		
		tablero.ClickIzquierdo += OnClickIzquierdo;
		tablero.ClickDerecho += OnClickDerecho;
		Filas = GameManager.filas;
		Cols = GameManager.columnas;
		NumMinas = GameManager.minas;
		Tiempo = GameManager.tiempo;
		VidasIniciales = GameManager.vidas;
		
		botonRendirse.Click += OnRendirseClick;
		
		//musica.Stream = canciones[GameManager.getActual()];
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
 
		if (celda.EsMina)
		{
			celda.RevelarMina();
			perderVida();
			return;
		}
 
		int countMines = GetSurroundingMines(srcRow, srcCol);
		celda.Revelar(countMines);
 
		if (countMines != 0)
		{
			//PlaySonido(SonidoCelda1);
		}
		else
		{
			for (int fila = srcRow - 1; fila <= srcRow + 1; fila++)
			{
				for (int col = srcCol - 1; col <= srcCol + 1; col++)
				{
					if (tablero.EnRango(fila, col) && !tablero.GetCelda(fila, col).Revelado)
						RevealCelda(fila, col);
				}
			}
			//PlaySonido(SonidoCelda);
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
 
	private void TerminarJuego(bool vivir)
	{
		if (juegoTerminado) return;
		juegoTerminado = true;
		//esto lo puse porque seaguia corriendo el timer aunque perdieras
 		timer.Stop();
 
		CalcularPuntaje();
		EmitSignal(SignalName.JuegoTerminado, vivir, puntos);
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
		if (vidas == 0)
			TerminarJuego(false);
	}
	private void ShakeTablero(){
		float intensidad = 20.0f;
		int sacudidas = 6;
		float duracion = 0.04f;
		tweenShake?.Kill();
		tablero.Position = tableroOrigen;

		tweenShake = CreateTween();
		for (int i = 0; i < sacudidas; i++)
		{
			float fuerza = intensidad * (1.0f - (float)i / sacudidas);
			Vector2 offset = new Vector2((float)GD.RandRange(-1.0, 1.0),(float)GD.RandRange(-1.0, 1.0)) * fuerza;
			tweenShake.TweenProperty(tablero, "position", tableroOrigen + offset, duracion);
		}
		tweenShake.TweenProperty(tablero, "position", tableroOrigen, duracion);
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
	}
}
