package com.delivery.demo.entities.direccion;

import lombok.*;
import org.hibernate.envers.Audited;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@EntityListeners(AuditingEntityListener.class)
@Audited
@DiscriminatorValue(value = "delivery")
public class DireccionDelivery extends Direccion {
    private String aclaraciones;
    private String alias;
    private double latitud;
    private double longitud;

}
