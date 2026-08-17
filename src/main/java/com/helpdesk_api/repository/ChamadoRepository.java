package com.helpdesk_api.repository;

import com.helpdesk_api.entity.Chamado;
import com.helpdesk_api.enums.Prioridade;
import com.helpdesk_api.enums.StatusChamado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChamadoRepository extends JpaRepository<Chamado, Long> {

    List<Chamado> findByEmpresaId(Long empresaId);

    List<Chamado> findByUsuarioAberturaId(Long usuarioId);

    List<Chamado> findByStatus(StatusChamado status);

    List<Chamado> findByEmpresaIdAndStatus(Long empresaId, StatusChamado status);

    List<Chamado> findByPrioridade(Prioridade prioridade);

    long countByStatus(StatusChamado status);
}
