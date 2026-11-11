package com.adrian.tablaTiempos.utils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.adrian.tablaTiempos.modelo.Tiempos;

@Repository
public class FuncionesGenericas {

	@Autowired
	private GuardarImagen guardaImagen;

	/**
	 * Ordena los tiempos de menor a mayor y calcula la diferencia (en
	 * milisegundos) respecto al primero.
	 */
	public List<Tiempos> ordenarTiempos(List<Tiempos> listaTiempos) {
		if (listaTiempos == null || listaTiempos.isEmpty()) {
			return new ArrayList<>();
		}

		List<Tiempos> ordenada = new ArrayList<>(listaTiempos);
		ordenada.sort(Comparator.comparingLong(this::tiempoEnMilisegundos));

		long primero = tiempoEnMilisegundos(ordenada.get(0));
		for (int i = 0; i < ordenada.size(); i++) {
			Tiempos tiempo = ordenada.get(i);
			if (i == 0) {
				tiempo.setDiferencia("");
			} else {
				tiempo.setDiferencia(Long.toString(tiempoEnMilisegundos(tiempo) - primero));
			}
			if (tiempo.getFoto() != null && !tiempo.getFoto().isEmpty()) {
				tiempo.setImagen(guardaImagen.verImagen(tiempo.getFoto()));
			}
		}
		return ordenada;
	}

	private long tiempoEnMilisegundos(Tiempos tiempo) {
		if (tiempo == null || tiempo.getTiempo() == null) {
			return Long.MAX_VALUE;
		}
		return parseTiempo(tiempo.getTiempo());
	}

	/**
	 * Convierte "m:ss.SSS" (o "mm:ss.SSS") a milisegundos.
	 */
	private long parseTiempo(String valor) {
		try {
			String[] partes = valor.trim().split(":");
			long minutos;
			String resto;
			if (partes.length == 2) {
				minutos = Long.parseLong(partes[0]);
				resto = partes[1];
			} else if (partes.length == 1) {
				minutos = 0;
				resto = partes[0];
			} else {
				return Long.MAX_VALUE;
			}

			String[] secMs = resto.split("\\.");
			long segundos = Long.parseLong(secMs[0]);
			long milis = 0;
			if (secMs.length > 1) {
				String ms = (secMs[1] + "000").substring(0, 3);
				milis = Long.parseLong(ms);
			}
			return ((minutos * 60) + segundos) * 1000 + milis;
		} catch (RuntimeException e) {
			return Long.MAX_VALUE;
		}
	}
}
