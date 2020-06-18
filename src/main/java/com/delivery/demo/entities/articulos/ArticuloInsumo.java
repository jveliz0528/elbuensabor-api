package com.delivery.demo.entities.articulos;

import com.delivery.demo.entities.Base;
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
@Table(name = "articulo_insumo")
public class ArticuloInsumo extends Base {

    @Column(columnDefinition = "boolean default true")
    private boolean esInsumo;
    private String denominacion;
    private String descripcion;
    private String imagen;
    private double costo;
    private double precio;
    private double stockActual;
    private double stockMaximo;
    private double stockMinimo;
    private String unidadMedida;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<HistorialStock> historialStock;
    @ManyToOne(optional = true)
    @JoinColumn(name = "fk_rubro")
    private Rubro rubro;

}
