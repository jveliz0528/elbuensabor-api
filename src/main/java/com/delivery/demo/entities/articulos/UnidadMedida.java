package com.delivery.demo.entities.articulos;

import com.delivery.demo.entities.Base;
import lombok.*;
import org.hibernate.envers.Audited;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
//@Entity
//@EntityListeners(AuditingEntityListener.class)
//@Audited
//@Table(name = "unidad_medida")
public class UnidadMedida extends Base {
    private String abreviatura;
    private String denominacion;
}
