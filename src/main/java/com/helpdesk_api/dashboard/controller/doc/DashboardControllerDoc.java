package com.helpdesk_api.dashboard.controller.doc;

import com.helpdesk_api.dashboard.dto.DashboardResponseDto;
import com.helpdesk_api.exception.ErrorResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "Dashboard", description = "Indicadores consolidados de chamados. Requer perfil ADMIN.")
public interface DashboardControllerDoc {
    @Operation(summary = "Obter resumo do dashboard", description = "Retorna o total de chamados distribuído por status.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Resumo gerado.", content = @Content(schema = @Schema(implementation = DashboardResponseDto.class))),
            @ApiResponse(responseCode = "403", description = "Acesso restrito a administradores.", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    ResponseEntity<DashboardResponseDto> obterResumo();
}
