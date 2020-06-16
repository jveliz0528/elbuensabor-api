package com.delivery.demo.entities.comprobantes;

import com.delivery.demo.entities.Base;
import com.delivery.demo.entities.articulos.Articulo;
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
    @ManyToOne(optional = false)
    @JoinColumn(name = "fk_articulo")
    private Articulo articulo;
}
