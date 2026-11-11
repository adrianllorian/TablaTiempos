package com.adrian.tablaTiempos.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Guarda todos los datos de la aplicacion en la carpeta "data" que hay junto
 * al JAR. Asi la aplicacion se puede mover de sitio completa: si se mueve
 * tambien la carpeta "data" no se pierde nada, y si se mueve solo el JAR se
 * empieza de cero.
 */
public class ConfiguracionDatos {

	public static void configurar() {
		Path carpeta = carpetaAplicacion().resolve("data").toAbsolutePath().normalize();

		try {
			Files.createDirectories(carpeta);
		} catch (IOException e) {
			System.out.println("No se puede crear la carpeta de datos: " + carpeta);
			System.out.println("Mueve el JAR a una carpeta donde tengas permisos de escritura.");
			System.exit(1);
		}

		Path baseDatos = carpeta.resolve("tabla-tiempo");
		Path fotos = carpeta.resolve("uploads");

		// Los system properties tienen prioridad sobre application.properties
		System.setProperty("spring.datasource.url",
				"jdbc:h2:file:" + baseDatos.toString().replace('\\', '/') + ";WRITE_DELAY=0");
		System.setProperty("app.upload-dir", fotos.toString());

		System.out.println("Carpeta de datos: " + carpeta);
	}

	private static Path carpetaAplicacion() {
		// Cuando se ejecuta "java -jar": el classpath es solo el JAR
		String classpath = System.getProperty("java.class.path", "");
		String[] entradas = classpath.split(File.pathSeparator);
		if (entradas.length == 1 && entradas[0].toLowerCase().endsWith(".jar")) {
			return Paths.get(entradas[0]).toAbsolutePath().getParent();
		}

		// En desarrollo (clases sueltas) se usa la carpeta del proyecto
		return Paths.get(System.getProperty("user.dir")).toAbsolutePath();
	}
}
