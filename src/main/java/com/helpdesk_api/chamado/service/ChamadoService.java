package com.helpdesk_api.chamado.service;

import com.helpdesk_api.chamado.dto.ChamadoRequestDto;
import com.helpdesk_api.chamado.dto.ChamadoResponseDto;
import com.helpdesk_api.chamado.entity.ChamadoEntity;
import com.helpdesk_api.chamado.mapper.ChamadoMapper;
import com.helpdesk_api.chamado.repository.ChamadoRepository;
import com.helpdesk_api.enums.CargoEnum;
import com.helpdesk_api.enums.StatusChamadoEnum;
import com.helpdesk_api.exception.BusinessException;
import com.helpdesk_api.exception.ResourceNotFoundException;
import com.helpdesk_api.usuario.entity.UsuarioEntity;
import com.helpdesk_api.util.UsuarioUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChamadoService {

    private final ChamadoRepository chamadoRepository;
    private final ChamadoMapper chamadoMapper;
    private final UsuarioUtil usuarioUtil;

    @Transactional
    public ChamadoResponseDto abrirChamado(ChamadoRequestDto request) {
        log.info("Iniciando abertura de chamado.");

        UsuarioEntity usuario = usuarioUtil.usuarioAutenticado();

        log.debug("Usuário autenticado para abertura do chamado. id={}, cargo={}, empresaId={}",
                usuario.getId(),
                usuario.getCargo(),
                usuario.getEmpresa() != null ? usuario.getEmpresa().getId() : null);

        ChamadoEntity chamado = chamadoMapper.toEntity(request);
        chamado.setStatus(StatusChamadoEnum.ABERTO);
        chamado.setEmpresa(usuario.getEmpresa());
        chamado.setUsuarioAbertura(usuario);

        ChamadoEntity salvo = chamadoRepository.save(chamado);

        log.info("Chamado aberto com sucesso. id={}, usuarioAberturaId={}, empresaId={}",
                salvo.getId(),
                usuario.getId(),
                usuario.getEmpresa() != null ? usuario.getEmpresa().getId() : null);

        return chamadoMapper.toResponseDto(salvo);
    }

    @Transactional(readOnly = true)
    public ChamadoResponseDto buscarChamadoPorId(Long id) {
        log.info("Iniciando busca de chamado. id={}", id);

        ChamadoEntity chamado = buscarEntidadePorId(id);

        validarAcessoAoChamado(chamado);

        log.info("Chamado encontrado com sucesso. id={}, status={}",
                chamado.getId(),
                chamado.getStatus());

        return chamadoMapper.toResponseDto(chamado);
    }

    @Transactional
    public ChamadoResponseDto atualizarChamado(Long id, ChamadoRequestDto request) {
        log.info("Iniciando atualização de chamado. id={}", id);

        ChamadoEntity chamado = buscarEntidadePorId(id);

        validarAcessoAoChamado(chamado);

        log.debug("Validando status do chamado para atualização. id={}, status={}",
                id,
                chamado.getStatus());

        if (chamado.getStatus() != StatusChamadoEnum.ABERTO) {
            log.warn("Chamado não pode ser atualizado pois não está ABERTO. id={}, status={}",
                    id,
                    chamado.getStatus());

            throw new BusinessException(
                    "Chamado só pode ser editado enquanto estiver ABERTO."
            );
        }

        chamadoMapper.updateEntityFromDto(request, chamado);

        ChamadoEntity atualizado = chamadoRepository.save(chamado);

        log.info("Chamado atualizado com sucesso. id={}, status={}",
                atualizado.getId(),
                atualizado.getStatus());

        return chamadoMapper.toResponseDto(atualizado);
    }

    // CLIENTE só acessa chamados da própria empresa. ADMIN acessa qualquer um.
    private void validarAcessoAoChamado(ChamadoEntity chamado) {
        UsuarioEntity usuario = usuarioUtil.usuarioAutenticado();

        log.debug("Validando acesso ao chamado. chamadoId={}, usuarioId={}, cargo={}",
                chamado.getId(),
                usuario.getId(),
                usuario.getCargo());

        if (usuario.getCargo() == CargoEnum.CLIENTE
                && !chamado.getEmpresa().getId().equals(usuario.getEmpresa().getId())) {

            log.warn("Acesso negado ao chamado. chamadoId={}, usuarioId={}, empresaChamadoId={}, empresaUsuarioId={}",
                    chamado.getId(),
                    usuario.getId(),
                    chamado.getEmpresa().getId(),
                    usuario.getEmpresa().getId());

            // TODO TRATAR EXCEPTION "Você não tem permissão para acessar este chamado."
        }

        log.debug("Acesso ao chamado validado com sucesso. chamadoId={}, usuarioId={}",
                chamado.getId(),
                usuario.getId());
    }

    private ChamadoEntity buscarEntidadePorId(Long id) {
        log.debug("Buscando chamado pelo id={}", id);

        return chamadoRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Chamado não encontrado. id={}", id);

                    return new ResourceNotFoundException(
                            "Chamado não encontrado: " + id
                    );
                });
    }
}