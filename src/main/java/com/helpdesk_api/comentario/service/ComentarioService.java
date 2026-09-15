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
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ComentarioService {

    private final ComentarioRepository comentarioRepository;
    private final ChamadoRepository chamadoRepository;
    private final ComentarioMapper comentarioMapper;
    private final UsuarioUtil usuarioUtil;

    @Transactional
    public ComentarioResponseDto adicionarComentario(Long chamadoId, ComentarioRequestDto request) {

        log.info("Iniciando adição de comentário ao chamado. chamadoId={}", chamadoId);

        ChamadoEntity chamado = buscarChamado(chamadoId);
        UsuarioEntity usuario = usuarioUtil.usuarioAutenticado();

        log.debug("Usuário autenticado para adição de comentário. usuarioId={}, cargo={}, "
                        + "chamadoId={}, empresaId={}",
                usuario.getId(),
                usuario.getCargo(),
                chamadoId,
                usuario.getEmpresa() != null ? usuario.getEmpresa().getId() : null);

        validarAcessoAoChamado(chamado, usuario);

        ComentarioEntity comentario = ComentarioEntity.builder()
                .texto(request.texto())
                .chamado(chamado)
                .usuario(usuario)
                .build();

        ComentarioEntity salvo = comentarioRepository.save(comentario);

        log.info("Comentário adicionado com sucesso. comentarioId={}, chamadoId={}, usuarioId={}",
                salvo.getId(),
                chamadoId,
                usuario.getId());

        return comentarioMapper.toResponseDto(salvo);
    }

    @Transactional(readOnly = true)
    public List<ComentarioResponseDto> listarComentarios(Long chamadoId) {

        log.info("Iniciando listagem de comentários do chamado. chamadoId={}", chamadoId);

        ChamadoEntity chamado = buscarChamado(chamadoId);
        UsuarioEntity usuario = usuarioUtil.usuarioAutenticado();

        log.debug("Usuário autenticado para listagem de comentários. usuarioId={}, cargo={}, "
                        + "chamadoId={}, empresaId={}",
                usuario.getId(),
                usuario.getCargo(),
                chamadoId,
                usuario.getEmpresa() != null ? usuario.getEmpresa().getId() : null);

        validarAcessoAoChamado(chamado, usuario);

        List<ComentarioResponseDto> comentarios =
                comentarioRepository.findByChamadoIdOrderByDataComentarioAsc(chamadoId).stream()
                        .map(comentarioMapper::toResponseDto)
                        .toList();

        log.info("Listagem de comentários concluída com sucesso. chamadoId={}, quantidade={}",
                chamadoId,
                comentarios.size());

        return comentarios;
    }

    private ChamadoEntity buscarChamado(Long chamadoId) {

        log.debug("Buscando chamado pelo id={}", chamadoId);

        return chamadoRepository.findById(chamadoId)
                .orElseThrow(() -> {
                    log.warn("Chamado não encontrado. chamadoId={}", chamadoId);

                    return new ResourceNotFoundException(
                            "Chamado não encontrado: " + chamadoId
                    );
                });
    }

    private void validarAcessoAoChamado(
            ChamadoEntity chamado,
            UsuarioEntity usuario) {

        log.debug("Validando acesso ao chamado para comentário. chamadoId={}, usuarioId={}, cargo={}",
                chamado.getId(),
                usuario.getId(),
                usuario.getCargo());

        if (usuario.getCargo() == CargoEnum.CLIENTE
                && !chamado.getEmpresa().getId().equals(usuario.getEmpresa().getId())) {

            log.warn("Acesso negado ao chamado para comentário. chamadoId={}, usuarioId={}, "
                            + "empresaChamadoId={}, empresaUsuarioId={}",
                    chamado.getId(),
                    usuario.getId(),
                    chamado.getEmpresa().getId(),
                    usuario.getEmpresa().getId());

            // TODO tratar exception "Você não tem permissão para comentar neste chamado."
        }

        log.debug("Acesso ao chamado validado com sucesso. chamadoId={}, usuarioId={}",
                chamado.getId(),
                usuario.getId());
    }
}