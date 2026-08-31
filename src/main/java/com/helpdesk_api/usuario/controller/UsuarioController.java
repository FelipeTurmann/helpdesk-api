package com.helpdesk_api.usuario.controller;

import com.helpdesk_api.usuario.dto.UsuarioFiltroConsultaDto;
import com.helpdesk_api.usuario.dto.UsuarioRequestDto;
import com.helpdesk_api.usuario.dto.UsuarioResponseDto;
import com.helpdesk_api.usuario.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/usuarios")
@PreAuthorize("hasRole('ADMIN')")
public class UsuarioController {

    private final UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<UsuarioResponseDto> criarUsuario(@Valid @RequestBody UsuarioRequestDto request) {
        UsuarioResponseDto response = usuarioService.criarUsuario(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<UsuarioResponseDto>> listarUsuarios(@ParameterObject UsuarioFiltroConsultaDto filtro) {
        return ResponseEntity.ok(usuarioService.listarUsuarios(filtro));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDto> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(usuarioService.buscarPorId(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        usuarioService.excluirUsuario(id);
        return ResponseEntity.noContent().build();
    }

}
