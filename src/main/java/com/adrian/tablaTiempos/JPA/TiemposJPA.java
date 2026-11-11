package com.adrian.tablaTiempos.JPA;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.adrian.tablaTiempos.modelo.Circuito;
import com.adrian.tablaTiempos.modelo.Piloto;
import com.adrian.tablaTiempos.modelo.Tiempos;

public interface TiemposJPA extends JpaRepository<Tiempos, Integer> {

	@Query("SELECT t FROM Tiempos t WHERE t.circuito.nombreCircuito = :circuito")
	List<Tiempos> buscarPorCircuito(@Param("circuito") String circuito);

	@Query("SELECT t FROM Tiempos t WHERE LOWER(t.piloto.nombre) LIKE LOWER(CONCAT('%', :piloto, '%'))")
	List<Tiempos> buscarPorPiloto(@Param("piloto") String piloto);

	@Query("SELECT t FROM Tiempos t WHERE LOWER(t.coche) LIKE LOWER(CONCAT('%', :coche, '%'))")
	List<Tiempos> buscarPorCoche(@Param("coche") String coche);

	@Query("SELECT t FROM Tiempos t WHERE t.circuito.nombreCircuito = :circuito "
			+ "AND LOWER(t.piloto.nombre) LIKE LOWER(CONCAT('%', :piloto, '%'))")
	List<Tiempos> buscarPorCircuitoYPiloto(@Param("circuito") String circuito, @Param("piloto") String piloto);

	@Query("SELECT t FROM Tiempos t WHERE t.circuito.nombreCircuito = :circuito "
			+ "AND LOWER(t.coche) LIKE LOWER(CONCAT('%', :coche, '%'))")
	List<Tiempos> buscarPorCircuitoYCoche(@Param("circuito") String circuito, @Param("coche") String coche);

	@Query("SELECT t FROM Tiempos t WHERE LOWER(t.piloto.nombre) LIKE LOWER(CONCAT('%', :piloto, '%')) "
			+ "AND LOWER(t.coche) LIKE LOWER(CONCAT('%', :coche, '%'))")
	List<Tiempos> buscarPorPilotoYCoche(@Param("piloto") String piloto, @Param("coche") String coche);

	@Query("SELECT t FROM Tiempos t WHERE t.circuito.nombreCircuito = :circuito "
			+ "AND LOWER(t.piloto.nombre) LIKE LOWER(CONCAT('%', :piloto, '%')) "
			+ "AND LOWER(t.coche) LIKE LOWER(CONCAT('%', :coche, '%'))")
	List<Tiempos> buscarPorCircuitoYPilotoYCoche(@Param("circuito") String circuito,
			@Param("piloto") String piloto, @Param("coche") String coche);

	@Query("SELECT COUNT(t) FROM Tiempos t WHERE t.circuito.nombreCircuito = :circuito")
	long contarPorCircuito(@Param("circuito") String circuito);

	@Query("SELECT COUNT(t) FROM Tiempos t WHERE t.piloto.nombre = :piloto")
	long contarPorPiloto(@Param("piloto") String piloto);

	@Modifying(flushAutomatically = true)
	@Query("UPDATE Tiempos t SET t.circuito = :nuevo WHERE t.circuito.nombreCircuito = :viejo")
	int renombrarCircuito(@Param("viejo") String viejo, @Param("nuevo") Circuito nuevo);

	@Modifying(flushAutomatically = true)
	@Query("UPDATE Tiempos t SET t.piloto = :nuevo WHERE t.piloto.nombre = :viejo")
	int renombrarPiloto(@Param("viejo") String viejo, @Param("nuevo") Piloto nuevo);
}
