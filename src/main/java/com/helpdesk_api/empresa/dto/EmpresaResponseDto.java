package com.helpdesk_api.empresa.dto;

import java.time.LocalDateTime;

public record EmpresaResponseDto(
        Long id,
        String nome,
        String cnpj,
        String telefone,
        String email,
        Boolean ativo,
        LocalDateTime dataCadastro
) {}
