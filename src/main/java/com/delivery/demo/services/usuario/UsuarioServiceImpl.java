package com.delivery.demo.services.usuario;

import com.delivery.demo.entities.usuarios.Cliente;
import com.delivery.demo.entities.usuarios.Usuario;
import com.delivery.demo.repositories.BaseRepository;
import com.delivery.demo.services.base.BaseServiceImpl;
import com.delivery.demo.specifications.SearchSpecification;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Optional;

@Service
public class UsuarioServiceImpl extends BaseServiceImpl<Usuario, Long> implements UsuarioService {

    public UsuarioServiceImpl(BaseRepository<Usuario, Long> baseRepository) {
        super(baseRepository);
    }

    SearchSpecification<Usuario> spec = new SearchSpecification<Usuario>();

    @Override
    public Usuario findByUID(String uid) throws Exception {
        try {

            Specification<Usuario> filterByUID = spec.findByUid(uid);
            Optional<Usuario> entityOptional = baseRepository.findOne(Specification.where(filterByUID));

            return entityOptional.get();

        } catch (Exception e) {

            throw new Exception(e.getMessage());

        }
    }
}
