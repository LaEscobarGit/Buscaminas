shader_type canvas_item;
uniform sampler2D screen_texture : hint_screen_texture, repeat_disable, filter_linear;
uniform float intensidad : hint_range(0.0, 1.0) = 0.0;
uniform float velocidad : hint_range(0.0, 20.0) = 0.0;
uniform vec4 color_portal : source_color = vec4(0.25, 0.35, 1.0, 1.0);

void fragment() {
    vec2 centro = vec2(0.5);
    vec2 diferencia = SCREEN_UV - centro;
    float distancia = length(diferencia);
    float angulo = atan(diferencia.y, diferencia.x);
    float falloff = 1.0 - smoothstep(0.0, 0.85, distancia);
    float fuerza = intensidad * falloff;
    angulo += fuerza * (2.0 + TIME * velocidad);
    float radio = distancia * (1.0 + fuerza * 0.65);
    vec2 nuevaUV = centro + vec2(cos(angulo), sin(angulo)) * radio;
    nuevaUV = clamp(nuevaUV, vec2(0.001), vec2(0.999));
    vec4 fondo = texture(screen_texture, nuevaUV);
    vec3 colorizado = mix(fondo.rgb, color_portal.rgb, 0.35);
    fondo.rgb = mix( fondo.rgb, colorizado, intensidad * 0.75);
	COLOR = fondo;
}
