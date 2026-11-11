package com.adrian.tablaTiempos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.adrian.tablaTiempos.utils.ConfiguracionDatos;


@SpringBootApplication
public class TablaTiemposApplication {

	public static void main(String[] args) {
		ConfiguracionDatos.configurar();
		SpringApplication.run(TablaTiemposApplication.class, args);
	}


}
