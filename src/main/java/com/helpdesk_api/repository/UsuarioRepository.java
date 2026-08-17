package com.helpdesk_api.repository;

import com.helpdesk_api.entity.Usuario;
import com.helpdesk_api.enums.Cargo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByEmail(String email);

    boolean existsByEmail(String email);

    List<Usuario> findByEmpresaId(Long empresaId);

    List<Usuario> findByCargo(Cargo cargo);

    List<Usuario> findByEmpresaIdAndAtivoTrue(Long empresaId);
}
