package com.helpdesk_api.chamado.dto;

import com.helpdesk_api.enums.PrioridadeEnum;
import com.helpdesk_api.enums.StatusChamadoEnum;

public record ChamadoFiltroConsultaDto(
        StatusChamadoEnum status,
        PrioridadeEnum prioridade,
        String categoria,
        Long empresaId
) {}
