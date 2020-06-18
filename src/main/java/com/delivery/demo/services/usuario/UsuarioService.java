package com.delivery.demo.services.usuario;

import com.delivery.demo.entities.usuarios.Usuario;
import com.delivery.demo.services.base.BaseService;

public interface UsuarioService extends BaseService<Usuario, Long> {
    public Usuario findByUID(String uid) throws Exception;
}
