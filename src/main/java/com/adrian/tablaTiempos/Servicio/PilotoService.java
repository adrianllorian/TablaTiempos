package com.adrian.tablaTiempos.Servicio;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.adrian.tablaTiempos.IMPL.PilotoImpl;
import com.adrian.tablaTiempos.modelo.Piloto;

@Service
public class PilotoService {
	
	@Autowired
	private PilotoImpl pilotoImpl;
	
	BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	
	public String guardarPiloto(Piloto piloto) {	
		String salida = null;
		List <Piloto> listaPilotos = pilotoImpl.listarPilotos();
		
		piloto.setPass(passwordEncoder.encode(piloto.getPass()));
		salida = pilotoImpl.guardarPiloto(piloto);
		if(!listaPilotos.isEmpty()) {
			for(Piloto item: listaPilotos) {
				if(item.getNombre().equals(piloto.getNombre())) {
					salida = "El nombre ya esta en uso";
				}	
			}
		}else {
			salida = pilotoImpl.guardarPiloto(piloto);
		}
		return salida;
	}
	
	public String cambiarContraseña(Piloto piloto) {
		piloto.setPass(passwordEncoder.encode(piloto.getPass()));
		return pilotoImpl.cambiarcontraseña(piloto);
	}
}
