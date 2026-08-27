package com.helpdesk_api.usuario.service;

import com.helpdesk_api.empresa.entity.EmpresaEntity;
import com.helpdesk_api.empresa.repository.EmpresaRepository;
import com.helpdesk_api.enums.CargoEnum;
import com.helpdesk_api.exception.BusinessException;
import com.helpdesk_api.exception.ResourceNotFoundException;
import com.helpdesk_api.usuario.dto.UsuarioRequestDto;
import com.helpdesk_api.usuario.dto.UsuarioResponseDto;
import com.helpdesk_api.usuario.entity.UsuarioEntity;
import com.helpdesk_api.usuario.mapper.UsuarioMapper;
import com.helpdesk_api.usuario.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final EmpresaRepository empresaRepository;
    private final UsuarioMapper usuarioMapper;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UsuarioResponseDto criarUsuario(UsuarioRequestDto request) {
        if (usuarioRepository.existsByEmail(request.email())) {
            throw new BusinessException("Já existe um usuário cadastrado com este email.");
        }

        validarEmpresaObrigatoria(request.cargo(), request.empresaId());
        EmpresaEntity empresa = buscarEmpresa(request.empresaId());

        UsuarioEntity usuario = usuarioMapper.toEntity(request);
        usuario.setSenha(passwordEncoder.encode(request.senha()));
        usuario.setAtivo(Boolean.TRUE);
        usuario.setEmpresa(empresa);

        UsuarioEntity salvo = usuarioRepository.save(usuario);
        return usuarioMapper.toResponseDto(salvo);
    }

    private void validarEmpresaObrigatoria(CargoEnum cargo, Long empresaId) {
        if (cargo == CargoEnum.CLIENTE && empresaId == null) {
            throw new BusinessException(
                    "Usuário com cargo CLIENTE deve estar vinculado a uma empresa."
            );
        }
    }

    private EmpresaEntity buscarEmpresa(Long empresaId) {
        if (empresaId == null) {
            return null;
        }

        return empresaRepository.findById(empresaId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Empresa não encontrada: " + empresaId)
                );
    }

}
