package com.delivery.demo.repositories.usuarios;

import com.delivery.demo.entities.usuarios.Usuario;
import com.delivery.demo.repositories.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public interface UsuarioRepository extends BaseRepository<Usuario, Long> {
    @Query(
            value = "SELECT usuario.email, COUNT(comprobante.id) AS \"ordenes\" FROM usuario LEFT JOIN comprobante ON comprobante.fk_cliente = usuario.id WHERE comprobante.fecha BETWEEN :fechaInicio AND :fechaFin GROUP BY usuario.id ORDER BY COUNT(comprobante.id) DESC",
            nativeQuery = true
    )
    List<Object[]> getOrdenes(@Param("fechaInicio") Date fechaInicio, @Param("fechaFin") Date fechaFin);
}
