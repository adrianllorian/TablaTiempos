package com.adrian.tablaTiempos.IMPL;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.adrian.tablaTiempos.DAO.TiemposDAO;
import com.adrian.tablaTiempos.JPA.TiemposJPA;
import com.adrian.tablaTiempos.modelo.Tiempos;

@Repository
public class TiemposImpl implements TiemposDAO {
	
	@Autowired
	private TiemposJPA tiemposJPA;

	@Override
	public String añadirTiempo(Tiempos tiempo) {
		String salida = "Ha habido un error al guardar el Tiempo";
		try {
			tiemposJPA.save(tiempo);
			salida= "Tiempo guardao con exito";
		}catch(Exception e) {
			salida = "Ha habido un error al guardar el Tiempo";
		}
		return salida;
	}

	@Override
	public List<Tiempos> listarTodosTiempos() {
		// TODO Auto-generated method stub
		return tiemposJPA.findAll();
	}

	@Override
	public List<Tiempos> listarTodosTiemposPorCircuito(Tiempos tiempo) {
		// TODO Auto-generated method stub
		return tiemposJPA.buscarPorCircuito(tiempo.getCircuito().getNombreCircuito());
	}

	@Override
	public List<Tiempos> listarTodosTiemposPorPiloto(Tiempos tiempo) {
		// TODO Auto-generated method stub
		return tiemposJPA.buscarPorPiloto(tiempo.getPiloto().getNombre());
	}

	@Override
	public List<Tiempos> listarTodosTiemposPorCoche(Tiempos tiempo) {
		// TODO Auto-generated method stub
		return tiemposJPA.buscarPorCoche(tiempo.getCoche());
	}

	@Override
	public List<Tiempos> listarTodosTiemposPorCircuitoYPiloto(Tiempos tiempo) {
		// TODO Auto-generated method stub
		return tiemposJPA.buscarPorCircuitoYPiloto(tiempo.getCircuito().getNombreCircuito(), tiempo.getPiloto().getNombre());
	}

	@Override
	public List<Tiempos> listarTodosTiemposPorCircuitoYCoche(Tiempos tiempo) {
		// TODO Auto-generated method stub
		return tiemposJPA.buscarPorCircuitoYCoche(tiempo.getCircuito().getNombreCircuito(), tiempo.getCoche());
	}

	@Override
	public List<Tiempos> listarTodosTiemposPorPilotoYCoche(Tiempos tiempo) {
		// TODO Auto-generated method stub
		return tiemposJPA.buscarPorCircuitoYPilotoYCoche(tiempo.getCoche(), tiempo.getPiloto().getNombre(), tiempo.getPiloto().getNombre());
	}

	@Override
	public List<Tiempos> listarTodosTiemposPorCircuitoYPilotoYCoche(Tiempos tiempo) {
		// TODO Auto-generated method stub
		return tiemposJPA.buscarPorCircuitoYPilotoYCoche(tiempo.getCircuito().getNombreCircuito(), tiempo.getPiloto().getNombre(), tiempo.getCoche());
	}

	@Override
	public List<Tiempos> listarPorIdTiempo(Tiempos tiempo) {
		// TODO Auto-generated method stub
		return tiemposJPA.listarPorIdTiempo(tiempo.getId());
	}

}
