package com.helpdesk_api.empresa.repository;

import com.helpdesk_api.empresa.entity.EmpresaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmpresaRepository extends JpaRepository<EmpresaEntity, Long>, JpaSpecificationExecutor<EmpresaEntity> {

    Optional<EmpresaEntity> findByCnpj(String cnpj);

    boolean existsByCnpj(String cnpj);
}
