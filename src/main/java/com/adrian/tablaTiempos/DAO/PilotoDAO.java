package com.adrian.tablaTiempos.DAO;

import java.util.List;

import com.adrian.tablaTiempos.modelo.Piloto;

public interface PilotoDAO {
	
	String guardarPiloto(Piloto piloto);
	String cambiarcontraseña(Piloto piloto);
	List <Piloto> listarPilotos();
	Piloto buscarPiloto(String nombre);

}
