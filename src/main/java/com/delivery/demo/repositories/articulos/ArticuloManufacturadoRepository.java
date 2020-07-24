package com.delivery.demo.repositories.articulos;

import com.delivery.demo.entities.articulos.ArticuloManufacturado;
import com.delivery.demo.repositories.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public interface ArticuloManufacturadoRepository extends BaseRepository<ArticuloManufacturado, Long> {
    @Query(
            value = "SELECT manufacturado.denominacion AS \"name\" , SUM(detalle_orden.cantidad) AS \"value\" FROM manufacturado INNER JOIN detalle_orden ON detalle_orden.fk_manufacturado = manufacturado.id WHERE detalle_orden.ultima_actualizacion BETWEEN :fechaInicio AND :fechaFin GROUP BY manufacturado.id ORDER BY SUM(cantidad) DESC LIMIT 5\n",
            nativeQuery = true
    )
    List<Object[]> getMasVendidos(@Param("fechaInicio") Date fechaInicio, @Param("fechaFin") Date fechaFin);
}
