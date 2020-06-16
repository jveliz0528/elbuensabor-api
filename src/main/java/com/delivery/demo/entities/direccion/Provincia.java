package com.delivery.demo.entities.direccion;

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
@Table(name = "provincia")
public class Provincia extends Base {
    private String nombre;
    @ManyToOne(optional = false)
    @JoinColumn(name = "fk_pais")
    private Pais pais;
}
