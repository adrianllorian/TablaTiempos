package com.adrian.tablaTiempos.Servicio;

import java.security.SecureRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.adrian.tablaTiempos.JPA.AjustesJPA;
import com.adrian.tablaTiempos.modelo.Ajustes;

/**
 * Clave para firmar los JWT. Se genera una distinta en cada instalacion y se
 * guarda en la base de datos. No hay que configurar nada.
 * Si se define la variable de entorno JWT_SECRET, se usa esa en su lugar.
 */
@Service
public class JwtService {

	private static final String ALFABETO = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

	@Autowired
	private AjustesJPA ajustesJPA;

	@Value("${JWT_SECRET:}")
	private String claveEntorno;

	private String clave;

	public synchronized String obtenerClave() {
		if (clave != null) {
			return clave;
		}

		if (claveEntorno != null && !claveEntorno.trim().isEmpty()) {
			clave = claveEntorno.trim();
			return clave;
		}

		Ajustes ajustes = ajustesJPA.findById(1L).orElseGet(() -> {
			Ajustes nuevo = new Ajustes();
			nuevo.setId(1L);
			return nuevo;
		});

		if (ajustes.getClaveJwt() == null || ajustes.getClaveJwt().trim().isEmpty()) {
			ajustes.setClaveJwt(generarClave());
			ajustesJPA.save(ajustes);
		}

		clave = ajustes.getClaveJwt();
		return clave;
	}

	private String generarClave() {
		SecureRandom random = new SecureRandom();
		StringBuilder salida = new StringBuilder(64);
		for (int i = 0; i < 64; i++) {
			salida.append(ALFABETO.charAt(random.nextInt(ALFABETO.length())));
		}
		return salida.toString();
	}
}
