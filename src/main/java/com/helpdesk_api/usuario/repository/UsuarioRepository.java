package com.helpdesk_api.usuario.repository;

import com.helpdesk_api.usuario.entity.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Long>, JpaSpecificationExecutor<UsuarioEntity> {

    Optional<UsuarioEntity> findByEmail(String email);

    boolean existsByEmail(String email);
}
