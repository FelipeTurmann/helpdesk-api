package com.helpdesk_api.dashboard.service;

import com.helpdesk_api.chamado.repository.ChamadoRepository;
import com.helpdesk_api.dashboard.dto.DashboardResponseDto;
import com.helpdesk_api.enums.StatusChamadoEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.EnumMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class DashboardService {

    private final ChamadoRepository chamadoRepository;

    @Transactional(readOnly = true)
    public DashboardResponseDto obterResumo() {

        log.info("Iniciando obtenção do resumo do dashboard.");

        Map<StatusChamadoEnum, Long> contagemPorStatus =
                new EnumMap<>(StatusChamadoEnum.class);

        // Inicializa todos os status com 0, para garantir que status sem chamado
        // apareçam no resultado.
        for (StatusChamadoEnum status : StatusChamadoEnum.values()) {
            contagemPorStatus.put(status, 0L);
        }

        log.debug("Status do dashboard inicializados com contagem zero.");

        log.debug("Consultando quantidade de chamados por status.");

        chamadoRepository.contarPorStatus()
                .forEach(projection -> {
                    log.debug(
                            "Quantidade de chamados encontrada. status={}, total={}",
                            projection.getStatus(),
                            projection.getTotal()
                    );

                    contagemPorStatus.put(
                            projection.getStatus(),
                            projection.getTotal()
                    );
                });

        long total = contagemPorStatus.values()
                .stream()
                .mapToLong(Long::longValue)
                .sum();

        log.info(
                "Resumo do dashboard calculado com sucesso. total={}, porStatus={}",
                total,
                contagemPorStatus
        );

        DashboardResponseDto response = new DashboardResponseDto(
                total,
                contagemPorStatus.get(StatusChamadoEnum.ABERTO),
                contagemPorStatus.get(StatusChamadoEnum.EM_ATENDIMENTO),
                contagemPorStatus.get(StatusChamadoEnum.AGUARDANDO_CLIENTE),
                contagemPorStatus.get(StatusChamadoEnum.RESOLVIDO),
                contagemPorStatus.get(StatusChamadoEnum.FECHADO)
        );

        log.debug("Resposta do dashboard criada com sucesso.");

        return response;
    }
}
