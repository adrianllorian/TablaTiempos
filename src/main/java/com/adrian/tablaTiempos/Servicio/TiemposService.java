package com.adrian.tablaTiempos.Servicio;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.adrian.tablaTiempos.IMPL.CircuitoImpl;
import com.adrian.tablaTiempos.IMPL.PilotoImpl;
import com.adrian.tablaTiempos.IMPL.TiemposImpl;
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
		tiempo.setCircuito(circuitoImpl.buscarCircuito(tiempo.getCircuito().getNombreCircuito()));
		tiempo.setPiloto(pilotoImpl.buscarPiloto(tiempo.getPiloto().getNombre()));
		if(!foto.equals(null)) {
			tiempo.setFoto(guardarImagen.guardarImagen(foto));
		}
		
		return tiempoImpl.añadirTiempo(tiempo);
	}
	
	public List <Tiempos> listarTodosTiempos(){
		return funcionesGenericas.ordenarTiempos(tiempoImpl.listarTodosTiempos());
	}
	
	public List <Tiempos>  listarPorIdTiempo(Tiempos tiempo){
		return funcionesGenericas.ordenarTiempos(tiempoImpl.listarPorIdTiempo(tiempo));
	}
	
	
	
	public List <Tiempos>  listarTodosTiemposPorCircuito(Tiempos tiempo){
		tiempo.setCircuito(circuitoImpl.buscarCircuito(tiempo.getCircuito().getNombreCircuito()));
		return funcionesGenericas.ordenarTiempos(tiempoImpl.listarTodosTiemposPorCircuito(tiempo));
	}
	
	public List <Tiempos>  listarTodosTiemposPorPiloto(Tiempos tiempo){
		tiempo.setPiloto(pilotoImpl.buscarPiloto(tiempo.getPiloto().getNombre()));
		return funcionesGenericas.ordenarTiempos(tiempoImpl.listarTodosTiemposPorPiloto(tiempo));
	}
	
	public List <Tiempos>  listarTodosTiemposPorCoche(Tiempos tiempo){
		return funcionesGenericas.ordenarTiempos(tiempoImpl.listarTodosTiemposPorCoche(tiempo));
	}
	
	public List <Tiempos>  listarTodosTiemposPorCircuitoYPiloto(Tiempos tiempo){
		tiempo.setCircuito(circuitoImpl.buscarCircuito(tiempo.getCircuito().getNombreCircuito()));
		tiempo.setPiloto(pilotoImpl.buscarPiloto(tiempo.getPiloto().getNombre()));
		return funcionesGenericas.ordenarTiempos(tiempoImpl.listarTodosTiemposPorCircuitoYPiloto(tiempo));
	}
	
	public List <Tiempos>  listarTodosTiemposPorCircuitoYCoche(Tiempos tiempo){
		tiempo.setCircuito(circuitoImpl.buscarCircuito(tiempo.getCircuito().getNombreCircuito()));
		return funcionesGenericas.ordenarTiempos(tiempoImpl.listarTodosTiemposPorCircuitoYCoche(tiempo));
	}
	
	public List <Tiempos>  listarTodosTiemposPorPilotoYCoche(Tiempos tiempo){
		tiempo.setPiloto(pilotoImpl.buscarPiloto(tiempo.getPiloto().getNombre()));
		return funcionesGenericas.ordenarTiempos(tiempoImpl.listarTodosTiemposPorPilotoYCoche(tiempo));
	}
	
	public List <Tiempos>  listarTodosTiemposPorCircuitoYPilotoYCoche(Tiempos tiempo){
		tiempo.setCircuito(circuitoImpl.buscarCircuito(tiempo.getCircuito().getNombreCircuito()));
		tiempo.setPiloto(pilotoImpl.buscarPiloto(tiempo.getPiloto().getNombre()));
		return funcionesGenericas.ordenarTiempos(tiempoImpl.listarTodosTiemposPorPilotoYCoche(tiempo));
	}
	
}
