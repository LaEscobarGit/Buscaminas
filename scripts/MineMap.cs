using Godot;
using System;

public class MineMap
{
	private readonly int fFila;
	private readonly int fCol;
	private readonly int filas;
	private readonly int cols;
	private readonly Random random = new Random();

	public int NumMinas { get; private set; }
	public bool[,] EsMina { get; }

	//inicializa el numero de filas y columnas del tablero
	public MineMap(int fFila, int fCol, int filas, int cols)
	{
		this.fFila = fFila; //fila de la primera celda clickeada
		this.fCol = fCol; // columna de la primera celda clickeada
		this.filas = filas;
		this.cols = cols;
		EsMina = new bool[filas, cols];
	}

	// hace el mapa de minas random
	public void NewMineMap(int numMinas)
	{
		for (int i = 0; i < numMinas; i++)
		{
			int fila, col;
			do
			{
				fila = random.Next(filas);
				col = random.Next(cols);
			} while (EsMina[fila, col] || ZonaSegura(fila, col));

			EsMina[fila, col] = true;
		}
	}
	
	//es para saber si la celda esta en las 8 celdas alrededor de la primera celda clickeada
	private bool ZonaSegura(int fila, int col)
	{
		return Math.Abs(fila - fFila) <= 1 && Math.Abs(col - fCol) <= 1;
	}
}
