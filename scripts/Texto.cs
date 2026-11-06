using Godot;
using System;
using System.Threading.Tasks;

public partial class Texto : RichTextLabel
{
	[Export] float CHARS_POR_SEGUNDO = 30.0f;
	[Export] AudioStream[] sonidos;
	
	bool escribiendo = false;
	AudioStreamPlayer sonido = new AudioStreamPlayer();
	
	public override void _Ready()
	{
		sonido.Bus = "SFX";
		AddChild(sonido);
	}
	
	public async Task mostrarTexto(String nuevo_texto){
		escribiendo = false;
		await ToSignal(GetTree(), SceneTree.SignalName.ProcessFrame);

		Text = nuevo_texto;
		VisibleCharacters = 0;
		escribiendo = true;

		for(int i = 0; i < nuevo_texto.Length; i++){
			if(!escribiendo)
				return;

			VisibleCharacters += 1;
			var letra = nuevo_texto[i];

			if (letra != ' ' && letra != '\n'){
				int indice = (int)GD.RandRange(0, sonidos.Length - 1);
				sonido.Stream = sonidos[indice];
				sonido.PitchScale = (float)GD.RandRange(0.95, 1.05);
				sonido.Play();
			}

			await ToSignal(GetTree().CreateTimer(1.0 / CHARS_POR_SEGUNDO), SceneTreeTimer.SignalName.Timeout);
		}
		if(escribiendo)
			completarTexto();
	}
	
	public void detenerTexto(){
		escribiendo = false;
	}

	public void completarTexto(){
		escribiendo = false;
		VisibleCharacters = -1;
	}
	
	public bool estaEscribiendo(){
		return escribiendo;
	}
}
