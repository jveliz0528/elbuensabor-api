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
@DiscriminatorValue(value = "manufacturado")
public class Manufacturado extends Articulo {
    private int tiempoEstimadoCocina;
    @ManyToOne(optional = false)
    @JoinColumn(name = "fk_categoria")
    private Categoria categoria;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DetalleReceta> detallesReceta;
}
