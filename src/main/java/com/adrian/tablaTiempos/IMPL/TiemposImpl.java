package com.adrian.tablaTiempos.IMPL;

import java.util.Collections;
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
			salida = "Tiempo guardado con exito";
		} catch (Exception e) {
			salida = "Ha habido un error al guardar el Tiempo";
		}
		return salida;
	}

	@Override
	public List<Tiempos> listarTodosTiempos() {
		return tiemposJPA.findAll();
	}

	@Override
	public List<Tiempos> listarTodosTiemposPorCircuito(Tiempos tiempo) {
		if (tiempo == null || tiempo.getCircuito() == null) {
			return Collections.emptyList();
		}
		return tiemposJPA.buscarPorCircuito(tiempo.getCircuito().getNombreCircuito());
	}

	@Override
	public List<Tiempos> listarTodosTiemposPorPiloto(Tiempos tiempo) {
		if (tiempo == null || tiempo.getPiloto() == null) {
			return Collections.emptyList();
		}
		return tiemposJPA.buscarPorPiloto(tiempo.getPiloto().getNombre());
	}

	@Override
	public List<Tiempos> listarTodosTiemposPorCoche(Tiempos tiempo) {
		if (tiempo == null) {
			return Collections.emptyList();
		}
		return tiemposJPA.buscarPorCoche(tiempo.getCoche());
	}

	@Override
	public List<Tiempos> listarTodosTiemposPorCircuitoYPiloto(Tiempos tiempo) {
		if (tiempo == null || tiempo.getCircuito() == null || tiempo.getPiloto() == null) {
			return Collections.emptyList();
		}
		return tiemposJPA.buscarPorCircuitoYPiloto(
				tiempo.getCircuito().getNombreCircuito(), tiempo.getPiloto().getNombre());
	}

	@Override
	public List<Tiempos> listarTodosTiemposPorCircuitoYCoche(Tiempos tiempo) {
		if (tiempo == null || tiempo.getCircuito() == null) {
			return Collections.emptyList();
		}
		return tiemposJPA.buscarPorCircuitoYCoche(
				tiempo.getCircuito().getNombreCircuito(), tiempo.getCoche());
	}

	@Override
	public List<Tiempos> listarTodosTiemposPorPilotoYCoche(Tiempos tiempo) {
		if (tiempo == null || tiempo.getPiloto() == null) {
			return Collections.emptyList();
		}
		return tiemposJPA.buscarPorPilotoYCoche(
				tiempo.getPiloto().getNombre(), tiempo.getCoche());
	}

	@Override
	public List<Tiempos> listarTodosTiemposPorCircuitoYPilotoYCoche(Tiempos tiempo) {
		if (tiempo == null || tiempo.getCircuito() == null || tiempo.getPiloto() == null) {
			return Collections.emptyList();
		}
		return tiemposJPA.buscarPorCircuitoYPilotoYCoche(
				tiempo.getCircuito().getNombreCircuito(), tiempo.getPiloto().getNombre(), tiempo.getCoche());
	}

	@Override
	public List<Tiempos> listarPorIdTiempo(Tiempos tiempo) {
		if (tiempo == null || tiempo.getId() == null) {
			return Collections.emptyList();
		}
		return tiemposJPA.findById(tiempo.getId()).map(List::of).orElse(Collections.emptyList());
	}

	public Tiempos buscarPorId(Integer id) {
		if (id == null) {
			return null;
		}
		return tiemposJPA.findById(id).orElse(null);
	}

	public String borrarTiempo(Integer id) {
		if (id == null || !tiemposJPA.existsById(id)) {
			return "El tiempo no existe";
		}
		tiemposJPA.deleteById(id);
		return "Tiempo borrado con exito";
	}
}
