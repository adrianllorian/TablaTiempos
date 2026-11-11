package com.adrian.tablaTiempos.modelo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Ajustes de la aplicacion: titulo, logo y foto de fondo.
 * La tabla conserva el nombre app_settings para no perder los datos.
 */
@Entity
@Table(name = "app_settings")
public class Ajustes {

	@Id
	private Long id = 1L;

	@Column(length = 120)
	private String titulo;

	@Column(name = "logo_file_name", length = 200)
	private String nombreLogo;

	@Column(name = "logo_content_type", length = 100)
	private String tipoLogo;

	@Column(name = "fondo_file_name", length = 200)
	private String nombreFondo;

	@Column(name = "fondo_content_type", length = 100)
	private String tipoFondo;

	@Column
	private long version;

	@Column(name = "jwt_secret", length = 200)
	private String claveJwt;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getNombreLogo() {
		return nombreLogo;
	}

	public void setNombreLogo(String nombreLogo) {
		this.nombreLogo = nombreLogo;
	}

	public String getTipoLogo() {
		return tipoLogo;
	}

	public void setTipoLogo(String tipoLogo) {
		this.tipoLogo = tipoLogo;
	}

	public String getNombreFondo() {
		return nombreFondo;
	}

	public void setNombreFondo(String nombreFondo) {
		this.nombreFondo = nombreFondo;
	}

	public String getTipoFondo() {
		return tipoFondo;
	}

	public void setTipoFondo(String tipoFondo) {
		this.tipoFondo = tipoFondo;
	}

	public long getVersion() {
		return version;
	}

	public void setVersion(long version) {
		this.version = version;
	}

	public String getClaveJwt() {
		return claveJwt;
	}

	public void setClaveJwt(String claveJwt) {
		this.claveJwt = claveJwt;
	}
}
