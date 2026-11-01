using Godot;
using System;

public partial class Celda : Button
{
	[Export] private CompressedTexture2D banderaIcon;
	[Export] private CompressedTexture2D minaIcon;
	
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
}
