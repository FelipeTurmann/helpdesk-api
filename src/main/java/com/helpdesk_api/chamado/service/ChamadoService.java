package com.helpdesk_api.chamado.service;

import com.helpdesk_api.chamado.dto.ChamadoRequestDto;
import com.helpdesk_api.chamado.dto.ChamadoResponseDto;
import com.helpdesk_api.chamado.entity.ChamadoEntity;
import com.helpdesk_api.chamado.mapper.ChamadoMapper;
import com.helpdesk_api.chamado.repository.ChamadoRepository;
import com.helpdesk_api.enums.StatusChamadoEnum;
import com.helpdesk_api.usuario.entity.UsuarioEntity;
import com.helpdesk_api.util.UsuarioUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ChamadoService {

    private final ChamadoRepository chamadoRepository;
    private final ChamadoMapper chamadoMapper;
    private final UsuarioUtil usuarioUtil;

    @Transactional
    public ChamadoResponseDto abrirChamado(ChamadoRequestDto request) {
        UsuarioEntity usuario =  usuarioUtil.usuarioAutenticado();

        ChamadoEntity chamado = chamadoMapper.toEntity(request);
        chamado.setStatus(StatusChamadoEnum.ABERTO);
        chamado.setEmpresa(usuario.getEmpresa());
        chamado.setUsuarioAbertura(usuario);

        ChamadoEntity salvo = chamadoRepository.save(chamado);
        return chamadoMapper.toResponseDto(salvo);
    }
}
