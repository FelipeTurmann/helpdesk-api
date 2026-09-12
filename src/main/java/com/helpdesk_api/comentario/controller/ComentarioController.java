package com.helpdesk_api.comentario.controller;

import com.helpdesk_api.comentario.dto.ComentarioRequestDto;
import com.helpdesk_api.comentario.dto.ComentarioResponseDto;
import com.helpdesk_api.comentario.service.ComentarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/chamados/{chamadoId}/comentarios")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('ADMIN', 'CLIENTE')")
public class ComentarioController {

    private final ComentarioService comentarioService;

    @PostMapping
    public ResponseEntity<ComentarioResponseDto> adicionarComentario(
            @PathVariable Long chamadoId,
            @Valid @RequestBody ComentarioRequestDto request
    ) {
        ComentarioResponseDto response = comentarioService.adicionarComentario(chamadoId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

}
