package com.helpdesk_api.comentario.service;

import com.helpdesk_api.chamado.entity.ChamadoEntity;
import com.helpdesk_api.chamado.repository.ChamadoRepository;
import com.helpdesk_api.comentario.dto.ComentarioRequestDto;
import com.helpdesk_api.comentario.dto.ComentarioResponseDto;
import com.helpdesk_api.comentario.entity.ComentarioEntity;
import com.helpdesk_api.comentario.mapper.ComentarioMapper;
import com.helpdesk_api.comentario.repository.ComentarioRepository;
import com.helpdesk_api.enums.CargoEnum;
import com.helpdesk_api.exception.ResourceNotFoundException;
import com.helpdesk_api.usuario.entity.UsuarioEntity;
import com.helpdesk_api.util.UsuarioUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ComentarioService {

    private final ComentarioRepository comentarioRepository;
    private final ChamadoRepository chamadoRepository;
    private final ComentarioMapper comentarioMapper;
    private final UsuarioUtil usuarioUtil;

    @Transactional
    public ComentarioResponseDto adicionarComentario(Long chamadoId, ComentarioRequestDto request) {
        ChamadoEntity chamado = buscarChamado(chamadoId);
        UsuarioEntity usuario = usuarioUtil.usuarioAutenticado();

        validarAcessoAoChamado(chamado, usuario);

        ComentarioEntity comentario = ComentarioEntity.builder()
                .texto(request.texto())
                .chamado(chamado)
                .usuario(usuario)
                .build();

        ComentarioEntity salvo = comentarioRepository.save(comentario);
        return comentarioMapper.toResponseDto(salvo);
    }

    private ChamadoEntity buscarChamado(Long chamadoId) {
        return chamadoRepository.findById(chamadoId)
                .orElseThrow(() -> new ResourceNotFoundException("Chamado não encontrado: " + chamadoId));
    }

    private void validarAcessoAoChamado(ChamadoEntity chamado, UsuarioEntity usuario) {
        if (usuario.getCargo() == CargoEnum.CLIENTE
                && !chamado.getEmpresa().getId().equals(usuario.getEmpresa().getId())) {
            //TODO tratar exception "Você não tem permissão para comentar neste chamado."
        }
    }
}
