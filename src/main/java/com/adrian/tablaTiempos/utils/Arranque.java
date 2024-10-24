package com.adrian.tablaTiempos.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.adrian.tablaTiempos.JPA.CircuitoJPA;
import com.adrian.tablaTiempos.JPA.PilotoJPA;
import com.adrian.tablaTiempos.modelo.Circuito;
import com.adrian.tablaTiempos.modelo.Piloto;

@Component
public class Arranque implements ApplicationListener<ContextRefreshedEvent>{

	@Autowired
	private CircuitoJPA circuitoJPA;
	
	@Autowired
	private PilotoJPA pilotoJPA;
	
	BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		
		Piloto piloto1 = new Piloto();
		piloto1.setNombre("Bochi");
		piloto1.setPass("###");
		piloto1.setPass(passwordEncoder.encode(piloto1.getPass()));
		pilotoJPA.save(piloto1);
		
		Piloto piloto2 = new Piloto();
		piloto2.setNombre("Jose");
		piloto2.setPass("###");
		piloto2.setPass(passwordEncoder.encode(piloto2.getPass()));
		pilotoJPA.save(piloto2);
		
		Piloto piloto3 = new Piloto();
		piloto3.setNombre("Toni");
		piloto3.setPass("###");
		piloto3.setPass(passwordEncoder.encode(piloto3.getPass()));
		pilotoJPA.save(piloto3);
		
		Piloto piloto4 = new Piloto();
		piloto4.setNombre("Pelayo");
		piloto4.setPass("###");
		piloto4.setPass(passwordEncoder.encode(piloto4.getPass()));
		pilotoJPA.save(piloto4);
		
		Piloto piloto5 = new Piloto();
		piloto5.setNombre("Tute");
		piloto5.setPass("###");
		piloto5.setPass(passwordEncoder.encode(piloto5.getPass()));
		pilotoJPA.save(piloto5);
		
		Circuito circuito1 = new Circuito();
		circuito1.setNombreCircuito("Autodromo Jose Carlos Pace (Interlagos)");
		circuitoJPA.save(circuito1);
		
		Circuito circuito2 = new Circuito();
		circuito2.setNombreCircuito("Autodromo Nazionale di Monza");
		circuitoJPA.save(circuito2);
		
		Circuito circuito3 = new Circuito();
		circuito3.setNombreCircuito("Autopolis");
		circuitoJPA.save(circuito3);
		
		Circuito circuito4 = new Circuito();
		circuito4.setNombreCircuito("Brands Hatch");
		circuitoJPA.save(circuito4);
		
		Circuito circuito5 = new Circuito();
		circuito5.setNombreCircuito("Brands Hatch");
		circuitoJPA.save(circuito5);
		
		Circuito circuito6 = new Circuito();
		circuito6.setNombreCircuito("Circuit de la Sarthe");
		circuitoJPA.save(circuito6);
		
		Circuito circuito7 = new Circuito();
		circuito7.setNombreCircuito("Circuit de Spa-Francorchamps");
		circuitoJPA.save(circuito7);
		
		Circuito circuito8 = new Circuito();
		circuito8.setNombreCircuito("Daytona International Speedway");
		circuitoJPA.save(circuito8);
		
		Circuito circuito9 = new Circuito();
		circuito9.setNombreCircuito("Fuji International Circuit");
		circuitoJPA.save(circuito9);
		
		Circuito circuito10 = new Circuito();
		circuito10.setNombreCircuito("Goodwood Motor Circuit");
		circuitoJPA.save(circuito10);
		
		Circuito circuito11 = new Circuito();
		circuito11.setNombreCircuito("Mount Panorama");
		circuitoJPA.save(circuito11);
		
		Circuito circuito12 = new Circuito();
		circuito12.setNombreCircuito("Nurburgring");
		circuitoJPA.save(circuito12);
		
		Circuito circuito13 = new Circuito();
		circuito13.setNombreCircuito("Red Bull Ring");
		circuitoJPA.save(circuito13);
		
		Circuito circuito14 = new Circuito();
		circuito14.setNombreCircuito("Suzuka Circuit");
		circuitoJPA.save(circuito14);
		
		Circuito circuito15 = new Circuito();
		circuito15.setNombreCircuito("Tsukuba Circuit");
		circuitoJPA.save(circuito15);
		
		Circuito circuito16= new Circuito();
		circuito16.setNombreCircuito("WeatherTech Raceway Laguna Seca");
		circuitoJPA.save(circuito16);
		
		Circuito circuito0 = new Circuito();
		circuito0.setNombreCircuito("Michelin Raceway Road Atlanta");
		circuitoJPA.save(circuito0);
		
		Circuito circuito17 = new Circuito();
		circuito17.setNombreCircuito("Willow Springs");
		circuitoJPA.save(circuito17);
		
		Circuito circuito18 = new Circuito();
		circuito18.setNombreCircuito("Alsace Village");
		circuitoJPA.save(circuito18);
		
		Circuito circuito19 = new Circuito();
		circuito19.setNombreCircuito("Autodrome Lago Maggiore");
		circuitoJPA.save(circuito19);
		
		Circuito circuito20 = new Circuito();
		circuito20.setNombreCircuito("Blue Moon Bay Speedway");
		circuitoJPA.save(circuito20);
		
		Circuito circuito21 = new Circuito();
		circuito21.setNombreCircuito("BB Raceway");
		circuitoJPA.save(circuito21);
		
		Circuito circuito22 = new Circuito();
		circuito22.setNombreCircuito("Circuit Sainte-Croix");
		circuitoJPA.save(circuito22);
		
		Circuito circuito23 = new Circuito();
		circuito23.setNombreCircuito("Colorado Springs");
		circuitoJPA.save(circuito23);
		
		Circuito circuito24 = new Circuito();
		circuito24.setNombreCircuito("Deep Forest Raceway");
		circuitoJPA.save(circuito24);
		
		Circuito circuito25 = new Circuito();
		circuito25.setNombreCircuito("Dragon Trail");
		circuitoJPA.save(circuito25);
		
		Circuito circuito26 = new Circuito();
		circuito26.setNombreCircuito("Fishermans Ranch");
		circuitoJPA.save(circuito26);
		
		Circuito circuito27 = new Circuito();
		circuito27.setNombreCircuito("High Speed Ring");
		circuitoJPA.save(circuito27);
		
		Circuito circuito28 = new Circuito();
		circuito28.setNombreCircuito("Kyoto Driving Park");
		circuitoJPA.save(circuito28);
		
		Circuito circuito29 = new Circuito();
		circuito29.setNombreCircuito("Northern Isle Speedway");
		circuitoJPA.save(circuito29);
		
		Circuito circuito30 = new Circuito();
		circuito30.setNombreCircuito("Sardegna Road/Sardegna Windmills");
		circuitoJPA.save(circuito30);
		
		Circuito circuito31 = new Circuito();
		circuito31.setNombreCircuito("Special Stage Route X");
		circuitoJPA.save(circuito31);
		
		Circuito circuito32 = new Circuito();
		circuito32.setNombreCircuito("Tokyo Expressway");
		circuitoJPA.save(circuito32);
		
		Circuito circuito33 = new Circuito();
		circuito33.setNombreCircuito("Trial Mountain");
		circuitoJPA.save(circuito33);
		
		Circuito circuito34 = new Circuito();
		circuito34.setNombreCircuito("Eiger Nordward");
		circuitoJPA.save(circuito34);
	}

}
