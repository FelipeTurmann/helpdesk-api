package com.helpdesk_api.chamado.repository;

import com.helpdesk_api.chamado.entity.ChamadoEntity;
import com.helpdesk_api.enums.PrioridadeEnum;
import com.helpdesk_api.enums.StatusChamadoEnum;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

public class ChamadoSpecification {

    private ChamadoSpecification() {
    }

    public static Specification<ChamadoEntity> comStatus(StatusChamadoEnum status) {
        if (status == null) {
            return Specification.unrestricted();
        }
        return (root, query, cb) -> cb.equal(root.get("status"), status);
    }

    public static Specification<ChamadoEntity> comPrioridade(PrioridadeEnum prioridade) {
        if (prioridade == null) {
            return Specification.unrestricted();
        }
        return (root, query, cb) -> cb.equal(root.get("prioridade"), prioridade);
    }

    public static Specification<ChamadoEntity> comCategoria(String categoria) {
        if (!StringUtils.hasText(categoria)) {
            return Specification.unrestricted();
        }
        return (root, query, cb) ->
                cb.like(cb.lower(root.get("categoria")), "%" + categoria.toLowerCase() + "%");
    }

    public static Specification<ChamadoEntity> comEmpresaId(Long empresaId) {
        if (empresaId == null) {
            return Specification.unrestricted();
        }
        return (root, query, cb) -> cb.equal(root.get("empresa").get("id"), empresaId);
    }
}
