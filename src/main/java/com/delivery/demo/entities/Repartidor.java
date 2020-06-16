package com.delivery.demo.entities;

import com.delivery.demo.entities.direccion.DireccionLegal;
import lombok.*;
import org.hibernate.envers.Audited;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
//@Entity
//@EntityListeners(AuditingEntityListener.class)
//@Audited
//@Table(name = "repartidor")
public class Repartidor extends Base {
    private String nombre;
    private String apellido;
    private int telefono;
    private String patenteVehiculo;
    private String tipoVehiculo;
    @OneToOne(cascade= CascadeType.ALL)
    @JoinColumn(name = "fk_direccion")
    private DireccionLegal direccion;
}
