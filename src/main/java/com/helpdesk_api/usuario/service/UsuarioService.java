package com.helpdesk_api.usuario.service;

import com.helpdesk_api.empresa.entity.EmpresaEntity;
import com.helpdesk_api.empresa.repository.EmpresaRepository;
import com.helpdesk_api.enums.CargoEnum;
import com.helpdesk_api.exception.BusinessException;
import com.helpdesk_api.exception.ResourceNotFoundException;
import com.helpdesk_api.usuario.dto.UsuarioFiltroConsultaDto;
import com.helpdesk_api.usuario.dto.UsuarioRequestDto;
import com.helpdesk_api.usuario.dto.UsuarioResponseDto;
import com.helpdesk_api.usuario.entity.UsuarioEntity;
import com.helpdesk_api.usuario.mapper.UsuarioMapper;
import com.helpdesk_api.usuario.repository.UsuarioRepository;
import com.helpdesk_api.usuario.repository.UsuarioSpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final EmpresaRepository empresaRepository;
    private final UsuarioMapper usuarioMapper;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UsuarioResponseDto criarUsuario(UsuarioRequestDto request) {
        log.info("Iniciando criação de usuário. email={}, cargo={}, empresaId={}",
                request.email(), request.cargo(), request.empresaId());

        if (usuarioRepository.existsByEmail(request.email())) {
            log.warn("Usuário já cadastrado para o email={}", request.email());
            throw new BusinessException("Já existe um usuário cadastrado com este email.");
        }

        validarEmpresaObrigatoria(request.cargo(), request.empresaId());
        EmpresaEntity empresa = buscarEmpresa(request.empresaId());

        UsuarioEntity usuario = usuarioMapper.toEntity(request);
        usuario.setSenha(passwordEncoder.encode(request.senha()));
        usuario.setAtivo(Boolean.TRUE);
        usuario.setEmpresa(empresa);

        UsuarioEntity salvo = usuarioRepository.save(usuario);

        log.info("Usuário criado com sucesso. id={}, email={}",
                salvo.getId(), salvo.getEmail());

        return usuarioMapper.toResponseDto(salvo);
    }

    @Transactional(readOnly = true)
    public List<UsuarioResponseDto> listarUsuarios(UsuarioFiltroConsultaDto filtro) {
        log.info("Iniciando busca de usuários. nome={}, email={}, cargo={}, empresaId={}, ativo={}",
                filtro.nome(),
                filtro.email(),
                filtro.cargo(),
                filtro.empresaId(),
                filtro.ativo());

        Specification<UsuarioEntity> specification = Specification.allOf(
                UsuarioSpecification.comNome(filtro.nome()),
                UsuarioSpecification.comEmail(filtro.email()),
                UsuarioSpecification.comCargo(filtro.cargo()),
                UsuarioSpecification.comEmpresaId(filtro.empresaId()),
                UsuarioSpecification.comAtivo(filtro.ativo())
        );

        List<UsuarioResponseDto> usuarios = usuarioRepository.findAll(specification).stream()
                .map(usuarioMapper::toResponseDto)
                .toList();

        log.info("Busca finalizada com sucesso. Quantidade de usuários={}",
                usuarios.size());

        return usuarios;
    }

    @Transactional(readOnly = true)
    public UsuarioResponseDto buscarUsuarioPorId(Long id) {
        log.info("Iniciando busca de usuário. id={}", id);

        UsuarioEntity usuario = buscarEntidadePorId(id);

        log.info("Usuário encontrado com sucesso. id={}, email={}",
                usuario.getId(), usuario.getEmail());

        return usuarioMapper.toResponseDto(usuario);
    }

    @Transactional
    public UsuarioResponseDto atualizarUsuario(Long id, UsuarioRequestDto request) {
        log.info("Iniciando atualização de usuário. id={}, email={}, cargo={}, empresaId={}",
                id,
                request.email(),
                request.cargo(),
                request.empresaId());

        UsuarioEntity usuario = buscarEntidadePorId(id);

        if (!usuario.getEmail().equals(request.email())
                && usuarioRepository.existsByEmail(request.email())) {

            log.warn("Não foi possível atualizar. Email já cadastrado. id={}, email={}",
                    id, request.email());

            throw new BusinessException("Já existe um usuário cadastrado com este email.");
        }

        // validarEmpresaObrigatoria(request.cargo(), request.empresaId()); desabilitado temporariamente
        EmpresaEntity empresa = buscarEmpresa(request.empresaId());

        usuarioMapper.updateEntityFromDto(request, usuario);
        usuario.setEmpresa(empresa);

        // so re-hasheia a senha se uma nova senha foi realmente enviada
        if (StringUtils.hasText(request.senha())) {
            log.info("Nova senha informada. Realizando hash da senha. id={}", id);
            usuario.setSenha(passwordEncoder.encode(request.senha()));
        }

        UsuarioEntity atualizado = usuarioRepository.save(usuario);

        log.info("Usuário atualizado com sucesso. id={}, email={}",
                atualizado.getId(), atualizado.getEmail());

        return usuarioMapper.toResponseDto(atualizado);
    }

    @Transactional
    public void excluirUsuario(Long id) {
        log.info("Iniciando exclusão de usuário. id={}", id);

        UsuarioEntity usuario = buscarEntidadePorId(id);

        usuarioRepository.delete(usuario);

        log.info("Usuário excluído com sucesso. id={}, email={}",
                usuario.getId(), usuario.getEmail());
    }

    private void validarEmpresaObrigatoria(CargoEnum cargo, Long empresaId) {
        log.debug("Validando empresa obrigatória. cargo={}, empresaId={}",
                cargo, empresaId);

        if (cargo == CargoEnum.CLIENTE && empresaId == null) {
            log.warn("Usuário CLIENTE sem empresa vinculada.");

            throw new BusinessException(
                    "Usuário com cargo CLIENTE deve estar vinculado a uma empresa."
            );
        }
    }

    private UsuarioEntity buscarEntidadePorId(Long id) {
        log.debug("Buscando usuário pelo id={}", id);

        return usuarioRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Usuário não encontrado. id={}", id);

                    return new ResourceNotFoundException(
                            "Usuário não encontrado: " + id
                    );
                });
    }

    private EmpresaEntity buscarEmpresa(Long empresaId) {
        if (empresaId == null) {
            log.debug("Nenhuma empresa informada.");

            return null;
        }

        log.debug("Buscando empresa pelo id={}", empresaId);

        return empresaRepository.findById(empresaId)
                .orElseThrow(() -> {
                    log.warn("Empresa não encontrada. id={}", empresaId);

                    return new ResourceNotFoundException(
                            "Empresa não encontrada: " + empresaId
                    );
                });
    }
}
