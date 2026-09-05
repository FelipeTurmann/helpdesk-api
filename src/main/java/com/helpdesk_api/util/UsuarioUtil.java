package com.helpdesk_api.util;

import com.helpdesk_api.security.UsuarioDetailsImpl;
import com.helpdesk_api.usuario.entity.UsuarioEntity;
import org.springframework.security.core.context.SecurityContextHolder;

public class UsuarioUtil {

    public UsuarioEntity usuarioAutenticado() {
        UsuarioDetailsImpl details = (UsuarioDetailsImpl) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
        return details.getUsuario();
    }

}
