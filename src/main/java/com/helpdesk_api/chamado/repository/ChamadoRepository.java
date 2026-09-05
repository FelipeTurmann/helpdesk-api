package com.helpdesk_api.chamado.repository;

import com.helpdesk_api.chamado.entity.ChamadoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ChamadoRepository extends JpaRepository<ChamadoEntity, Long>, JpaSpecificationExecutor<ChamadoEntity> {

}
