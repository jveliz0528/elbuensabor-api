package com.delivery.demo.services.rol;

import com.delivery.demo.entities.usuarios.Rol;
import com.delivery.demo.repositories.BaseRepository;
import com.delivery.demo.services.base.BaseServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class RolServiceImpl extends BaseServiceImpl<Rol, Long> implements RolService {

    public RolServiceImpl(BaseRepository<Rol, Long> baseRepository) {
        super(baseRepository);
    }
}
