package com.helpdesk_api.comentario.dto;

import jakarta.validation.constraints.NotBlank;

public record ComentarioRequestDto(

        @NotBlank(message = "Texto do comentário é obrigatório")
        String texto
) {}
