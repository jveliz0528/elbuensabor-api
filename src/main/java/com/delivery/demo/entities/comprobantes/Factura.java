package com.delivery.demo.entities.comprobantes;

import com.delivery.demo.entities.Base;
import com.delivery.demo.entities.DatosEmpresa;
import com.delivery.demo.entities.usuarios.Empleado;
import lombok.*;
import org.hibernate.envers.Audited;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
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
@DiscriminatorValue(value = "factura")
public class Factura extends Comprobante {

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_orden")
    private Orden orden;

    @ManyToOne
    @JoinColumn(name = "fk_datos_empresa")
    private DatosEmpresa datosEmpresa;

    @ManyToOne
    @JoinColumn(name = "fk_cajero")
    private Empleado cajero;
}
