extends Panel
var opciones_actual: int = 0
const PAGINAS_OPCIONES = ["general", "audio", "controles"]
const RESOLUCIONES = [
	Vector2i(1280, 720),
	Vector2i(1600, 900),
	Vector2i(1920, 1080)
]
const IDIOMAS = ["es","en"]
const ACCIONES = {
	"interact": "Interactuar",
	"grab": "Recoger",
	"back": "Regresar/Folder",
	"advance": "Avanzar diálogo",
	"move_forward": "Mover adelante",
	"move_backward": "Mover atrás",
	"move_left": "Mover izquierda",
	"move_right": "Mover derecha",
	"run": "Correr"
}
var accion_esperando: String = ""
@onready var panel_general: Panel = $General
@onready var panel_audio: Panel = $Audio
@onready var panel_controles: Panel = $Controles
@onready var panel_salir: Panel = $Abandonar
@onready var controles_botones = $Controles/GridContainer
@onready var res_bot = $General/GridContainer/ResBot
@onready var full = $General/GridContainer/Fullscreen
@onready var idioma_bot = $General/GridContainer/IdiomaBot
@onready var reti = $General/GridContainer/Reticula
@onready var slider_1 = $Audio/VBoxContainer/SliderMaster
@onready var slider_2 = $Audio/VBoxContainer/SliderFX
@onready var slider_3 = $Audio/VBoxContainer/SliderMusica

func _ready() -> void:
	for accion in ACCIONES:
		var boton = controles_botones.get_node(accion)
		boton.pressed.connect(_on_boton_pressed.bind(accion))
	actualizar_botones()
	_cargar_config()

func abrir():
	_cargar_config()
	print("abrir:",GameManager.fullscreen)
	visible = true

func cerrar():
	GameManager.guardar_config()
	print("cerrar:",GameManager.fullscreen)
	visible = false

func _cargar_config():
	GameManager.cargar_config()
	slider_1.value = db_to_linear(GameManager.master_volumen)
	slider_2.value = db_to_linear(GameManager.sfx_volumen)
	slider_3.value = db_to_linear(GameManager.musica_volumen)
	full.button_pressed = GameManager.fullscreen
	reti.button_pressed = GameManager.reticula

	var ind := RESOLUCIONES.find(GameManager.resolucion)
	if ind != -1:
		res_bot.select(ind)
	var indice := IDIOMAS.find(GameManager.idioma)
	if indice != -1:
		idioma_bot.select(indice)

#GENERAL
func _on_res_bot_item_selected(index: int) -> void:
	if DisplayServer.window_get_mode() == DisplayServer.WINDOW_MODE_WINDOWED:
		DisplayServer.window_set_size(RESOLUCIONES[index])
func _on_fullscreen_toggled(toggled_on: bool) -> void:
	print("toggle: ",toggled_on)
	if toggled_on:
		DisplayServer.window_set_mode(DisplayServer.WINDOW_MODE_FULLSCREEN)
		res_bot.disabled = true
	else:
		DisplayServer.window_set_mode(DisplayServer.WINDOW_MODE_WINDOWED)
		res_bot.disabled = false
func _on_idioma_bot_item_selected(index: int) -> void:
	match index:
		0: GameManager.idioma = "es"
		1: GameManager.idioma = "en"
	TranslationServer.set_locale(GameManager.idioma)
	DatosManager.cargar_datos()
func _on_reticula_toggled(toggled_on: bool) -> void:
	UI.ret_enabled = toggled_on
	UI.set_reticula()
func actualizar_mensaje_pausa() -> void:
	if GameManager.fase_actual == GameManager.Fase.SUENO:
		$Abandonar/VBoxContainer/Cont.text = "Si sales ahora, perderás el progreso del sueño actual."
		$Abandonar/VBoxContainer/Tit.text = "Abandonar sueño"
	else:
		$Abandonar/VBoxContainer/Cont.text = "Tu progreso se guardará desde el último guardado automático."
		$Abandonar/VBoxContainer/Tit.text = "Volver al menú"
func _on_abandon_button_pressed() -> void:
	UI.reset_estado()
	get_tree().change_scene_to_file("res://Scenes/UI/menu.tscn")
	GameManager.en_menu = true
	panel_salir.visible = false
	panel_general.visible = true
func _on_stay_button_pressed() -> void:
	panel_salir.visible = false
	panel_general.visible = true
#AUDIO
func _on_slider_master_value_changed(value: float) -> void:
	AudioServer.set_bus_volume_db(AudioServer.get_bus_index("Master"), linear_to_db(value))
func _on_slider_fx_value_changed(value: float) -> void:
	AudioServer.set_bus_volume_db(AudioServer.get_bus_index("SFX"), linear_to_db(value))
func _on_slider_musica_value_changed(value: float) -> void:
	AudioServer.set_bus_volume_db(AudioServer.get_bus_index("Music"), linear_to_db(value))

#CONTROLES
func actualizar_botones() -> void:
	for accion in ACCIONES:
		var boton = controles_botones.get_node(accion)
		var label = boton.get_node("Label")
		var eventos = InputMap.action_get_events(accion)
		if eventos.size() > 0:
			if eventos[0] is InputEventKey:
				label.text = OS.get_keycode_string(eventos[0].physical_keycode)
			else:
				label.text = eventos[0].as_text()
func _on_boton_pressed(accion: String) -> void:
	if accion_esperando != "":
		actualizar_botones()
	accion_esperando = accion
	controles_botones.get_node(accion).get_node("Label").text = "..."
func _input(event: InputEvent) -> void:
	if accion_esperando == "":
		return
	if event is InputEventKey and event.pressed:
		if event.keycode == KEY_ESCAPE:
			accion_esperando = ""
			actualizar_botones()
			get_viewport().set_input_as_handled()
			return
		InputMap.action_erase_events(accion_esperando)
		InputMap.action_add_event(accion_esperando, event)
		actualizar_botones()
		accion_esperando = ""
		guardar_controles()
		get_viewport().set_input_as_handled()
func guardar_controles() -> void:
	var config = ConfigFile.new()
	config.load("user://settings.cfg")
	for accion in ACCIONES:
		var eventos = InputMap.action_get_events(accion)
		if eventos.size() > 0:
			config.set_value("controles", accion, eventos[0].as_text())
	config.save("user://settings.cfg")
