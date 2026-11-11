package com.adrian.tablaTiempos.modelo;

import java.nio.file.Path;

public class ArchivoDto {

	private Path ruta;
	private String tipoContenido;

	public ArchivoDto(Path ruta, String tipoContenido) {
		this.ruta = ruta;
		this.tipoContenido = tipoContenido;
	}

	public Path getRuta() {
		return ruta;
	}

	public void setRuta(Path ruta) {
		this.ruta = ruta;
	}

	public String getTipoContenido() {
		return tipoContenido;
	}

	public void setTipoContenido(String tipoContenido) {
		this.tipoContenido = tipoContenido;
	}
}
