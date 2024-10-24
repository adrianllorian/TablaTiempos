package com.adrian.tablaTiempos.JPA;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.adrian.tablaTiempos.modelo.Tiempos;

public interface TiemposJPA extends JpaRepository <Tiempos, Integer>{

	@Transactional @Query(value="Select * from tiempos t where t.circuito_nombre_circuito=:circuito", nativeQuery=true)
	List <Tiempos> buscarPorCircuito(@Param("circuito") String piloto);
	
	@Transactional @Query(value="Select * from tiempos t where t.id=:id", nativeQuery=true)
	List <Tiempos> listarPorIdTiempo(@Param("id") Integer id );
	
	@Transactional @Query(value="Select * from tiempos t where t.piloto_nombre LIKE %:piloto%", nativeQuery=true)
	List <Tiempos> buscarPorPiloto(@Param("piloto") String piloto);
	
	@Transactional @Query(value="Select * from tiempos t where t.coche LIKE %:coche%", nativeQuery=true)
	List <Tiempos> buscarPorCoche(@Param("coche") String piloto);
	
	@Transactional @Query(value="Select * from tiempos t where t.circuito_nombre_circuito=:circuito and t.piloto_nombre LIKE %:piloto%", nativeQuery=true)
	List <Tiempos> buscarPorCircuitoYPiloto(@Param("circuito") String circuito, @Param("piloto") String piloto );
	
	@Transactional @Query(value="Select * from tiempos t where t.circuito_nombre_circuito=:circuito and t.coche LIKE %:coche%", nativeQuery=true)
	List <Tiempos> buscarPorCircuitoYCoche(@Param("circuito") String circuito, @Param("coche") String coche );
	
	@Transactional @Query(value="Select * from tiempos t where t.coche LIKE %:coche% and t.piloto_nombre LIKE %:piloto%", nativeQuery=true)
	List <Tiempos> buscarPorPilotoYCoche(@Param("coche") String coche, @Param("piloto") String piloto );
	
	@Transactional @Query(value="Select * from tiempos t where t.circuito_nombre_circuito=:circuito and t.piloto_nombre LIKE %:piloto% and t.coche LIKE %:coche%", nativeQuery=true)
	List <Tiempos> buscarPorCircuitoYPilotoYCoche(@Param("circuito") String circuito, @Param("piloto") String piloto, @Param("coche") String coche );
}
