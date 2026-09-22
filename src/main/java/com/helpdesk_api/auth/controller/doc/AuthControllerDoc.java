package com.helpdesk_api.auth.controller.doc;

import com.helpdesk_api.auth.dto.LoginRequestDto;
import com.helpdesk_api.auth.dto.LoginResponseDto;
import com.helpdesk_api.exception.ErrorResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "Autenticação", description = "Emissão de token JWT para acesso aos recursos protegidos.")
public interface AuthControllerDoc {

    @Operation(summary = "Autenticar usuário", description = "Valida e-mail e senha e retorna um token JWT Bearer.")
    @SecurityRequirements
    @RequestBody(required = true, description = "Credenciais do usuário.", content = @Content(schema = @Schema(implementation = LoginRequestDto.class)))
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Autenticação realizada.", content = @Content(schema = @Schema(implementation = LoginResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Credenciais inválidas.", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "401", description = "E-mail ou senha inválidos.", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    ResponseEntity<LoginResponseDto> login(LoginRequestDto request);
}
