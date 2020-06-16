package com.delivery.demo.entities.articulos;

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
@DiscriminatorValue(value = "insumo")
public class Insumo extends Articulo {
    private double costoPorUnidad;
    private String denominacion;
    private String imagen;
    private double stockActual;
    private double stockMaximo;
    private double stockMinimo;
    private String unidadMedida;
//    @ManyToOne(optional = false)
//    @JoinColumn(name = "fk_unidad_medida")
//    private UnidadMedida unidadMedida;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<HistorialStock> historialStock;
    @ManyToOne(optional = false)
    @JoinColumn(name = "fk_rubro")
    private Rubro rubro;
}
