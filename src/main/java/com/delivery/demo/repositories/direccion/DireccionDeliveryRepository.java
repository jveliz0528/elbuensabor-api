package com.delivery.demo.repositories.direccion;

import com.delivery.demo.entities.direccion.DireccionDelivery;
import com.delivery.demo.repositories.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DireccionDeliveryRepository extends BaseRepository<DireccionDelivery, Long> {
}
