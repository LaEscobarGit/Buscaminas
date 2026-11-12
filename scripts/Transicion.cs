using Godot;
using System;
using System.Threading.Tasks;

public partial class Transicion : CanvasLayer
{
	[Export] AnimationPlayer animPlayer;
	public static Transicion instancia;

	public override void _Ready(){
		instancia = this;
	}
	
	public async Task cambiarEscena(String destino){
		animPlayer.Play("dissolve");
		await ToSignal(animPlayer, AnimationPlayer.SignalName.AnimationFinished);
		GetTree().ChangeSceneToFile(destino);
		animPlayer.PlayBackwards("dissolve");
	}
}
