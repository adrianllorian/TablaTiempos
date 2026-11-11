package com.adrian.tablaTiempos.Servicio;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.adrian.tablaTiempos.IMPL.CircuitoImpl;
import com.adrian.tablaTiempos.IMPL.PilotoImpl;
import com.adrian.tablaTiempos.IMPL.TiemposImpl;
import com.adrian.tablaTiempos.modelo.Circuito;
import com.adrian.tablaTiempos.modelo.Piloto;
import com.adrian.tablaTiempos.modelo.Tiempos;
import com.adrian.tablaTiempos.utils.FuncionesGenericas;
import com.adrian.tablaTiempos.utils.GuardarImagen;

@Service
public class TiemposService {

	@Autowired
	private TiemposImpl tiempoImpl;
	
	@Autowired
	private PilotoImpl pilotoImpl;
	
	@Autowired
	private CircuitoImpl circuitoImpl;
	
	@Autowired
	private FuncionesGenericas funcionesGenericas;
	
	@Autowired
	private GuardarImagen guardarImagen;
	
	public String añadirTiempo(Tiempos tiempo, MultipartFile foto) {
		if (tiempo == null || tiempo.getCircuito() == null || tiempo.getPiloto() == null
				|| tiempo.getCircuito().getNombreCircuito() == null
				|| tiempo.getPiloto().getNombre() == null) {
			return "Faltan datos del tiempo";
		}
		if (tiempo.getTiempo() == null || !tiempo.getTiempo().matches("\\d{1,2}:\\d{1,2}(\\.\\d{1,3})?")) {
			return "El tiempo no tiene un formato valido";
		}

		Circuito circuito = circuitoImpl.buscarCircuito(tiempo.getCircuito().getNombreCircuito());
		Piloto piloto = pilotoImpl.buscarPiloto(tiempo.getPiloto().getNombre());

		if (circuito == null) {
			return "El circuito no existe";
		}
		if (piloto == null) {
			return "El piloto no existe";
		}

		tiempo.setCircuito(circuito);
		tiempo.setPiloto(piloto);

		// Al editar, si no se sube foto nueva se conserva la anterior
		Tiempos existente = null;
		if (tiempo.getId() != null) {
			existente = tiempoImpl.buscarPorId(tiempo.getId());
			if (existente == null) {
				return "El tiempo no existe";
			}
			if (foto == null || foto.isEmpty()) {
				tiempo.setFoto(existente.getFoto());
				tiempo.setImagen(existente.getImagen());
			}
		}

		if (foto != null && !foto.isEmpty()) {
			String nombreFoto = guardarImagen.guardarImagen(foto);
			if (nombreFoto == null) {
				return "No se pudo guardar la foto";
			}
			tiempo.setFoto(nombreFoto);
		}

		String resultado = tiempoImpl.añadirTiempo(tiempo);

		// Si se ha cambiado la foto, borrar la antigua del disco
		if (resultado != null && resultado.toLowerCase().contains("exito")
				&& existente != null && existente.getFoto() != null
				&& !existente.getFoto().equals(tiempo.getFoto())) {
			guardarImagen.borrarImagen(existente.getFoto());
		}
		return resultado;
	}
	
	public List<Tiempos> listarTodosTiempos() {
		return funcionesGenericas.ordenarTiempos(tiempoImpl.listarTodosTiempos());
	}
	
	public List<Tiempos> listarPorIdTiempo(Tiempos tiempo) {
		if (tiempo == null || tiempo.getId() == null) {
			return Collections.emptyList();
		}
		return funcionesGenericas.ordenarTiempos(tiempoImpl.listarPorIdTiempo(tiempo));
	}

	public Tiempos buscarPorId(Integer id) {
		return tiempoImpl.buscarPorId(id);
	}

	public String borrarTiempo(Integer id) {
		Tiempos existente = tiempoImpl.buscarPorId(id);
		String resultado = tiempoImpl.borrarTiempo(id);
		if (existente != null && resultado != null && resultado.toLowerCase().contains("exito")
				&& existente.getFoto() != null) {
			guardarImagen.borrarImagen(existente.getFoto());
		}
		return resultado;
	}
	
