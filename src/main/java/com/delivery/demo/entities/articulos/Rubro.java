package com.delivery.demo.entities.articulos;

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
@Table(name = "rubro")
public class Rubro extends Base {
    private String denominacion;
    @ManyToOne(optional = true)
    @JoinColumn(name = "fk_rubro_padre")
    private Rubro rubroPadre;
}
