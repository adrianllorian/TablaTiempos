package com.adrian.tablaTiempos.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.adrian.tablaTiempos.modelo.Tiempos;

@Repository
public class FuncionesGenericas {
	
	@Autowired
	private GuardarImagen guardaImagen;

	public List <Tiempos> ordenarTiempos(List<Tiempos> listaTiempos){
		String array []; 
		Double arrayTiempos [] = new Double[listaTiempos.size()];
		Double valor;
		int pasos =0;
		List <Tiempos> salida = new ArrayList <Tiempos>();
		
		if(!listaTiempos.isEmpty()) {
			for(Tiempos tiem: listaTiempos) {
					array = tiem.getTiempo().split(":");
					arrayTiempos[pasos] = Double.parseDouble(array[0]+array[1]);
					pasos++;			
				
			
		}
		 Arrays.sort(arrayTiempos);
		 for(int i = 0; i<arrayTiempos.length; i++) {
			 for(Tiempos tiempo1: listaTiempos) {
				 	array = tiempo1.getTiempo().split(":");
					valor = Double.parseDouble(array[0]+array[1]);
				 if(arrayTiempos[i].equals(valor)) {
					 tiempo1.setImagen(guardaImagen.verImagen(tiempo1.getFoto()));
					 salida.add(tiempo1);
				 }
			 }
		 }
		}
			return calculasDiferencias(salida);
		}
	
	public List <Tiempos> calculasDiferencias(List <Tiempos> listaTiempos){
		SimpleDateFormat formato = new SimpleDateFormat("mm:ss.SSS");
		Date primero = null;
		Date ahora = null;
		List <Tiempos> salida = new ArrayList <Tiempos>();
		Integer pasos = 0;
		String diferencia;
		
		for(Tiempos tiempo: listaTiempos) {
			String [] separtarMiliSec = tiempo.getTiempo().split("\\.");
			String [] separtarSecMin = separtarMiliSec[0].split(":");
			if(pasos.equals(0)) {
				try {
					primero= formato.parse(Integer.parseInt(separtarSecMin[0]) + ":" + Integer.parseInt(separtarSecMin[1]) + "." + Integer.parseInt(separtarMiliSec[1]));
					tiempo.setDiferencia("");				
					salida.add(tiempo);
					pasos++;
				} catch (NumberFormatException | ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			else {
				try {
					ahora= formato.parse(Integer.parseInt(separtarSecMin[0]) + ":" + Integer.parseInt(separtarSecMin[1]) + "." + Integer.parseInt(separtarMiliSec[1]));
					diferencia = Long.toString((ahora.getTime() - primero.getTime()));
					if(!salida.contains(tiempo)) {
						tiempo.setDiferencia(diferencia);
						salida.add(tiempo);					
					}
					pasos++;
				} catch (NumberFormatException | ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
			}
		}
		
		return salida;
	}

	
	}
