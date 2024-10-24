package com.adrian.tablaTiempos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.adrian.tablaTiempos.Servicio.PilotoService;
import com.adrian.tablaTiempos.modelo.Piloto;

@RestController
public class RutasPiloto {
	
	@Autowired
	private PilotoService pilotoService;

	@PostMapping(value="/addPiloto", consumes=MediaType.APPLICATION_JSON_VALUE)
	public String añadirUsuario(@RequestBody Piloto piloto) {
		return pilotoService.guardarPiloto(piloto);
	}
	 
	@PostMapping(value="/cambiarPass", consumes=MediaType.APPLICATION_JSON_VALUE)
	public String cambiarContraseña(@RequestBody Piloto piloto) {
		return pilotoService.cambiarContraseña(piloto);
	}
	
}
