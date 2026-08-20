package com.helpdesk_api.empresa.repository;

import com.helpdesk_api.empresa.entity.EmpresaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmpresaRepository extends JpaRepository<EmpresaEntity, Long> {

    Optional<EmpresaEntity> findByCnpj(String cnpj);

    boolean existsByCnpj(String cnpj);
}
