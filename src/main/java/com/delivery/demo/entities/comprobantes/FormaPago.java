package com.delivery.demo.entities.comprobantes;

import com.delivery.demo.entities.Base;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
//@Entity
//@EntityListeners(AuditingEntityListener.class)
//@Audited
//@Table(name = "forma_pago")
public class FormaPago extends Base {
    private String denominacion;
}
