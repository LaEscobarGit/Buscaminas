using Godot;
using System;

public partial class DialogManager : Node
{
	[Signal] public delegate void DialogoActualizadoEventHandler(string quien, string texto);
	[Signal] public delegate void DialogoTerminadoEventHandler();
	[Signal] public delegate void PersonajeSaleEventHandler(string personaje);
	[Signal] public delegate void PersonajeEntraEventHandler(string personaje);
	
	private Godot.Collections.Dictionary dialogos = new();
	private Godot.Collections.Array lineasActuales = new();
	private int lineaIndex = 0;
	
	public override void _Ready()
	{
		cargarDialogos();
	}

	private void cargarDialogos(){
		using FileAccess archivo = FileAccess.Open("res://assets/data/dialogos.json",FileAccess.ModeFlags.Read);
		if(archivo!=null){
			dialogos = Json.ParseString(archivo.GetAsText()).AsGodotDictionary();
		}
	}
	
	public void iniciarDialogo(string dialogo){
		if (!dialogos.ContainsKey(dialogo)){
			return;
		}

		lineasActuales = dialogos[dialogo].AsGodotArray();
		lineaIndex = 0;
		mostrarLineaActual();
	}

	public void avanzar(){
		lineaIndex++;
		if (lineaIndex >= lineasActuales.Count){
			EmitSignal(SignalName.DialogoTerminado);
			return;
		}

		mostrarLineaActual();
	}
	
	private void mostrarLineaActual()
	{
		string linea = lineasActuales[lineaIndex].AsString();
		if (linea.StartsWith("entra [") && linea.EndsWith("]")){
			string personaje = linea.Substring(7, linea.Length - 8);
			EmitSignal(SignalName.PersonajeEntra, personaje);
			avanzar();
			return;
		}
		if (linea.StartsWith("sale [") && linea.EndsWith("]")){
			string personaje = linea.Substring(6, linea.Length - 7);
			EmitSignal(SignalName.PersonajeSale, personaje);
			avanzar();
			return;
		}

		var partes = separarQuien(linea);
		EmitSignal(SignalName.DialogoActualizado,partes[0].AsString(),partes[1].AsString());
	}
	
	private Godot.Collections.Array separarQuien(string texto){
		if (texto.Contains(": ")){
			string[] partes = texto.Split(new[] { ": " },2,StringSplitOptions.None);
			return new Godot.Collections.Array { partes[0], partes[1] };
		}
		return new Godot.Collections.Array { "", texto };
	}
}
