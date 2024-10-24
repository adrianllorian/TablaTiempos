package com.adrian.tablaTiempos.IMPL;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.adrian.tablaTiempos.DAO.PilotoDAO;
import com.adrian.tablaTiempos.JPA.PilotoJPA;
import com.adrian.tablaTiempos.modelo.Piloto;

@Repository
public class PilotoImpl implements PilotoDAO{
	
	@Autowired
	private PilotoJPA pilotoJPA;

	
	@Override
	public String guardarPiloto(Piloto piloto) {
		String salida = "No ha llegado correctamente el piloto al servidor";
		try {
			pilotoJPA.save(piloto);
			salida= "Piloto guardao con exito";
		}catch(Exception e) {
			salida = "Ha habido un error al guardar el Piloto";
		}
		return salida;
	}


	@Override
	public String cambiarcontraseña(Piloto piloto) {
		String salida = "No ha llegado correctamente el piloto al servidor";
		try {
			pilotoJPA.save(piloto);
			salida= "Contraseña cambiada con exito";
		}catch(Exception e) {
			salida = "Ha habido un error al guardar el Piloto";
		}
		return salida;
	}


	@Override
	public List<Piloto> listarPilotos() {
		// TODO Auto-generated method stub
		return pilotoJPA.findAll();
	}


	@Override
	public Piloto buscarPiloto(String nombre) {
		// TODO Auto-generated method stub
		return pilotoJPA.findById(nombre).get();
	}
	

}
