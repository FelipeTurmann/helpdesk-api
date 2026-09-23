package com.helpdesk_api.empresa.controller.doc;

import com.helpdesk_api.empresa.dto.EmpresaFiltroConsultaDto;
import com.helpdesk_api.empresa.dto.EmpresaRequestDto;
import com.helpdesk_api.empresa.dto.EmpresaResponseDto;
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

@Tag(name = "Empresas", description = "Cadastro e manutenção das empresas atendidas. Requer perfil ADMIN.")
public interface EmpresaControllerDoc {
    @Operation(summary = "Criar empresa", description = "Cadastra uma empresa para vincular usuários e chamados.")
    @RequestBody(required = true, content = @Content(schema = @Schema(implementation = EmpresaRequestDto.class)))
    @ApiResponses({@ApiResponse(responseCode = "201", description = "Empresa criada.", content = @Content(schema = @Schema(implementation = EmpresaResponseDto.class))), @ApiResponse(responseCode = "400", description = "Dados inválidos.", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))), @ApiResponse(responseCode = "409", description = "CNPJ já cadastrado.", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))})
    ResponseEntity<EmpresaResponseDto> criarEmpresa(EmpresaRequestDto requestDto);

    @Operation(summary = "Listar empresas", description = "Retorna empresas conforme os filtros opcionais de cadastro.")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Empresas encontradas."), @ApiResponse(responseCode = "403", description = "Acesso restrito a administradores.", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))})
    ResponseEntity<List<EmpresaResponseDto>> listarEmpresas(@Parameter(description = "Filtros opcionais da consulta.") EmpresaFiltroConsultaDto requestDto);

    @Operation(summary = "Buscar empresa por ID")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Empresa encontrada.", content = @Content(schema = @Schema(implementation = EmpresaResponseDto.class))), @ApiResponse(responseCode = "404", description = "Empresa não encontrada.", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))})
    ResponseEntity<EmpresaResponseDto> listarEmpresaPorId(@Parameter(description = "ID da empresa.", required = true, example = "1") Long idEmpresa);

    @Operation(summary = "Atualizar empresa")
    @RequestBody(required = true, content = @Content(schema = @Schema(implementation = EmpresaRequestDto.class)))
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Empresa atualizada.", content = @Content(schema = @Schema(implementation = EmpresaResponseDto.class))), @ApiResponse(responseCode = "400", description = "Dados inválidos.", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))), @ApiResponse(responseCode = "404", description = "Empresa não encontrada.", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))})
    ResponseEntity<EmpresaResponseDto> atualizar(@Parameter(description = "ID da empresa.", required = true) Long id, EmpresaRequestDto request);

    @Operation(summary = "Excluir empresa")
    @ApiResponses({@ApiResponse(responseCode = "204", description = "Empresa excluída."), @ApiResponse(responseCode = "404", description = "Empresa não encontrada.", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))), @ApiResponse(responseCode = "409", description = "Empresa possui registros vinculados.", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))})
    ResponseEntity<Void> excluir(@Parameter(description = "ID da empresa.", required = true) Long id);
}
