package com.adrian.tablaTiempos.utils;

import java.awt.Desktop;
import java.net.URI;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * Abre la aplicacion en el navegador al arrancar, para que el usuario no tenga
 * que escribir la direccion a mano. Se puede desactivar con ABRIR_NAVEGADOR=false.
 */
@Component
public class AbrirNavegador implements ApplicationRunner {

	@Value("${server.port:8080}")
	private String puerto;

	@Value("${app.abrir-navegador:true}")
	private boolean abrir;

	@Override
	public void run(ApplicationArguments args) {
		if (!abrir) {
			return;
		}

		String url = "http://localhost:" + puerto;

		System.out.println("Abriendo la aplicacion en el navegador: " + url);

		try {
			if (Desktop.isDesktopSupported()
					&& Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
				Desktop.getDesktop().browse(new URI(url));
				return;
			}
		} catch (Exception e) {
			// Si el escritorio no esta disponible, se prueba con el comando del sistema
		}

		abrirConElSistema(url);
	}

	private void abrirConElSistema(String url) {
		try {
			String sistema = System.getProperty("os.name", "").toLowerCase();
			if (sistema.contains("win")) {
				Runtime.getRuntime().exec(new String[] { "rundll32", "url.dll,FileProtocolHandler", url });
			} else if (sistema.contains("mac")) {
				Runtime.getRuntime().exec(new String[] { "open", url });
			} else {
				Runtime.getRuntime().exec(new String[] { "xdg-open", url });
			}
		} catch (Exception e) {
			System.out.println("Abre la aplicacion en el navegador: " + url);
		}
	}
}
