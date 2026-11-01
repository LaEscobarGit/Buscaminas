using Godot;
using System;

public partial class Tablero : Control
{
	[Signal] public delegate void ClickIzquierdoEventHandler(Celda celda);
	[Signal] public delegate void ClickDerechoEventHandler(Celda celda);
 
	[Export]
	public PackedScene CeldaScene;
 
	private GridContainer grid;
	private Celda[,] celdas;
 
	public int Filas { get; private set; }
	public int Cols { get; private set; }
 
	public override void _Ready()
	{
		grid = GetNode<GridContainer>("GridContainer");
		grid.AddThemeConstantOverride("h_separation", 2);
		grid.AddThemeConstantOverride("v_separation", 2);
	}
 
	// armar las celdas
	public void Construir(int filas, int cols)
	{
		Filas = filas;
		Cols = cols;

		foreach (Node hijo in grid.GetChildren())
		{
			grid.RemoveChild(hijo);
			hijo.QueueFree();
		}
 
		grid.Columns = cols;
		celdas = new Celda[filas, cols];
 
		for (int fila = 0; fila < filas; fila++)
		{
			for (int col = 0; col < cols; col++)
			{
				Celda celda = CeldaScene.Instantiate<Celda>();
				celda.Init(fila, col);

				celda.SizeFlagsHorizontal = SizeFlags.ExpandFill;
				celda.SizeFlagsVertical = SizeFlags.ExpandFill;
 
				celda.ClickIzquierdo += c => EmitSignal(SignalName.ClickIzquierdo, c);
				celda.ClickDerecho += c => EmitSignal(SignalName.ClickDerecho, c);
 
				celdas[fila, col] = celda;
				grid.AddChild(celda);
				celda.NewGame(false);
			}
		}
	}
 
	public Celda GetCelda(int fila, int col)
	{
		return celdas[fila, col];
	}
 
	public bool EnRango(int fila, int col)
	{
		return fila >= 0 && fila < Filas && col >= 0 && col < Cols;
	}
}
