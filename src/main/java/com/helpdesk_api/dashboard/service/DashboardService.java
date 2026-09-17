package com.helpdesk_api.dashboard.service;

import com.helpdesk_api.chamado.repository.ChamadoRepository;
import com.helpdesk_api.dashboard.dto.DashboardResponseDto;
import com.helpdesk_api.enums.StatusChamadoEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.EnumMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final ChamadoRepository chamadoRepository;

    @Transactional(readOnly = true)
    public DashboardResponseDto obterResumo() {
        Map<StatusChamadoEnum, Long> contagemPorStatus = new EnumMap<>(StatusChamadoEnum.class);

        // inicializa todos os status com 0, pra garantir que status sem chamado nenhum apareçam no resultado
        for (StatusChamadoEnum status : StatusChamadoEnum.values()) {
            contagemPorStatus.put(status, 0L);
        }

        chamadoRepository.contarPorStatus()
                .forEach(projection -> contagemPorStatus.put(projection.getStatus(), projection.getTotal()));

        long total = contagemPorStatus.values().stream().mapToLong(Long::longValue).sum();

        return new DashboardResponseDto(
                total,
                contagemPorStatus.get(StatusChamadoEnum.ABERTO),
                contagemPorStatus.get(StatusChamadoEnum.EM_ATENDIMENTO),
                contagemPorStatus.get(StatusChamadoEnum.AGUARDANDO_CLIENTE),
                contagemPorStatus.get(StatusChamadoEnum.RESOLVIDO),
                contagemPorStatus.get(StatusChamadoEnum.FECHADO)
        );
    }
}
