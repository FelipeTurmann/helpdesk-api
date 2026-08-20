package com.helpdesk_api.empresa.controller;

import com.helpdesk_api.empresa.dto.EmpresaRequestDto;
import com.helpdesk_api.empresa.dto.EmpresaResponseDto;
import com.helpdesk_api.empresa.service.EmpresaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/empresas")
@RequiredArgsConstructor
public class EmpresaController {

    private final EmpresaService empresaService;

    @PostMapping
    public ResponseEntity<EmpresaResponseDto> criarEmpresa(@Valid @RequestBody EmpresaRequestDto requestDto) {

        EmpresaResponseDto response = empresaService.criarEmpresa(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }


}
