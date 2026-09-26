package com.helpdesk_api.comentario.controller.doc;

import com.helpdesk_api.comentario.dto.ComentarioRequestDto;
import com.helpdesk_api.comentario.dto.ComentarioResponseDto;
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

@Tag(name = "Comentários", description = "Comentários vinculados a chamados. Requer perfil ADMIN ou CLIENTE.")
public interface ComentarioControllerDoc {
    @Operation(summary = "Adicionar comentário", description = "Inclui um comentário no chamado informado.")
    @RequestBody(required = true, content = @Content(schema = @Schema(implementation = ComentarioRequestDto.class)))
    @ApiResponses({@ApiResponse(responseCode = "201", description = "Comentário adicionado.", content = @Content(schema = @Schema(implementation = ComentarioResponseDto.class))), @ApiResponse(responseCode = "400", description = "Texto do comentário inválido.", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))), @ApiResponse(responseCode = "404", description = "Chamado não encontrado.", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))})
    ResponseEntity<ComentarioResponseDto> adicionarComentario(@Parameter(description = "ID do chamado.", required = true, example = "1") Long chamadoId, ComentarioRequestDto request);

    @Operation(summary = "Listar comentários", description = "Retorna os comentários registrados para o chamado informado.")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Comentários encontrados."), @ApiResponse(responseCode = "404", description = "Chamado não encontrado.", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))})
    ResponseEntity<List<ComentarioResponseDto>> listarComentarios(@Parameter(description = "ID do chamado.", required = true, example = "1") Long chamadoId);
}
