package com.helpdesk_api.usuario.dto;

import com.helpdesk_api.enums.CargoEnum;

import java.time.LocalDateTime;

public record UsuarioResponseDto(
        Long id,
        String nome,
        String email,
        CargoEnum cargo,
        Boolean ativo,
        LocalDateTime dataCadastro,
        Long empresaId,
        String empresaNome
) {}
