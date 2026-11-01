using Godot;
using System;

public partial class Boton : Button
{	private Tween tween;
	private bool enHover;
	private RandomNumberGenerator random = new RandomNumberGenerator();
	
	[Export] private AudioStream SonidoClick;
	[Export] private AudioStream SonidoHover;
	[Export] private AudioStreamPlayer sfx;
	[Signal] public delegate void ClickEventHandler(Boton boton);
	
	public override void _Ready()
	{
		MouseEntered += _on_menu_mouse_entered;
		MouseExited += _on_menu_mouse_exited;
		ButtonDown += _on_button_down;
		ButtonUp += _on_button_up;
		enHover = false;
	}
	
	public void _on_menu_mouse_entered(){
		if(enHover) return;
		enHover = true;
		
		PivotOffset = Size / 2.0f;
		float rotacion = random.RandiRange(0, 1) == 0 ? -4.0f : 4.0f;

		sfx.Stream = SonidoHover;
		sfx.Play();

		tween = CreateTween().SetTrans(Tween.TransitionType.Back).SetEase(Tween.EaseType.Out);
		tween.TweenProperty(this, "scale:x", 1.1f, 0.2f);
		tween.Parallel().TweenProperty(this, "scale:y", 1.1f, 0.3f);
		tween.Parallel().TweenProperty(this, "rotation_degrees", rotacion, 0.1f);
	}
	public void _on_menu_mouse_exited(){
		enHover = false;
		tween = CreateTween();
		tween.TweenProperty(this, "rotation_degrees", 0.0f, 0.1f).SetTrans(Tween.TransitionType.Back);
		tween.Parallel().TweenProperty(this, "scale:y", 1.0f, 0.1f);
		tween.Parallel().TweenProperty(this, "scale:x", 1.0f, 0.1f);
	}
	
	public void _on_button_down(){
		sfx.Stream = SonidoClick;
		sfx.Play();
		
		tween = CreateTween().SetTrans(Tween.TransitionType.Back).SetEase(Tween.EaseType.Out);
		tween.TweenProperty(this, "scale:x", 0.9f, 0.2f);
		tween.Parallel().TweenProperty(this, "scale:y", 0.9f, 0.3f);
		tween.Parallel().TweenProperty(this, "rotation_degrees", 0.0f, 0.1f);
	}
	public void _on_button_up(){
		PivotOffset = Size / 2.0f;
		float rotacion = random.RandiRange(0, 1) == 0 ? -4.0f : 4.0f;

		sfx.Stream = SonidoClick;
		sfx.Play();

		tween = CreateTween().SetTrans(Tween.TransitionType.Back).SetEase(Tween.EaseType.Out);
		tween.TweenProperty(this, "scale:x", 1.3f, 0.2f);
		tween.Parallel().TweenProperty(this, "scale:y", 1.3f, 0.3f);
		tween.Parallel().TweenProperty(this, "rotation_degrees", rotacion, 0.1f);
	}
	
	public override void _Pressed(){
		EmitSignal(SignalName.Click, this);
	}
}
