package com.adrian.tablaTiempos.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
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
}
