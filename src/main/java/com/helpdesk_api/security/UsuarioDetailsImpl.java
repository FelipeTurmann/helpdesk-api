package com.helpdesk_api.security;

import com.helpdesk_api.usuario.entity.UsuarioEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class UsuarioDetailsImpl implements UserDetails {

    private final UsuarioEntity usuario;

    public UsuarioDetailsImpl(UsuarioEntity usuario) {
        this.usuario = usuario;
    }

    public UsuarioEntity getUsuario() {
        return usuario;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Spring Security espera o prefixo "ROLE_" para usar hasRole() nas anotações
        return List.of(new SimpleGrantedAuthority("ROLE_" + usuario.getCargo().name()));
    }

    @Override
    public String getPassword() {
        return usuario.getSenha();
    }

    @Override
    public String getUsername() {
        return usuario.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return usuario.getAtivo();
    }
}
