using Godot;
using System;
using System.Threading.Tasks;

public partial class PanelTween : Control
{
	public async Task aparecer(){
		Scale = Vector2.Zero;
		Tween tween = CreateTween();
		tween.TweenProperty(this, "scale", new Vector2(1.2f, 1.2f), 0.2f)
			.SetTrans(Tween.TransitionType.Back).SetEase(Tween.EaseType.Out);
		tween.TweenProperty(this, "scale", Vector2.One, 0.15f)
			.SetTrans(Tween.TransitionType.Sine).SetEase(Tween.EaseType.Out);
		await ToSignal(tween, Tween.SignalName.Finished);
	}
	
	public async Task desaparecer(){
		Tween tween = CreateTween();
		tween.TweenProperty(this, "scale", new Vector2(0.1f, 0.1f), 0.2f).SetTrans(Tween.TransitionType.Back).SetEase(Tween.EaseType.In);
		await ToSignal(tween, Tween.SignalName.Finished);
	}
}
