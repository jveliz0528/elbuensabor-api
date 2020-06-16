package com.delivery.demo.entities.comprobantes;

import com.delivery.demo.entities.Base;
import lombok.*;
import org.hibernate.envers.Audited;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@EntityListeners(AuditingEntityListener.class)
@Audited
@Table(name = "comprobante")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo", discriminatorType= DiscriminatorType.STRING)
public class Comprobante extends Base {
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    private String formaPago;
    private double montoDescuento;
    private double total;
    @ManyToOne(optional = false)
    @JoinColumn(name = "fk_estado")
    private Estado estado;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DetalleOrden> detalles;
}
