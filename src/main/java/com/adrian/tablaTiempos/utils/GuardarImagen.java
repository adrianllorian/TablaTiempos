package com.adrian.tablaTiempos.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Locale;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class GuardarImagen {

	private final Path directorio;

	public GuardarImagen(@Value("${app.upload-dir:uploads}") String uploadDir) throws IOException {
		this.directorio = Paths.get(uploadDir).toAbsolutePath().normalize();
		Files.createDirectories(this.directorio);
	}

	public String guardarImagen(MultipartFile foto) {
		if (foto == null || foto.isEmpty()) {
			return null;
		}

		String nombre = UUID.randomUUID().toString() + "." + extensionDe(foto.getOriginalFilename());
		try {
			Files.copy(foto.getInputStream(), directorio.resolve(nombre));
			return nombre;
		} catch (IOException e) {
			return null;
		}
	}

	public byte[] verImagen(String nombreDeLaFoto) {
		if (nombreDeLaFoto == null || nombreDeLaFoto.trim().isEmpty()) {
			return null;
		}
		try {
			Path ruta = directorio.resolve(nombreDeLaFoto).normalize();
			if (ruta.startsWith(directorio) && Files.exists(ruta)) {
				return Files.readAllBytes(ruta);
			}

			// Compatibilidad con las fotos antiguas guardadas en static
			Path antigua = Paths.get("src/main/resources/static").resolve(nombreDeLaFoto).normalize();
			if (Files.exists(antigua)) {
				return Files.readAllBytes(antigua);
			}
			return null;
		} catch (IOException e) {
			return null;
		}
	}

	public void borrarImagen(String nombreDeLaFoto) {
		if (nombreDeLaFoto == null || nombreDeLaFoto.trim().isEmpty()) {
			return;
		}
		try {
			Path ruta = directorio.resolve(nombreDeLaFoto).normalize();
			if (ruta.startsWith(directorio)) {
				Files.deleteIfExists(ruta);
			}
		} catch (IOException ignored) {
			// Si no se puede borrar, no impedimos el resto de la operacion.
		}
	}

	private String extensionDe(String nombreOriginal) {
		String extension = "jpg";
		if (nombreOriginal != null) {
			int punto = nombreOriginal.lastIndexOf('.');
			if (punto >= 0) {
				extension = nombreOriginal.substring(punto + 1).toLowerCase(Locale.ROOT);
			}
		}
		if (!extension.matches("png|jpg|jpeg|gif|webp")) {
			extension = "jpg";
		}
		return extension;
	}
}
