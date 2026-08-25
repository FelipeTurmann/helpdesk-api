package com.helpdesk_api.repository;

import com.helpdesk_api.usuario.entity.UsuarioEntity;
import com.helpdesk_api.enums.CargoEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Long> {

    Optional<UsuarioEntity> findByEmail(String email);

    boolean existsByEmail(String email);

    List<UsuarioEntity> findByEmpresaId(Long empresaId);

    List<UsuarioEntity> findByCargo(CargoEnum cargo);

    List<UsuarioEntity> findByEmpresaIdAndAtivoTrue(Long empresaId);
}
