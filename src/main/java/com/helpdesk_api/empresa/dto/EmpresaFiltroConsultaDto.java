package com.helpdesk_api.empresa.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record EmpresaFiltroConsultaDto(
        String nome,
        String cnpj,
        String telefone,
        String email) {
}
