package com.helpdesk_api.chamado.dto;

import com.helpdesk_api.enums.StatusChamadoEnum;
import jakarta.validation.constraints.NotNull;

public record ChamadoStatusUpdateDto(

        @NotNull(message = "Status é obrigatório")
        StatusChamadoEnum status
) {}
