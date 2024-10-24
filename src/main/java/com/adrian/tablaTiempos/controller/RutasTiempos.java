package com.adrian.tablaTiempos.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.adrian.tablaTiempos.Servicio.TiemposService;
import com.adrian.tablaTiempos.modelo.Tiempos;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;


@RestController
public class RutasTiempos {

	//Acuerdate de ingresar los circuitos
	
	@Autowired
	private TiemposService tiemposService;
	
	@PostMapping(value="/addTiempo")
	public String addTiempo(@RequestParam("tiempos") String tiempos, @RequestParam("foto") MultipartFile foto) throws JsonMappingException, JsonProcessingException {
		
		ObjectMapper mapper = new ObjectMapper();
		Tiempos tiempo = mapper.readValue(tiempos, Tiempos.class);
		return tiemposService.añadirTiempo(tiempo, foto);
		}
	//@PostMapping(value="/borrarTiempo")
	//public boolean borrarTiempo(@RequestBody Tiempos tiempo) {
		
	//}
	
	public List <Tiempos> listarTodosTiempos() {
		return tiemposService.listarTodosTiempos();
	}
	
	@PostMapping(value="/listarPorIdTiempoYPiloto", consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
	public List <Tiempos> listarPorIdTiempoYPiloto(@RequestBody Tiempos tiempo){
		return tiemposService.listarPorIdTiempo(tiempo);
	}
	
	@PostMapping(value="/listarTodosTiemposPorCircuito", consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
	public List <Tiempos> listarTodosTiemposPorCircuito(@RequestBody Tiempos tiempo){
		return tiemposService.listarTodosTiemposPorCircuito(tiempo);
	}
	
	@PostMapping(value="/listarTodosTiemposPorPiloto",  consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
	public List <Tiempos> listarTodosTiemposPorPiloto(@RequestBody Tiempos tiempo){
		return tiemposService.listarTodosTiemposPorPiloto(tiempo);
	}
	
	@PostMapping(value="/listarTodosTiemposPorCoche", consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
	public List <Tiempos> listarTodosTiemposPorCoche(@RequestBody Tiempos tiempo){
		return tiemposService.listarTodosTiemposPorCoche(tiempo);
	}
	
	@PostMapping(value="/listarTodosTiemposPorCircuitoYPiloto", consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
	public List <Tiempos> listarTodosTiemposPorCircuitoYPiloto(@RequestBody Tiempos tiempo){
		return tiemposService.listarTodosTiemposPorCircuitoYPiloto(tiempo);
	}
	
	@PostMapping(value="/listarTodosTiemposPorCircuitoYCoche", consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
	public List <Tiempos> listarTodosTiemposPorCircuitoYCoche(@RequestBody Tiempos tiempo){
		return tiemposService.listarTodosTiemposPorCircuitoYCoche(tiempo);
	}
	
	@PostMapping(value="/listarTodosTiemposPorPilotoYCoche", consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
	public List <Tiempos> listarTodosTiemposPorPilotoYCoche(@RequestBody Tiempos tiempo){
		return tiemposService.listarTodosTiemposPorPilotoYCoche(tiempo);
	}
	
	@PostMapping(value="/listarTodosTiemposPorCircuitoYPilotoYCoche", consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
	public List <Tiempos> listarTodosTiemposPorCircuitoYPilotoYCoche(@RequestBody Tiempos tiempo){
		return tiemposService.listarTodosTiemposPorCircuitoYPilotoYCoche(tiempo);
	}
}
