package com.helpdesk_api.dashboard.dto;

public record DashboardResponseDto(
        long totalChamados,
        long abertos,
        long emAtendimento,
        long aguardandoCliente,
        long resolvidos,
        long fechados
) {}
