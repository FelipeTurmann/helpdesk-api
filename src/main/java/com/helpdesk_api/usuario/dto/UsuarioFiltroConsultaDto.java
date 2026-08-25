package com.helpdesk_api.usuario.dto;

import com.helpdesk_api.enums.CargoEnum;

public record UsuarioFiltroConsultaDto(
        String nome,
        String email,
        CargoEnum cargo,
        Long empresaId,
        Boolean ativo
) {}
