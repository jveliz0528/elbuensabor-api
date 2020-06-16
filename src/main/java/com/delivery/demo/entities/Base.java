package com.delivery.demo.entities;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public class Base {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    private Date ultimaActualizacion;

    @Column(columnDefinition = "boolean default false")
    private boolean oculto;

    @Column(columnDefinition = "boolean default false")
    private boolean eliminado;
}
