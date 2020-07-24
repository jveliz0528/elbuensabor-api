package com.delivery.demo.repositories.articulos;

import com.delivery.demo.dtos.GraficosDTO;
import com.delivery.demo.entities.articulos.ArticuloInsumo;
import com.delivery.demo.repositories.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public interface ArticuloInsumoRepository extends BaseRepository<ArticuloInsumo, Long> {
    @Query(
            value = "SELECT articulo_insumo.denominacion AS \"name\" , SUM(detalle_orden.cantidad) AS \"value\" FROM articulo_insumo INNER JOIN detalle_orden ON detalle_orden.fk_insumo = articulo_insumo.id WHERE detalle_orden.ultima_actualizacion BETWEEN :fechaInicio AND :fechaFin GROUP BY articulo_insumo.id ORDER BY SUM(cantidad) DESC LIMIT 5",
            nativeQuery = true
    )
    List<Object[]> getMasVendidos(@Param("fechaInicio")Date fechaInicio, @Param("fechaFin") Date fechaFin);
}
