package com.adrian.tablaTiempos.Servicio;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.adrian.tablaTiempos.IMPL.CircuitoImpl;
import com.adrian.tablaTiempos.JPA.CircuitoJPA;
import com.adrian.tablaTiempos.JPA.TiemposJPA;
import com.adrian.tablaTiempos.modelo.Circuito;

@Service
public class CircuitoService {
	
	@Autowired
	private CircuitoImpl circuitoImpl;

	@Autowired
	private CircuitoJPA circuitoJPA;

	@Autowired
	private TiemposJPA tiemposJPA;
	
	public List <Circuito> obtenerCircuitos() {
		return circuitoImpl.obtenerCircuitos();
	}

	public String guardarCircuito(String nombre) {
		if (nombre == null || nombre.trim().length() < 2) {
			return "El nombre del circuito es demasiado corto";
		}
		String limpio = nombre.trim();
		if (limpio.length() > 100) {
			return "El nombre del circuito es demasiado largo";
		}
		if (circuitoJPA.existsById(limpio)) {
			return "El circuito ya existe";
		}

		Circuito circuito = new Circuito();
		circuito.setNombreCircuito(limpio);
		circuitoJPA.save(circuito);
		return "Circuito guardado con exito";
	}

	public String borrarCircuito(String nombre) {
		if (nombre == null || nombre.trim().isEmpty()) {
			return "Selecciona un circuito";
		}
		String limpio = nombre.trim();
		if (!circuitoJPA.existsById(limpio)) {
			return "El circuito no existe";
		}

		long tiempos = tiemposJPA.contarPorCircuito(limpio);
		if (tiempos > 0) {
			return "No se puede borrar: hay " + tiempos + " tiempos registrados en ese circuito";
		}

		circuitoJPA.deleteById(limpio);
		return "Circuito borrado con exito";
	}

	@Transactional
	public String editarCircuito(String nombre, String nuevoNombre) {
		if (nombre == null || nombre.trim().isEmpty()) {
			return "Selecciona un circuito";
		}
		if (nuevoNombre == null || nuevoNombre.trim().length() < 2) {
			return "El nombre del circuito es demasiado corto";
		}

		String viejo = nombre.trim();
		String nuevo = nuevoNombre.trim();
		if (viejo.equals(nuevo)) {
			return "El nombre no ha cambiado";
		}
		if (nuevo.length() > 100) {
			return "El nombre del circuito es demasiado largo";
		}
		if (!circuitoJPA.existsById(viejo)) {
			return "El circuito no existe";
		}
		if (circuitoJPA.existsById(nuevo)) {
			return "Ya existe un circuito con ese nombre";
		}

		Circuito nuevoCircuito = new Circuito();
		nuevoCircuito.setNombreCircuito(nuevo);
		circuitoJPA.save(nuevoCircuito);
		tiemposJPA.renombrarCircuito(viejo, nuevoCircuito);
		circuitoJPA.deleteById(viejo);
		return "Circuito actualizado con exito";
	}
}
