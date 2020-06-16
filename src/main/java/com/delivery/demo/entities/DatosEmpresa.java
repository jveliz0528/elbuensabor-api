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
@Entity
@EntityListeners(AuditingEntityListener.class)
@Audited
@Table(name = "empresa")
public class DatosEmpresa extends Base {
    private String email;
    private String propietario;
    private String rezonSocial;
    private int telefono;
    @OneToOne(cascade= CascadeType.ALL)
    @JoinColumn(name = "fk_direccion")
    private DireccionLegal direccion;
}
