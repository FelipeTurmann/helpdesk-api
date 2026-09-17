package com.helpdesk_api.chamado.repository;

import com.helpdesk_api.chamado.entity.ChamadoEntity;
import com.helpdesk_api.enums.StatusChamadoEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChamadoRepository extends JpaRepository<ChamadoEntity, Long>, JpaSpecificationExecutor<ChamadoEntity> {

    @Query("SELECT c.status AS status, COUNT(c) AS total FROM ChamadoEntity c GROUP BY c.status")
    List<StatusCountProjection> contarPorStatus();

    interface StatusCountProjection {
        StatusChamadoEnum getStatus();
        Long getTotal();
    }

}
