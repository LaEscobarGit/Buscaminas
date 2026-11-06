using Godot;
using System;

public partial class Celda : Button
{
	[Export] private CompressedTexture2D banderaIcon;
	[Export] private CompressedTexture2D minaIcon;
	
	// colores para las celdas
	[Export] private Color verdeClaro = new Color("559575ff");
	[Export] private Color verdeOscuro = new Color("5a8964ff");
	[Export] private Color reveladoClaro = new Color("e5c29f");
	[Export] private Color reveladoOscuro = new Color("d7b899");
	
	[Signal] public delegate void ClickIzquierdoEventHandler(Celda celda);
	[Signal] public delegate void ClickDerechoEventHandler(Celda celda);

	public int Fila { get; private set; }
	public int Col { get; private set; }
	public bool Revelado { get; private set; }
	public bool EsMina { get; private set; }
	public bool Banderado { get; private set; }

	public void Init(int fila, int col)
	{
		Fila = fila;
		Col = col;
		ExpandIcon = true;
		IconAlignment = HorizontalAlignment.Center;
		FocusMode = FocusModeEnum.None;
		AplicarColores();
	}

	public void NewGame(bool esMina)
	{
		Revelado = false;
		Banderado = false;
		EsMina = esMina;
		Disabled = false;
		Text = "";
		Icon = null;
	}

	public void AlternarBandera()
	{
		if (Revelado) return;
		Banderado = !Banderado;
		if(Banderado){
			Icon = banderaIcon;
		}else{
			Icon = null;
		}
		Text = "";
	}

	public void Revelar(int minasAlrededor)
	{
		if(Banderado) return;
		Revelado = true;
		Banderado = false;
		Disabled = true;
		Icon = null;
		if(minasAlrededor > 0){
			Text = minasAlrededor.ToString();
		}else{
			Text = "";
		}
	}

	public void RevelarMina()
	{
		if(Banderado) return;
		Revelado = true;
		Banderado = false;
		Disabled = true;
		Icon = minaIcon;
		Text = "";
	}

	// click izq y der
	public override void _GuiInput(InputEvent @event)
	{
		if (@event is InputEventMouseButton mouse && mouse.Pressed)
		{
			if (mouse.ButtonIndex == MouseButton.Left)
				EmitSignal(SignalName.ClickIzquierdo, this);
			else if (mouse.ButtonIndex == MouseButton.Right)
				EmitSignal(SignalName.ClickDerecho, this);
		}
	}
	
	// estos métodos los puse para que las celdas alternen color
	private void AplicarColores()
	{
	bool par = (Fila + Col) % 2 == 0;
	Color normal = par ? verdeClaro : verdeOscuro;
	Color revelado = par ? reveladoClaro : reveladoOscuro;

	AddThemeStyleboxOverride("normal", CrearEstilo(normal));
	AddThemeStyleboxOverride("hover", CrearEstilo(normal.Lightened(0.15f)));
	AddThemeStyleboxOverride("pressed", CrearEstilo(normal.Darkened(0.1f)));
	AddThemeStyleboxOverride("disabled", CrearEstilo(revelado));
	}

	private StyleBoxFlat CrearEstilo(Color color)
	{
	StyleBoxFlat estilo = new StyleBoxFlat();
	estilo.BgColor = color;
	return estilo;
	}
}
