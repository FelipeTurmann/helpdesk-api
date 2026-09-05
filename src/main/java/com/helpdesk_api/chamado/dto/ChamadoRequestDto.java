package com.helpdesk_api.chamado.dto;

import com.helpdesk_api.enums.PrioridadeEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ChamadoRequestDto(

        @NotBlank(message = "Título é obrigatório")
        String titulo,

        @NotBlank(message = "Descrição é obrigatória")
        String descricao,

        @NotBlank(message = "Categoria é obrigatória")
        String categoria,

        @NotNull(message = "Prioridade é obrigatória")
        PrioridadeEnum prioridade
) {}
