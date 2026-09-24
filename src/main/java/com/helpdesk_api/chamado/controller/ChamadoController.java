package com.helpdesk_api.chamado.controller;

import com.helpdesk_api.chamado.dto.ChamadoFiltroConsultaDto;
import com.helpdesk_api.chamado.dto.ChamadoRequestDto;
import com.helpdesk_api.chamado.dto.ChamadoResponseDto;
import com.helpdesk_api.chamado.dto.ChamadoStatusUpdateDto;
import com.helpdesk_api.chamado.controller.doc.ChamadoControllerDoc;
import com.helpdesk_api.chamado.service.ChamadoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chamados")
@RequiredArgsConstructor
public class ChamadoController implements ChamadoControllerDoc {

    private final ChamadoService chamadoService;

    @PostMapping
    @Override
    @PreAuthorize("hasRole('CLIENTE')")
    public ResponseEntity<ChamadoResponseDto> abrirChamado(@Valid @RequestBody ChamadoRequestDto request) {
        ChamadoResponseDto response = chamadoService.abrirChamado(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    @Override
    @PreAuthorize("hasAnyRole('ADMIN', 'CLIENTE')")
    public ResponseEntity<List<ChamadoResponseDto>> listarChamados(@ParameterObject ChamadoFiltroConsultaDto filtro) {
        return ResponseEntity.ok(chamadoService.listarChamados(filtro));
    }

    @GetMapping("/{id}")
    @Override
    @PreAuthorize("hasAnyRole('ADMIN', 'CLIENTE')")
    public ResponseEntity<ChamadoResponseDto> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(chamadoService.buscarChamadoPorId(id));
    }

    @PutMapping("/{id}")
    @Override
    @PreAuthorize("hasRole('CLIENTE')")
    public ResponseEntity<ChamadoResponseDto> atualizarChamado(
            @PathVariable Long id,
            @Valid @RequestBody ChamadoRequestDto request
    ) {
        return ResponseEntity.ok(chamadoService.atualizarChamado(id, request));
    }

    @PatchMapping("/{id}/status")
    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ChamadoResponseDto> alterarStatus(
            @PathVariable Long id,
            @Valid @RequestBody ChamadoStatusUpdateDto dto
    ) {
        return ResponseEntity.ok(chamadoService.alterarStatus(id, dto));
    }

    @DeleteMapping("/{id}")
    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        chamadoService.excluirChamado(id);
        return ResponseEntity.noContent().build();
    }
}
