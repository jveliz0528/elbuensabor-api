package com.delivery.demo.entities.articulos;

import com.delivery.demo.entities.Base;
import lombok.*;
import org.hibernate.envers.Audited;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@EntityListeners(AuditingEntityListener.class)
@Audited
@Table(name = "historial_stock")
public class HistorialStock extends Base {
    private double cantidad;
    private double costoPorUnidad;
    private Date fechaMovimiento;
    @ManyToOne(optional = false)
    @JoinColumn(name = "fk_articulo")
    private Articulo articulo;
    @Column(columnDefinition = "boolean default false")
    private boolean operacion;
}
