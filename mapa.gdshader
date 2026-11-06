shader_type canvas_item;
uniform vec4 line_color: source_color = vec4(1);
uniform float line_thickness = 5.0;
uniform float outline_alpha : hint_range(0.0, 1.0) = 0.0;

void fragment() {
	vec4 color = texture(TEXTURE,UV);
	vec2 size = TEXTURE_PIXEL_SIZE * line_thickness;
	float outline = color.a;
	outline += texture(TEXTURE, UV + vec2(size.x, 0)).a;
	outline += texture(TEXTURE, UV - vec2(size.x, 0)).a;
	outline += texture(TEXTURE, UV + vec2(0, size.y)).a;
	outline += texture(TEXTURE, UV - vec2(0, size.y)).a;
	outline = min(outline,1.0);
	
	vec4 final_line_color = line_color;
	final_line_color.a *= outline_alpha;

	COLOR = mix(color, final_line_color, outline - color.a);
}