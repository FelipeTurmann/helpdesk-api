package com.helpdesk_api.empresa.service;

import com.helpdesk_api.empresa.dto.EmpresaFiltroConsultaDto;
import com.helpdesk_api.empresa.dto.EmpresaRequestDto;
import com.helpdesk_api.empresa.dto.EmpresaResponseDto;
import com.helpdesk_api.empresa.entity.EmpresaEntity;
import com.helpdesk_api.empresa.mapper.EmpresaMapper;
import com.helpdesk_api.empresa.repository.EmpresaRepository;
import com.helpdesk_api.empresa.repository.EmpresaSpecification;
import com.helpdesk_api.exception.BusinessException;
import com.helpdesk_api.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmpresaService {

    private final EmpresaRepository empresaRepository;
    private final EmpresaMapper empresaMapper;

    public EmpresaResponseDto criarEmpresa(EmpresaRequestDto request) {
        log.info("Iniciando criação de empresa. cnpj={}", request.cnpj());

        if (empresaRepository.existsByCnpj(request.cnpj())) {
            log.warn("Não foi possível criar empresa. CNPJ já cadastrado. cnpj={}", request.cnpj());

            throw new BusinessException("Já existe uma empresa com esse CNPJ cadastrada.");
        }

        EmpresaEntity empresaEntity = empresaMapper.toEntity(request);
        empresaEntity.setAtivo(Boolean.TRUE);

        EmpresaEntity empresaSalva = empresaRepository.save(empresaEntity);

        log.info("Empresa criada com sucesso. id={}, cnpj={}",
                empresaSalva.getId(),
                empresaSalva.getCnpj());

        return empresaMapper.toResponseDTO(empresaSalva);
    }

    @Transactional(readOnly = true)
    public List<EmpresaResponseDto> listarEmpresas(EmpresaFiltroConsultaDto filtro) {
        log.info("Iniciando busca de empresas. nome={}, cnpj={}, telefone={}, email={}",
                filtro.nome(),
                filtro.cnpj(),
                filtro.telefone(),
                filtro.email());

        Specification<EmpresaEntity> specification = Specification.allOf(
                EmpresaSpecification.comNome(filtro.nome()),
                EmpresaSpecification.comCnpj(filtro.cnpj()),
                EmpresaSpecification.comTelefone(filtro.telefone()),
                EmpresaSpecification.comEmail(filtro.email())
        );

        List<EmpresaResponseDto> listaEmpresasFiltradas = empresaRepository.findAll(specification).stream()
                .map(empresaMapper::toResponseDTO)
                .toList();

        if (listaEmpresasFiltradas.isEmpty()) {
            log.warn("Nenhuma empresa encontrada com os filtros informados.");

            throw new BusinessException("Não existe nenhuma empresa registrada com esses filtros.");
        }

        log.info("Busca de empresas finalizada com sucesso. Quantidade encontrada={}",
                listaEmpresasFiltradas.size());

        return listaEmpresasFiltradas;
    }

    @Transactional(readOnly = true)
    public EmpresaResponseDto listarEmpresaPorId(Long idEmpresa) {
        log.info("Iniciando busca de empresa. id={}", idEmpresa);

        EmpresaEntity empresa = buscarEntidadePorId(idEmpresa);

        log.info("Empresa encontrada com sucesso. id={}, cnpj={}",
                empresa.getId(),
                empresa.getCnpj());

        return empresaMapper.toResponseDTO(empresa);
    }

    @Transactional
    public EmpresaResponseDto atualizar(Long id, EmpresaRequestDto request) {
        log.info("Iniciando atualização de empresa. id={}, cnpj={}",
                id,
                request.cnpj());

        EmpresaEntity empresa = buscarEntidadePorId(id);

        if (!empresa.getCnpj().equals(request.cnpj())
                && empresaRepository.existsByCnpj(request.cnpj())) {

            log.warn("Não foi possível atualizar empresa. CNPJ já cadastrado. id={}, cnpj={}",
                    id,
                    request.cnpj());

            throw new BusinessException("Já existe uma empresa cadastrada com este CNPJ");
        }

        empresaMapper.updateEntityFromDto(request, empresa);

        EmpresaEntity atualizada = empresaRepository.save(empresa);

        log.info("Empresa atualizada com sucesso. id={}, cnpj={}",
                atualizada.getId(),
                atualizada.getCnpj());

        return empresaMapper.toResponseDTO(atualizada);
    }

    @Transactional
    public void excluir(Long idEmpresa) {
        log.info("Iniciando exclusão de empresa. id={}", idEmpresa);

        EmpresaEntity empresa = buscarEntidadePorId(idEmpresa);

        empresaRepository.delete(empresa);

        log.info("Empresa excluída com sucesso. id={}, cnpj={}",
                empresa.getId(),
                empresa.getCnpj());
    }

    private EmpresaEntity buscarEntidadePorId(Long id) {
        log.debug("Buscando empresa pelo id={}", id);

        return empresaRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Empresa não encontrada. id={}", id);

                    return new ResourceNotFoundException(
                            "Empresa não encontrada: " + id
                    );
                });
    }
}