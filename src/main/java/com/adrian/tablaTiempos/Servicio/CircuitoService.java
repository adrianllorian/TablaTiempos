package com.adrian.tablaTiempos.Servicio;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adrian.tablaTiempos.IMPL.CircuitoImpl;
import com.adrian.tablaTiempos.modelo.Circuito;

@Service
public class CircuitoService {
	
	@Autowired
	private CircuitoImpl circuitoImpl;
	
	public List <Circuito> obtenerCircuitos() {
		return circuitoImpl.obtenerCircuitos();
	}

}
