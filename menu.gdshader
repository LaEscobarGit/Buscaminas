shader_type canvas_item;

uniform float progreso = -0.5;
uniform float ancho = 0.08;
uniform float inclinacion = 0.35;
uniform float intensidad = 1.0;
uniform float opacidad = 1.0;
uniform vec4 color_brillo : source_color = vec4(0.0, 0.0, 0.0, 1.0);

void fragment() {
	vec4 tex = texture(TEXTURE, UV);
	float pos = UV.x + UV.y * inclinacion;
	float banda = smoothstep(ancho, 0.0, abs(pos - progreso));
	COLOR = tex;
	COLOR.rgb = mix(tex.rgb, color_brillo.rgb, banda * intensidad);
	COLOR.a *= opacidad;
}