package com.adrian.tablaTiempos.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.CacheControl;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.adrian.tablaTiempos.Servicio.AjustesService;
import com.adrian.tablaTiempos.modelo.AjustesDto;
import com.adrian.tablaTiempos.modelo.ArchivoDto;

@RestController
@RequestMapping("/settings")
public class RutasAjustes {

	@Autowired
	private AjustesService ajustesService;

	@GetMapping
	public AjustesDto obtenerAjustes() {
		return ajustesService.obtenerAjustes();
	}

	@GetMapping("/logo")
	public ResponseEntity<Resource> verLogo() {
		return archivo(ajustesService.obtenerLogo());
	}

	@GetMapping("/fondo")
	public ResponseEntity<Resource> verFondo() {
		return archivo(ajustesService.obtenerFondo());
	}

	@PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<?> guardarAjustes(@RequestParam(required = false) String titulo,
			@RequestParam(required = false) MultipartFile logo,
			@RequestParam(required = false) MultipartFile fondo,
			@RequestParam(required = false, defaultValue = "false") boolean quitarLogo,
			@RequestParam(required = false, defaultValue = "false") boolean quitarFondo) {
		try {
			return ResponseEntity.ok(ajustesService.guardarAjustes(titulo, logo, fondo, quitarLogo, quitarFondo));
		} catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
		}
	}

	private ResponseEntity<Resource> archivo(ArchivoDto archivo) {
		if (archivo == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok()
				.contentType(MediaType.parseMediaType(archivo.getTipoContenido()))
				.cacheControl(CacheControl.noCache())
				.body(new FileSystemResource(archivo.getRuta()));
	}
}
