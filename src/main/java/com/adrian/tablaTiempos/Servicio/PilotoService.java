package com.adrian.tablaTiempos.Servicio;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.adrian.tablaTiempos.IMPL.PilotoImpl;
import com.adrian.tablaTiempos.JPA.PilotoJPA;
import com.adrian.tablaTiempos.JPA.TiemposJPA;
import com.adrian.tablaTiempos.modelo.Piloto;

@Service
public class PilotoService {
	
	@Autowired
	private PilotoImpl pilotoImpl;

	@Autowired
	private PilotoJPA pilotoJPA;

	@Autowired
	private TiemposJPA tiemposJPA;
	
	private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	
	public String guardarPiloto(Piloto piloto) {
		if (piloto == null || piloto.getNombre() == null || piloto.getNombre().trim().isEmpty()
				|| piloto.getPass() == null || piloto.getPass().isEmpty()) {
			return "No ha llegado correctamente el piloto al servidor";
		}

		String nombre = piloto.getNombre().trim();
		if (nombre.length() < 3 || nombre.length() > 10) {
			return "El nombre debe tener entre 3 y 10 caracteres";
		}
		if (piloto.getPass().length() < 4) {
			return "La contraseña debe tener al menos 4 caracteres";
		}
		piloto.setNombre(nombre);

		// Comprobar duplicado ANTES de guardar (antes se guardaba y luego se avisaba)
		if (pilotoImpl.buscarPiloto(nombre) != null) {
			return "El nombre ya esta en uso";
		}

		// Los pilotos creados desde /addPiloto siempre son usuarios normales
		piloto.setRol("USER");
		piloto.setPass(passwordEncoder.encode(piloto.getPass()));
		return pilotoImpl.guardarPiloto(piloto);
	}

	public boolean adminExiste() {
		return pilotoJPA.existsByRolIgnoreCase("ADMIN");
	}

	public String crearAdministrador(String nombre, String pass) {
		if (adminExiste()) {
			return "Ya existe un administrador";
		}
		if (nombre == null || nombre.trim().length() < 3 || nombre.trim().length() > 10) {
			return "El usuario debe tener entre 3 y 10 caracteres";
		}
		if (pass == null || pass.length() < 4) {
			return "La contraseña debe tener al menos 4 caracteres";
		}
		if (pilotoImpl.buscarPiloto(nombre.trim()) != null) {
			return "El nombre ya esta en uso";
		}

		Piloto admin = new Piloto();
		admin.setNombre(nombre.trim());
		admin.setPass(passwordEncoder.encode(pass));
		admin.setRol("ADMIN");
		return pilotoImpl.guardarPiloto(admin);
	}
	
	public String cambiarContraseña(Piloto piloto) {
		if (piloto == null || piloto.getNombre() == null || piloto.getPass() == null || piloto.getPass().isEmpty()) {
			return "No ha llegado correctamente el piloto al servidor";
		}

		// Cargar el piloto existente para no borrar su rol ni sus tiempos
		Piloto existente = pilotoImpl.buscarPiloto(piloto.getNombre());
		if (existente == null) {
			return "No ha llegado correctamente el piloto al servidor";
		}
		if (piloto.getPass().length() < 4) {
			return "La contraseña debe tener al menos 4 caracteres";
		}

		existente.setPass(passwordEncoder.encode(piloto.getPass()));
		return pilotoImpl.cambiarcontraseña(existente);
	}

	public List<Piloto> listarPilotos() {
		return pilotoImpl.listarPilotos();
	}

	@Transactional
	public String editarPiloto(String nombre, String nuevoNombre, String pass) {
		if (nombre == null || nombre.trim().isEmpty()) {
			return "Selecciona un piloto";
		}
		Piloto existente = pilotoImpl.buscarPiloto(nombre);
		if (existente == null) {
			return "El piloto no existe";
		}

		String destino = nuevoNombre == null || nuevoNombre.trim().isEmpty()
				? nombre
				: nuevoNombre.trim();

		if (destino.length() < 3 || destino.length() > 10) {
			return "El nombre debe tener entre 3 y 10 caracteres";
		}

		if (!destino.equals(nombre)) {
			if ("ADMIN".equalsIgnoreCase(existente.getRol())) {
				return "No se puede cambiar el nombre de un administrador";
			}
			if (pilotoImpl.buscarPiloto(destino) != null) {
				return "El nombre ya esta en uso";
			}
		}

		if (pass != null && !pass.isEmpty()) {
			if (pass.length() < 4) {
				return "La contraseña debe tener al menos 4 caracteres";
			}
			existente.setPass(passwordEncoder.encode(pass));
		}

		if (!destino.equals(nombre)) {
			Piloto nuevo = new Piloto();
			nuevo.setNombre(destino);
			nuevo.setPass(existente.getPass());
			nuevo.setRol(existente.getRol());
			pilotoImpl.guardarPiloto(nuevo);
			tiemposJPA.renombrarPiloto(nombre, nuevo);
			pilotoImpl.borrarPiloto(nombre);
			return "Piloto actualizado con exito";
		}

		pilotoImpl.guardarPiloto(existente);
		return "Piloto actualizado con exito";
	}

	public String borrarPiloto(String nombre) {
		if (nombre == null || nombre.trim().isEmpty()) {
			return "Selecciona un piloto";
		}
		Piloto existente = pilotoImpl.buscarPiloto(nombre);
		if (existente == null) {
			return "El piloto no existe";
		}
		if ("ADMIN".equalsIgnoreCase(existente.getRol())) {
			return "No se puede borrar un administrador";
		}

		long tiempos = tiemposJPA.contarPorPiloto(nombre);
		if (tiempos > 0) {
			return "No se puede borrar: tiene " + tiempos + " tiempos registrados";
		}
		return pilotoImpl.borrarPiloto(nombre);
	}
}
