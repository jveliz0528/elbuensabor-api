package com.delivery.demo.entities.comprobantes;

import com.delivery.demo.entities.Base;
import com.delivery.demo.entities.articulos.ArticuloInsumo;
import com.delivery.demo.entities.articulos.ArticuloManufacturado;
import lombok.*;
import org.hibernate.envers.Audited;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@EntityListeners(AuditingEntityListener.class)
@Audited
@Table(name = "detalle_orden")
public class DetalleOrden extends Base {

    private double cantidad;

    private double precioTotal;

    @ManyToOne
    @JoinColumn(name = "fk_insumo")
    private ArticuloInsumo insumo;

    @ManyToOne
    @JoinColumn(name= "fk_manufacturado")
    private ArticuloManufacturado articuloManufacturado;
}
