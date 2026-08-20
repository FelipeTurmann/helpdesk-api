package com.helpdesk_api.empresa.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record EmpresaRequestDto(

        @NotBlank(message = "Nome é obrigatório")
        String nome,

        @NotBlank(message = "CNPJ é obrigatório")
        @Pattern(regexp = "\\d{14}", message = "CNPJ deve conter 14 dígitos numéricos")
        String cnpj,

        String telefone,

        @Email(message = "Email inválido")
        String email
) {}