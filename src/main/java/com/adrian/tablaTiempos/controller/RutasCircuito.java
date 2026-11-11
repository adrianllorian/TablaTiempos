package com.adrian.tablaTiempos.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.adrian.tablaTiempos.Servicio.CircuitoService;
import com.adrian.tablaTiempos.modelo.Circuito;

@RestController
public class RutasCircuito {
	
	@Autowired
	public CircuitoService circuitoService;

	@GetMapping(value="/listarCircuitos", produces=MediaType.APPLICATION_JSON_VALUE)
	public List <Circuito>listarCircuitos() {
		return circuitoService.obtenerCircuitos();
	}

	public record EditarCircuito(String nombreCircuito, String nuevoNombre) {
	}

	@PostMapping(value="/addCircuito", consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.TEXT_PLAIN_VALUE)
	public String addCircuito(@RequestBody Circuito circuito) {
		return circuitoService.guardarCircuito(circuito == null ? null : circuito.getNombreCircuito());
	}

	@PostMapping(value="/editarCircuito", consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.TEXT_PLAIN_VALUE)
	public String editarCircuito(@RequestBody EditarCircuito body) {
		if (body == null) {
			return "Faltan datos";
		}
		return circuitoService.editarCircuito(body.nombreCircuito(), body.nuevoNombre());
	}

	@PostMapping(value="/borrarCircuito", consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.TEXT_PLAIN_VALUE)
	public String borrarCircuito(@RequestBody Circuito circuito) {
		return circuitoService.borrarCircuito(circuito == null ? null : circuito.getNombreCircuito());
	}
}
