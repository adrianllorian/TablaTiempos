package com.adrian.tablaTiempos.modelo;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;



@Entity
public class Circuito {
	
	
	@Id
	private String nombreCircuito;

	@OneToMany(mappedBy = "circuito")
	@JsonIgnore
	private List<Tiempos> tiempos;



	public String getNombreCircuito() {
		return nombreCircuito;
	}

	public void setNombreCircuito(String nombreCircuito) {
		this.nombreCircuito = nombreCircuito;
	}

	public List<Tiempos> getTiempos() {
		return tiempos;
	}

	public void setTiempos(List<Tiempos> tiempos) {
		this.tiempos = tiempos;
	}

	@Override
	public String toString() {
		return "Circuito [nombreCircuito=" + nombreCircuito + ", tiempos=" + tiempos + "]";
	}
	
	
	

}
