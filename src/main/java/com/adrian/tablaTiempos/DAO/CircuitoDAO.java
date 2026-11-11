package com.adrian.tablaTiempos.DAO;

import java.util.List;

import com.adrian.tablaTiempos.modelo.Circuito;

public interface CircuitoDAO {

	List<Circuito>obtenerCircuitos();
	Circuito buscarCircuito(String nombreCircuito);
}
