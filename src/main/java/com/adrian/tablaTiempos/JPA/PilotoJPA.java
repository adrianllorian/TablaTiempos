package com.adrian.tablaTiempos.JPA;

import org.springframework.data.jpa.repository.JpaRepository;

import com.adrian.tablaTiempos.modelo.Piloto;

public interface PilotoJPA  extends JpaRepository <Piloto,String>{
	

}
