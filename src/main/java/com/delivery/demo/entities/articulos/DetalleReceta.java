package com.delivery.demo.entities.articulos;

import com.delivery.demo.entities.Base;
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
@Table(name = "detalle_receta")
public class DetalleReceta extends Base {
    private double cantidad;
    @ManyToOne(optional = false)
    @JoinColumn(name = "fk_insumo")
    private Insumo insumo;
}
