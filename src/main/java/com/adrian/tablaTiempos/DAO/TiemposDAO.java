package com.adrian.tablaTiempos.DAO;

import java.util.List;

import com.adrian.tablaTiempos.modelo.Tiempos;


public interface TiemposDAO {
	
	String añadirTiempo(Tiempos tiempo);
	
	List <Tiempos> listarTodosTiempos();
	
	List <Tiempos> listarPorIdTiempo(Tiempos tiempo);
	
	List <Tiempos> listarTodosTiemposPorCircuito(Tiempos tiempo);
	List <Tiempos> listarTodosTiemposPorPiloto(Tiempos tiempo);
	List <Tiempos> listarTodosTiemposPorCoche(Tiempos tiempo);
	
	List <Tiempos> listarTodosTiemposPorCircuitoYPiloto(Tiempos tiempo);
	List <Tiempos> listarTodosTiemposPorCircuitoYCoche(Tiempos tiempo);
	
	List <Tiempos> listarTodosTiemposPorPilotoYCoche(Tiempos tiempo);
	
	List <Tiempos> listarTodosTiemposPorCircuitoYPilotoYCoche(Tiempos tiempo);
	

	
	
	
	
}
