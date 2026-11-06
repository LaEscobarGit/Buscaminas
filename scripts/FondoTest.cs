using Godot;
using System;

public partial class FondoTest : TextureRect
{
	[Export] private float zoomFinal = 1.15f;
	[Export] private float duracion = 20.0f;

	public override void _Ready()
	{
		PivotOffset = Size / 2.0f;
		Scale = Vector2.One;

		Tween zoom = CreateTween().SetTrans(Tween.TransitionType.Sine).SetEase(Tween.EaseType.InOut);
		zoom.TweenProperty(this, "scale", new Vector2(zoomFinal, zoomFinal), duracion);
	}
}
