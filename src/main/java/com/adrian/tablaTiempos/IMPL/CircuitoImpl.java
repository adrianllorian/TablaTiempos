package com.adrian.tablaTiempos.IMPL;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.adrian.tablaTiempos.DAO.CircuitoDAO;
import com.adrian.tablaTiempos.JPA.CircuitoJPA;
import com.adrian.tablaTiempos.modelo.Circuito;

@Repository
public class CircuitoImpl implements CircuitoDAO {
	
	@Autowired
	private CircuitoJPA circuitoJPA;

	@Override
	public List<Circuito> obtenerCircuitos() {
		
		return circuitoJPA.findAll();
	}

	@Override
	public Circuito buscarCircuito(String nombreCircuito) {
		// TODO Auto-generated method stub
		return circuitoJPA.findById(nombreCircuito).get();
	}

}
