package com.adrian.tablaTiempos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.adrian.tablaTiempos.Servicio.AjustesService;
import com.adrian.tablaTiempos.Servicio.PilotoService;

/**
 * Configuracion inicial: solo permite crear el primer administrador.
 */
@RestController
@RequestMapping("/setup")
public class SetupController {

	@Autowired
	private PilotoService pilotoService;

	@Autowired
	private AjustesService ajustesService;

	public record EstadoConfiguracion(boolean required) {
	}

	public record RespuestaConfiguracion(String mensaje) {
	}

	@GetMapping(value = "/status", produces = MediaType.APPLICATION_JSON_VALUE)
	public EstadoConfiguracion estado() {
		return new EstadoConfiguracion(!pilotoService.adminExiste());
	}

	@PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<RespuestaConfiguracion> configurar(
			@RequestParam(required = false) String nombre,
			@RequestParam(required = false) String pass,
			@RequestParam(required = false) String titulo,
			@RequestParam(required = false) MultipartFile logo,
			@RequestParam(required = false) MultipartFile fondo) {

		if (pilotoService.adminExiste()) {
			return ResponseEntity.status(HttpStatus.CONFLICT)
					.body(new RespuestaConfiguracion("Ya existe un administrador"));
		}
		if (nombre == null || pass == null) {
			return ResponseEntity.badRequest().body(new RespuestaConfiguracion("Faltan datos"));
		}

		try {
			ajustesService.guardarAjustes(titulo, logo, fondo, false, false);
		} catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().body(new RespuestaConfiguracion(e.getMessage()));
		}

		String resultado = pilotoService.crearAdministrador(nombre, pass);
		if (resultado == null || !resultado.toLowerCase().contains("guardado")) {
			return ResponseEntity.badRequest()
					.body(new RespuestaConfiguracion(resultado == null ? "No se pudo crear el administrador" : resultado));
		}
		return ResponseEntity.ok(new RespuestaConfiguracion("Administrador creado con exito"));
	}
}
