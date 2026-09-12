package com.helpdesk_api.comentario.dto;

import java.time.LocalDateTime;

public record ComentarioResponseDto(
        Long id,
        String texto,
        LocalDateTime dataComentario,
        Long usuarioId,
        String usuarioNome,
        Long chamadoId
) {}