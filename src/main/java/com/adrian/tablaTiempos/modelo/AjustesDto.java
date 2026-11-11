package com.adrian.tablaTiempos.modelo;

public class AjustesDto {

	private String titulo;
	private String logoUrl;
	private String fondoUrl;

	public AjustesDto() {
	}

	public AjustesDto(String titulo, String logoUrl, String fondoUrl) {
		this.titulo = titulo;
		this.logoUrl = logoUrl;
		this.fondoUrl = fondoUrl;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getLogoUrl() {
		return logoUrl;
	}

	public void setLogoUrl(String logoUrl) {
		this.logoUrl = logoUrl;
	}

	public String getFondoUrl() {
		return fondoUrl;
	}

	public void setFondoUrl(String fondoUrl) {
		this.fondoUrl = fondoUrl;
	}
}
