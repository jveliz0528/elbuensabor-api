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
@Table(name = "manufacturado")
public class ArticuloManufacturado extends Base {
    private String denominacion;
    private String descripcion;
    private String imagen;
    private double precio;
    private int tiempoEstimadoCocina;
    @ManyToOne(optional = true)
    @JoinColumn(name = "fk_categoria")
    private Categoria categoria;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DetalleReceta> detallesReceta;
}
