using Godot;

public partial class FondoPasto : Sprite2D
{
	[Export] private Texture2D spritesheet;
	[Export] private float escala = 4.0f;
	[Export] private Vector2 velocidad = new Vector2(8, 4);

	private const int Tile = 16;
	private const int TilesPorLado = 16;
	private Vector2 desplazamiento;

	public override void _Ready()
	{
		Image hoja = spritesheet.GetImage();
		int columnas = hoja.GetWidth() / Tile;
		int filas = hoja.GetHeight() / Tile;

		// se crea una imagen de pasto al azar
		int lado = Tile * TilesPorLado;
		Image imagen = Image.CreateEmpty(lado, lado, false, hoja.GetFormat());

		for (int x = 0; x < TilesPorLado; x++)
		{
			for (int y = 0; y < TilesPorLado; y++)
			{
				int c = GD.RandRange(0, columnas - 1);
				int f = GD.RandRange(0, filas - 1);
				Rect2I origen = new Rect2I(c * Tile, f * Tile, Tile, Tile);
				imagen.BlitRect(hoja, origen, new Vector2I(x * Tile, y * Tile));
			}
		}

		Texture = ImageTexture.CreateFromImage(imagen);

		// repetir la textura
		Centered = false;
		RegionEnabled = true;
		TextureRepeat = TextureRepeatEnum.Enabled;
		TextureFilter = TextureFilterEnum.Nearest;
		Scale = new Vector2(escala, escala);
	}

	public override void _Process(double delta)
	{
		desplazamiento += velocidad * (float)delta;
		RegionRect = new Rect2(desplazamiento, GetViewportRect().Size / escala);
	}
}