	public List<Tiempos> listarTodosTiemposPorCircuito(Tiempos tiempo) {
		if (tiempo == null || tiempo.getCircuito() == null || tiempo.getCircuito().getNombreCircuito() == null) {
			return Collections.emptyList();
		}
		Circuito circuito = circuitoImpl.buscarCircuito(tiempo.getCircuito().getNombreCircuito());
		if (circuito == null) {
			return Collections.emptyList();
		}
		tiempo.setCircuito(circuito);
		return funcionesGenericas.ordenarTiempos(tiempoImpl.listarTodosTiemposPorCircuito(tiempo));
	}
	
	public List<Tiempos> listarTodosTiemposPorPiloto(Tiempos tiempo) {
		if (tiempo == null || tiempo.getPiloto() == null || tiempo.getPiloto().getNombre() == null) {
			return Collections.emptyList();
		}
		Piloto piloto = pilotoImpl.buscarPiloto(tiempo.getPiloto().getNombre());
		if (piloto == null) {
			return Collections.emptyList();
		}
		tiempo.setPiloto(piloto);
		return funcionesGenericas.ordenarTiempos(tiempoImpl.listarTodosTiemposPorPiloto(tiempo));
	}
	
	public List<Tiempos> listarTodosTiemposPorCoche(Tiempos tiempo) {
		if (tiempo == null || tiempo.getCoche() == null) {
			return Collections.emptyList();
		}
		return funcionesGenericas.ordenarTiempos(tiempoImpl.listarTodosTiemposPorCoche(tiempo));
	}
	
	public List<Tiempos> listarTodosTiemposPorCircuitoYPiloto(Tiempos tiempo) {
		if (tiempo == null || tiempo.getCircuito() == null || tiempo.getPiloto() == null) {
			return Collections.emptyList();
		}
		Circuito circuito = circuitoImpl.buscarCircuito(tiempo.getCircuito().getNombreCircuito());
		Piloto piloto = pilotoImpl.buscarPiloto(tiempo.getPiloto().getNombre());
		if (circuito == null || piloto == null) {
			return Collections.emptyList();
		}
		tiempo.setCircuito(circuito);
		tiempo.setPiloto(piloto);
		return funcionesGenericas.ordenarTiempos(tiempoImpl.listarTodosTiemposPorCircuitoYPiloto(tiempo));
	}
	
	public List<Tiempos> listarTodosTiemposPorCircuitoYCoche(Tiempos tiempo) {
		if (tiempo == null || tiempo.getCircuito() == null || tiempo.getCoche() == null) {
			return Collections.emptyList();
		}
		Circuito circuito = circuitoImpl.buscarCircuito(tiempo.getCircuito().getNombreCircuito());
		if (circuito == null) {
			return Collections.emptyList();
		}
		tiempo.setCircuito(circuito);
		return funcionesGenericas.ordenarTiempos(tiempoImpl.listarTodosTiemposPorCircuitoYCoche(tiempo));
	}
	
	public List<Tiempos> listarTodosTiemposPorPilotoYCoche(Tiempos tiempo) {
		if (tiempo == null || tiempo.getPiloto() == null || tiempo.getCoche() == null) {
			return Collections.emptyList();
		}
		Piloto piloto = pilotoImpl.buscarPiloto(tiempo.getPiloto().getNombre());
		if (piloto == null) {
			return Collections.emptyList();
		}
		tiempo.setPiloto(piloto);
		return funcionesGenericas.ordenarTiempos(tiempoImpl.listarTodosTiemposPorPilotoYCoche(tiempo));
	}
	
	public List<Tiempos> listarTodosTiemposPorCircuitoYPilotoYCoche(Tiempos tiempo) {
		if (tiempo == null || tiempo.getCircuito() == null || tiempo.getPiloto() == null || tiempo.getCoche() == null) {
			return Collections.emptyList();
		}
		Circuito circuito = circuitoImpl.buscarCircuito(tiempo.getCircuito().getNombreCircuito());
		Piloto piloto = pilotoImpl.buscarPiloto(tiempo.getPiloto().getNombre());
		if (circuito == null || piloto == null) {
			return Collections.emptyList();
		}
		tiempo.setCircuito(circuito);
		tiempo.setPiloto(piloto);
		return funcionesGenericas.ordenarTiempos(tiempoImpl.listarTodosTiemposPorCircuitoYPilotoYCoche(tiempo));
	}
}
