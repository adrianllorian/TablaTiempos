package com.adrian.tablaTiempos.Servicio;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Locale;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.adrian.tablaTiempos.JPA.AjustesJPA;
import com.adrian.tablaTiempos.modelo.Ajustes;
import com.adrian.tablaTiempos.modelo.AjustesDto;
import com.adrian.tablaTiempos.modelo.ArchivoDto;

@Service
public class AjustesService {

	public static final String TITULO_POR_DEFECTO = "Tabla de Tiempos";
	private static final long MAX_IMAGEN_BYTES = 2 * 1024 * 1024;

	@Autowired
	private AjustesJPA ajustesJPA;

	@Value("${app.upload-dir:uploads}")
	private String carpetaSubidas;

	private Path directorioAjustes;

	public AjustesDto obtenerAjustes() {
		Ajustes ajustes = ajustesJPA.findById(1L).orElse(null);
		String titulo = ajustes == null || ajustes.getTitulo() == null || ajustes.getTitulo().trim().isEmpty()
				? TITULO_POR_DEFECTO
				: ajustes.getTitulo();
		String version = ajustes == null ? "0" : Long.toString(ajustes.getVersion());

		String logoUrl = ajustes != null && ajustes.getNombreLogo() != null
				&& existeArchivo(ajustes.getNombreLogo())
				? "/settings/logo?v=" + version
				: null;
		String fondoUrl = ajustes != null && ajustes.getNombreFondo() != null
				&& existeArchivo(ajustes.getNombreFondo())
				? "/settings/fondo?v=" + version
				: null;

		return new AjustesDto(titulo, logoUrl, fondoUrl);
	}

	@Transactional
	public AjustesDto guardarAjustes(String titulo, MultipartFile logo, MultipartFile fondo,
			boolean quitarLogo, boolean quitarFondo) {
		Ajustes ajustes = ajustesJPA.findById(1L).orElseGet(() -> {
			Ajustes nuevo = new Ajustes();
			nuevo.setId(1L);
			return nuevo;
		});

		String tituloLimpio = titulo == null ? "" : titulo.trim();
		if (tituloLimpio.length() > 120) {
			throw new IllegalArgumentException("El titulo no puede superar 120 caracteres");
		}
		ajustes.setTitulo(tituloLimpio.isEmpty() ? TITULO_POR_DEFECTO : tituloLimpio);

		if (quitarLogo) {
			borrarArchivo(ajustes.getNombreLogo());
			ajustes.setNombreLogo(null);
			ajustes.setTipoLogo(null);
		}
		if (logo != null && !logo.isEmpty()) {
			ArchivoGuardado guardado = guardarArchivo(logo, "logo");
			borrarArchivo(ajustes.getNombreLogo());
			ajustes.setNombreLogo(guardado.getNombre());
			ajustes.setTipoLogo(guardado.getTipoContenido());
		}

		if (quitarFondo) {
			borrarArchivo(ajustes.getNombreFondo());
			ajustes.setNombreFondo(null);
			ajustes.setTipoFondo(null);
		}
		if (fondo != null && !fondo.isEmpty()) {
			ArchivoGuardado guardado = guardarArchivo(fondo, "fondo");
			borrarArchivo(ajustes.getNombreFondo());
			ajustes.setNombreFondo(guardado.getNombre());
			ajustes.setTipoFondo(guardado.getTipoContenido());
		}

		ajustes.setVersion(ajustes.getVersion() + 1);
		ajustesJPA.save(ajustes);
		return obtenerAjustes();
	}

	public ArchivoDto obtenerLogo() {
		Ajustes ajustes = ajustesJPA.findById(1L).orElse(null);
		if (ajustes == null || ajustes.getNombreLogo() == null) {
			return null;
		}
		return obtenerArchivo(ajustes.getNombreLogo(), ajustes.getTipoLogo());
	}

	public ArchivoDto obtenerFondo() {
		Ajustes ajustes = ajustesJPA.findById(1L).orElse(null);
		if (ajustes == null || ajustes.getNombreFondo() == null) {
			return null;
		}
		return obtenerArchivo(ajustes.getNombreFondo(), ajustes.getTipoFondo());
	}

	private ArchivoDto obtenerArchivo(String nombre, String tipoContenido) {
		Path ruta = directorio().resolve(nombre).normalize();
		if (!ruta.startsWith(directorio()) || !Files.exists(ruta)) {
			return null;
		}
		return new ArchivoDto(ruta, tipoContenido == null ? "application/octet-stream" : tipoContenido);
	}

	private boolean existeArchivo(String nombre) {
		if (nombre == null || nombre.trim().isEmpty()) {
			return false;
		}
		Path ruta = directorio().resolve(nombre).normalize();
		return ruta.startsWith(directorio()) && Files.exists(ruta);
	}

	private ArchivoGuardado guardarArchivo(MultipartFile archivo, String prefijo) {
		if (archivo.getSize() > MAX_IMAGEN_BYTES) {
			throw new IllegalArgumentException("La imagen no puede superar 2 MB");
		}
		String tipoContenido = archivo.getContentType() == null
				? ""
				: archivo.getContentType().toLowerCase(Locale.ROOT);
		if (!tipoContenido.startsWith("image/")) {
			throw new IllegalArgumentException("El archivo debe ser una imagen");
		}

		String extension = extension(tipoContenido, archivo.getOriginalFilename());
		String nombre = prefijo + "-" + UUID.randomUUID() + "." + extension;
		try {
			Files.copy(archivo.getInputStream(), directorio().resolve(nombre));
		} catch (IOException e) {
			throw new IllegalArgumentException("No se pudo guardar la imagen");
		}
		return new ArchivoGuardado(nombre, tipoContenido);
	}

	private String extension(String tipoContenido, String nombreOriginal) {
		switch (tipoContenido) {
		case "image/png":
			return "png";
		case "image/jpeg":
		case "image/jpg":
			return "jpg";
		case "image/gif":
			return "gif";
		case "image/webp":
			return "webp";
		case "image/svg+xml":
			return "svg";
		default:
			break;
		}
		if (nombreOriginal != null) {
			int punto = nombreOriginal.lastIndexOf('.');
			if (punto >= 0) {
				String ext = nombreOriginal.substring(punto + 1).toLowerCase(Locale.ROOT);
				if (ext.matches("png|jpg|jpeg|gif|webp|svg")) {
					return "jpeg".equals(ext) ? "jpg" : ext;
				}
			}
		}
		return "png";
	}

	private void borrarArchivo(String nombre) {
		if (nombre == null || nombre.trim().isEmpty()) {
			return;
		}
		try {
			Path ruta = directorio().resolve(nombre).normalize();
			if (ruta.startsWith(directorio())) {
				Files.deleteIfExists(ruta);
			}
		} catch (IOException ignored) {
			// Si no se puede borrar el antiguo, no impedimos guardar el nuevo.
		}
	}

	private Path directorio() {
		if (directorioAjustes == null) {
			directorioAjustes = Paths.get(carpetaSubidas, "settings").toAbsolutePath().normalize();
			try {
				Files.createDirectories(directorioAjustes);
			} catch (IOException e) {
				throw new IllegalStateException("No se pudo crear la carpeta de ajustes", e);
			}
		}
		return directorioAjustes;
	}

	private static class ArchivoGuardado {

		private final String nombre;
		private final String tipoContenido;

		ArchivoGuardado(String nombre, String tipoContenido) {
			this.nombre = nombre;
			this.tipoContenido = tipoContenido;
		}

		String getNombre() {
			return nombre;
		}

		String getTipoContenido() {
			return tipoContenido;
		}
	}
}
