package com.adrian.tablaTiempos.modelo;


import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;



@Entity
public class Tiempos {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column
	private String tiempo;
	
	@Column
	private String coche;
	
	@Column
	private String diferencia;
	
	@Column
	private String foto;
	
	@Column
	private byte [] imagen;
	

	@ManyToOne(cascade = CascadeType.ALL)
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	private Piloto piloto;
	
	@ManyToOne (cascade = CascadeType.ALL)
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	private Circuito circuito;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTiempo() {
		return tiempo;
	}

	public void setTiempo(String tiempo) {
		this.tiempo = tiempo;
	}

	public String getCoche() {
		return coche;
	}

	public void setCoche(String coche) {
		this.coche = coche;
	}

	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}

	public Piloto getPiloto() {
		return piloto;
	}

	public void setPiloto(Piloto piloto) {
		this.piloto = piloto;
	}

	public Circuito getCircuito() {
		return circuito;
	}

	public void setCircuito(Circuito circuito) {
		this.circuito = circuito;
	}

	public String getDiferencia() {
		return diferencia;
	}

	public void setDiferencia(String direrencias) {
		this.diferencia = direrencias;
	}
	
	public byte[] getImagen() {
		return imagen;
	}

	public void setImagen(byte[] imagen) {
		this.imagen = imagen;
	}

	@Override
	public String toString() {
		return "Tiempos [id=" + id + ", tiempo=" + tiempo + ", coche=" + coche + ", foto=" + foto + ", diferencia="+ diferencia +", piloto=" + piloto
				+ ", circuito=" + circuito + "]";
	}
	
	
}
