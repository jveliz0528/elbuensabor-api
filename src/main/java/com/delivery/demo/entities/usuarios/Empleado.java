package com.delivery.demo.entities.usuarios;

import com.delivery.demo.entities.direccion.DireccionLegal;
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
@DiscriminatorValue(value = "empleado")
public class Empleado extends Usuario {
    @Column(unique = true)
    private String cuil;
    private Date fechaDeIngreso;
    @OneToOne(cascade= CascadeType.ALL)
    @JoinColumn(name = "fk_direccion")
    private DireccionLegal direccion;
}
