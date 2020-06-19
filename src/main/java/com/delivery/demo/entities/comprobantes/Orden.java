package com.delivery.demo.entities.comprobantes;

import com.delivery.demo.entities.Base;
import com.delivery.demo.entities.direccion.DireccionDelivery;
import com.delivery.demo.entities.usuarios.Cliente;
import com.delivery.demo.entities.usuarios.Empleado;
import lombok.*;
import org.hibernate.envers.Audited;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.ArrayList;
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
@DiscriminatorValue(value = "orden")
public class Orden extends Comprobante {

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DetalleOrden> detalles;

    @Column(columnDefinition = "boolean default false")
    private boolean delivery;

    private int tiempoTotalPreparacion;

    @Temporal(TemporalType.TIMESTAMP)
    private Date horarioEntrega;

    @ManyToOne
    @JoinColumn(name = "fk_cliente")
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "fk_direccion_entrega")
    private DireccionDelivery direccionEntrega;

    @ManyToOne
    @JoinColumn(name = "fk_repartidor")
    private Empleado repartidor;

    @OneToOne
    @JoinColumn(name = "fk_factura")
    private Factura factura;

}
