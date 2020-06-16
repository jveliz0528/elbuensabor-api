package com.delivery.demo.entities.usuarios;

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
@Table(name = "usuario")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo", discriminatorType= DiscriminatorType.STRING)
public class Usuario extends Base {
    private Date fechaAlta;
    private String nombre;
    private String apellido;
    private String telefono;
    @Column(unique = true)
    private String email;
    @Column(unique = true)
    private String uid;
    @ManyToOne(optional = false)
    @JoinColumn(name="fk_rol")
    private Rol rol;
}
