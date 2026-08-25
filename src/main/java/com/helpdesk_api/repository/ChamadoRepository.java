package com.helpdesk_api.repository;

import com.helpdesk_api.entity.Chamado;
import com.helpdesk_api.enums.PrioridadeEnum;
import com.helpdesk_api.enums.StatusChamadoEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChamadoRepository extends JpaRepository<Chamado, Long> {

    List<Chamado> findByEmpresaId(Long empresaId);

    List<Chamado> findByUsuarioAberturaId(Long usuarioId);

    List<Chamado> findByStatus(StatusChamadoEnum status);

    List<Chamado> findByEmpresaIdAndStatus(Long empresaId, StatusChamadoEnum status);

    List<Chamado> findByPrioridade(PrioridadeEnum prioridade);

    long countByStatus(StatusChamadoEnum status);
}
