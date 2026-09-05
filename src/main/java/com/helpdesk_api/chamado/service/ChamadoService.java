package com.helpdesk_api.chamado.service;

import com.helpdesk_api.chamado.dto.ChamadoRequestDto;
import com.helpdesk_api.chamado.dto.ChamadoResponseDto;
import com.helpdesk_api.chamado.entity.ChamadoEntity;
import com.helpdesk_api.chamado.mapper.ChamadoMapper;
import com.helpdesk_api.chamado.repository.ChamadoRepository;
import com.helpdesk_api.enums.CargoEnum;
import com.helpdesk_api.enums.StatusChamadoEnum;
import com.helpdesk_api.exception.ResourceNotFoundException;
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

    @Transactional(readOnly = true)
    public ChamadoResponseDto buscarChamadoPorId(Long id) {
        ChamadoEntity chamado = buscarEntidadePorId(id);
        validarAcessoAoChamado(chamado);
        return chamadoMapper.toResponseDto(chamado);
    }

    // CLIENTE só acessa chamados da própria empresa. ADMIN acessa qualquer um.
    private void validarAcessoAoChamado(ChamadoEntity chamado) {
        UsuarioEntity usuario = usuarioUtil.usuarioAutenticado();
        if (usuario.getCargo() == CargoEnum.CLIENTE
                && !chamado.getEmpresa().getId().equals(usuario.getEmpresa().getId())) {
            // TODO TRATAR EXCEPTION "Você não tem permissão para acessar este chamado."
        }
    }

    private ChamadoEntity buscarEntidadePorId(Long id) {
        return chamadoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Chamado não encontrado: " + id));
    }
}
