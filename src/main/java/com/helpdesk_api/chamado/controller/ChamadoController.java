package com.helpdesk_api.chamado.controller;

import com.helpdesk_api.chamado.dto.ChamadoRequestDto;
import com.helpdesk_api.chamado.dto.ChamadoResponseDto;
import com.helpdesk_api.chamado.service.ChamadoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/chamados")
@RequiredArgsConstructor
public class ChamadoController {

    private final ChamadoService chamadoService;

    @PostMapping
    @PreAuthorize("hasRole('CLIENTE')")
    public ResponseEntity<ChamadoResponseDto> abrirChamado(@Valid @RequestBody ChamadoRequestDto request) {
        ChamadoResponseDto response = chamadoService.abrirChamado(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'CLIENTE')")
    public ResponseEntity<ChamadoResponseDto> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(chamadoService.buscarChamadoPorId(id));
    }
}
