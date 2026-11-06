using Godot;
using System;

public partial class Profecia : Control
{
	[Export] VideoStreamPlayer video;
	private bool cambioEscena = false;
	private GameManager gameManager;
	
	public override void _Ready(){
		gameManager = GetNode<GameManager>("/root/GameManager");
		if (video == null)
		{
			GD.PushError("Video es null");
			return;
		}
		video.Finished += onVideoFinished;
	}
	private void onVideoFinished(){
		GD.Print("hey");
		GameManager.setDialogoActual("introduccion");
		GetTree().ChangeSceneToFile("res://scenes/historia.tscn");
	}
}
