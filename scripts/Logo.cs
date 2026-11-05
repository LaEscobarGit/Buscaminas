using Godot;
using System;

public partial class Logo : TextureRect
{
	[Export] private float retrasoInicial = 0.5f;

	public override void _Ready()
	{

		((ShaderMaterial)Material).SetShaderParameter("opacidad", 0.0f);

		Tween entrada = CreateTween().SetTrans(Tween.TransitionType.Sine).SetEase(Tween.EaseType.Out);
		entrada.TweenInterval(retrasoInicial);
		entrada.TweenProperty(this, "material:shader_parameter/opacidad", 1.0f, 1.5f);
		entrada.TweenInterval(0.3);
		entrada.TweenCallback(Callable.From(Brillo));
	}

	private void Brillo()
	{
		Tween brillo = CreateTween();
		brillo.TweenProperty(this, "material:shader_parameter/progreso", 1.5f, 0.7f)
			.From(-0.2f)
			.SetTrans(Tween.TransitionType.Sine)
			.SetEase(Tween.EaseType.InOut);
	}
}
