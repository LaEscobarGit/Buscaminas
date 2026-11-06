using Godot;
using System;

public partial class Region : Sprite2D
{
	private ShaderMaterial material;
	private bool isHovered = false;
	private CollisionPolygon2D polygon;
	private bool habilitado;
	private int region;
	
	public override void _Ready()
	{
		material = (ShaderMaterial)Material.Duplicate();
		Material = material;
		
		region = getRegion();
		habilitado = GameManager.getHabilitado(region);
		
		polygon = GetNode<CollisionPolygon2D>("Area2D/CollisionPolygon2D");
		material.SetShaderParameter("outline_alpha", 0.0f);
	}
	
	public override void _Process(double delta)
	{
		if(!habilitado)
			return;
		Vector2 mouse = GetGlobalMousePosition();
		Vector2 localMouse = polygon.ToLocal(mouse);

		bool hovering = Geometry2D.IsPointInPolygon(localMouse, polygon.Polygon);

		if (hovering != isHovered)
		{
			isHovered = hovering;
			material.SetShaderParameter("outline_alpha",isHovered ? 1.0f : 0.0f);
		}
		if (hovering && Input.IsActionJustPressed("click"))
		{
			GetTree().ChangeSceneToFile("res://scenes/historia.tscn");
		}
	}
	
	private int getRegion(){
		switch(Name){
			case "Region1": return 0;
			case "Region2": return 1;
			case "Region3": return 2;
		}
		return -1;
	}
}
