package com.adrian.tablaTiempos.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.adrian.tablaTiempos.Servicio.PilotoService;
import com.adrian.tablaTiempos.modelo.Piloto;

@RestController
public class RutasPiloto {
	
	@Autowired
	private PilotoService pilotoService;

	public record EditarPiloto(String nombre, String nuevoNombre, String pass) {
	}

	public record BorrarPiloto(String nombre) {
	}

	@PostMapping(value="/addPiloto", consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.TEXT_PLAIN_VALUE)
	public String añadirUsuario(@RequestBody Piloto piloto) {
		return pilotoService.guardarPiloto(piloto);
	}
	 
	@PostMapping(value="/cambiarPass", consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.TEXT_PLAIN_VALUE)
	public String cambiarContraseña(@RequestBody Piloto piloto,
			org.springframework.security.core.Authentication authentication) {
		if (piloto == null || piloto.getNombre() == null || authentication == null) {
			return "No ha llegado correctamente el piloto al servidor";
		}

		boolean esAdmin = authentication.getAuthorities().stream()
				.anyMatch(a -> "ROLE_ADMIN".equals(a.getAuthority()));
		if (!esAdmin && !authentication.getName().equals(piloto.getNombre())) {
			return "No tienes permiso para cambiar esa contraseña";
		}

		return pilotoService.cambiarContraseña(piloto);
	}

	@GetMapping(value="/listarPilotos", produces=MediaType.APPLICATION_JSON_VALUE)
	public List<Piloto> listarPilotos() {
		return pilotoService.listarPilotos();
	}

	@PostMapping(value="/editarPiloto", consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.TEXT_PLAIN_VALUE)
	public String editarPiloto(@RequestBody EditarPiloto body) {
		if (body == null) {
			return "Faltan datos";
		}
		return pilotoService.editarPiloto(body.nombre(), body.nuevoNombre(), body.pass());
	}

	@PostMapping(value="/borrarPiloto", consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.TEXT_PLAIN_VALUE)
	public String borrarPiloto(@RequestBody BorrarPiloto body) {
		return pilotoService.borrarPiloto(body == null ? null : body.nombre());
	}
}
