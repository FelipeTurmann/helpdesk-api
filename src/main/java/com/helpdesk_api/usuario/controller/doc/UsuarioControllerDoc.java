package com.helpdesk_api.usuario.controller.doc;

import com.helpdesk_api.exception.ErrorResponseDto;
import com.helpdesk_api.usuario.dto.UsuarioFiltroConsultaDto;
import com.helpdesk_api.usuario.dto.UsuarioRequestDto;
import com.helpdesk_api.usuario.dto.UsuarioResponseDto;
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

@Tag(name = "Usuários", description = "Administração de usuários. Requer perfil ADMIN.")
public interface UsuarioControllerDoc {
    @Operation(summary = "Criar usuário", description = "Cria um usuário e o associa opcionalmente a uma empresa.")
    @RequestBody(required = true, content = @Content(schema = @Schema(implementation = UsuarioRequestDto.class)))
    @ApiResponses({@ApiResponse(responseCode = "201", description = "Usuário criado.", content = @Content(schema = @Schema(implementation = UsuarioResponseDto.class))), @ApiResponse(responseCode = "400", description = "Dados inválidos.", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))), @ApiResponse(responseCode = "403", description = "Acesso restrito a administradores.", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))), @ApiResponse(responseCode = "409", description = "E-mail já cadastrado.", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))})
    ResponseEntity<UsuarioResponseDto> criarUsuario(UsuarioRequestDto request);

    @Operation(summary = "Listar usuários", description = "Retorna usuários, com filtros opcionais por nome, e-mail, cargo, empresa e situação.")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Usuários encontrados."), @ApiResponse(responseCode = "403", description = "Acesso restrito a administradores.", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))})
    ResponseEntity<List<UsuarioResponseDto>> listarUsuarios(@Parameter(description = "Filtros opcionais da consulta.") UsuarioFiltroConsultaDto filtro);

    @Operation(summary = "Buscar usuário por ID")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Usuário encontrado.", content = @Content(schema = @Schema(implementation = UsuarioResponseDto.class))), @ApiResponse(responseCode = "404", description = "Usuário não encontrado.", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))})
    ResponseEntity<UsuarioResponseDto> buscarUsuarioPorId(@Parameter(description = "ID do usuário.", required = true, example = "1") Long id);

    @Operation(summary = "Atualizar usuário", description = "Substitui os dados cadastrais do usuário informado.")
    @RequestBody(required = true, content = @Content(schema = @Schema(implementation = UsuarioRequestDto.class)))
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Usuário atualizado.", content = @Content(schema = @Schema(implementation = UsuarioResponseDto.class))), @ApiResponse(responseCode = "400", description = "Dados inválidos.", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))), @ApiResponse(responseCode = "404", description = "Usuário não encontrado.", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))})
    ResponseEntity<UsuarioResponseDto> atualizarUsuario(@Parameter(description = "ID do usuário.", required = true) Long id, UsuarioRequestDto request);

    @Operation(summary = "Excluir usuário")
    @ApiResponses({@ApiResponse(responseCode = "204", description = "Usuário excluído."), @ApiResponse(responseCode = "404", description = "Usuário não encontrado.", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))})
    ResponseEntity<Void> excluir(@Parameter(description = "ID do usuário.", required = true) Long id);
}
