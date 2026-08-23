package com.helpdesk_api.empresa.controller;

import com.helpdesk_api.empresa.dto.EmpresaFiltroConsultaDto;
import com.helpdesk_api.empresa.dto.EmpresaRequestDto;
import com.helpdesk_api.empresa.dto.EmpresaResponseDto;
import com.helpdesk_api.empresa.service.EmpresaService;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/empresas")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class EmpresaController {

    private final EmpresaService empresaService;

    @PostMapping
    public ResponseEntity<EmpresaResponseDto> criarEmpresa(@Valid @RequestBody EmpresaRequestDto requestDto) {
        EmpresaResponseDto response = empresaService.criarEmpresa(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<EmpresaResponseDto>> listarEmpresas(@ParameterObject EmpresaFiltroConsultaDto requestDto) {
        List<EmpresaResponseDto> response = empresaService.listarEmpresas(requestDto);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping({"/{idEmpresa}"})
    public ResponseEntity<EmpresaResponseDto> listarEmpresaPorId(@PathVariable Long idEmpresa) {
        EmpresaResponseDto response = empresaService.listarEmpresaPorId(idEmpresa);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
