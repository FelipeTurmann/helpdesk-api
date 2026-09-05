package com.helpdesk_api.chamado.dto;

import com.helpdesk_api.enums.PrioridadeEnum;
import com.helpdesk_api.enums.StatusChamadoEnum;

import java.time.LocalDateTime;

public record ChamadoResponseDto(
        Long id,
        String titulo,
        String descricao,
        StatusChamadoEnum status,
        PrioridadeEnum prioridade,
        String categoria,
        LocalDateTime dataAbertura,
        LocalDateTime dataAtualizacao,
        LocalDateTime dataFechamento,
        Long empresaId,
        String empresaNome,
        Long usuarioAberturaId,
        String usuarioAberturaNome
) {}
