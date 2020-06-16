package com.delivery.demo.entities.usuarios;

import com.delivery.demo.entities.direccion.DireccionDelivery;
import lombok.*;
import org.hibernate.envers.Audited;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@EntityListeners(AuditingEntityListener.class)
@Audited
@DiscriminatorValue(value = "cliente")
public class Cliente extends Empleado {
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DireccionDelivery> direccionesEnvio;
}
