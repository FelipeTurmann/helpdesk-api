package com.helpdesk_api.chamado.controller.doc;

import com.helpdesk_api.chamado.dto.ChamadoFiltroConsultaDto;
import com.helpdesk_api.chamado.dto.ChamadoRequestDto;
import com.helpdesk_api.chamado.dto.ChamadoResponseDto;
import com.helpdesk_api.chamado.dto.ChamadoStatusUpdateDto;
import com.helpdesk_api.exception.ErrorResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import java.util.List;

@Tag(name = "Chamados", description = "Abertura, consulta e acompanhamento de chamados técnicos.")
public interface ChamadoControllerDoc {
    @Operation(summary = "Abrir chamado", description = "Cria um chamado para a empresa do usuário CLIENTE autenticado.")
    @RequestBody(required = true, content = @Content(schema = @Schema(implementation = ChamadoRequestDto.class)))
    @ApiResponses({@ApiResponse(responseCode = "201", description = "Chamado aberto.", content = @Content(schema = @Schema(implementation = ChamadoResponseDto.class))), @ApiResponse(responseCode = "400", description = "Dados inválidos.", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))), @ApiResponse(responseCode = "403", description = "Apenas CLIENTE pode abrir chamados.", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))})
    ResponseEntity<ChamadoResponseDto> abrirChamado(ChamadoRequestDto request);

    @Operation(summary = "Listar chamados", description = "ADMIN consulta todos; CLIENTE consulta somente os chamados permitidos. Aceita filtros opcionais.")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Chamados encontrados."), @ApiResponse(responseCode = "403", description = "Usuário sem permissão.", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))})
    ResponseEntity<List<ChamadoResponseDto>> listarChamados(@Parameter(description = "Filtros por status, prioridade, categoria e empresa.") ChamadoFiltroConsultaDto filtro);

    @Operation(summary = "Buscar chamado por ID")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Chamado encontrado.", content = @Content(schema = @Schema(implementation = ChamadoResponseDto.class))), @ApiResponse(responseCode = "404", description = "Chamado não encontrado.", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))})
    ResponseEntity<ChamadoResponseDto> buscarPorId(@Parameter(description = "ID do chamado.", required = true, example = "1") Long id);

    @Operation(summary = "Atualizar chamado", description = "Atualiza título, descrição, categoria e prioridade de um chamado. Requer CLIENTE.")
    @RequestBody(required = true, content = @Content(schema = @Schema(implementation = ChamadoRequestDto.class)))
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Chamado atualizado.", content = @Content(schema = @Schema(implementation = ChamadoResponseDto.class))), @ApiResponse(responseCode = "400", description = "Dados inválidos.", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))), @ApiResponse(responseCode = "404", description = "Chamado não encontrado.", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))})
    ResponseEntity<ChamadoResponseDto> atualizarChamado(@Parameter(description = "ID do chamado.", required = true) Long id, ChamadoRequestDto request);

    @Operation(summary = "Alterar status do chamado", description = "Atualiza o status do chamado. Requer perfil ADMIN.")
    @RequestBody(required = true, content = @Content(schema = @Schema(implementation = ChamadoStatusUpdateDto.class)))
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Status alterado.", content = @Content(schema = @Schema(implementation = ChamadoResponseDto.class))), @ApiResponse(responseCode = "400", description = "Status inválido.", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))), @ApiResponse(responseCode = "404", description = "Chamado não encontrado.", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))})
    ResponseEntity<ChamadoResponseDto> alterarStatus(@Parameter(description = "ID do chamado.", required = true) Long id, ChamadoStatusUpdateDto dto);

    @Operation(summary = "Excluir chamado", description = "Remove definitivamente um chamado. Requer perfil ADMIN.")
    @ApiResponses({@ApiResponse(responseCode = "204", description = "Chamado excluído."), @ApiResponse(responseCode = "404", description = "Chamado não encontrado.", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))})
    ResponseEntity<Void> excluir(@Parameter(description = "ID do chamado.", required = true) Long id);
}
