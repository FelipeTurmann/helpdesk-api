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
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmpresaService {

    private final EmpresaRepository empresaRepository;
    private final EmpresaMapper empresaMapper;

    public EmpresaResponseDto criarEmpresa(EmpresaRequestDto request) {

        if (empresaRepository.existsByCnpj(request.cnpj())) {
            throw new BusinessException("Já existe uma empresa com esse CNPJ cadastrada.");
        }

        EmpresaEntity empresaEntity = empresaMapper.toEntity(request);
        empresaEntity.setAtivo(Boolean.TRUE);

        EmpresaEntity empresaSalva = empresaRepository.save(empresaEntity);
        return empresaMapper.toResponseDTO(empresaSalva);
    }

    @Transactional(readOnly = true)
    public List<EmpresaResponseDto> listarEmpresas(EmpresaFiltroConsultaDto filtro) {
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
            throw new BusinessException("Não existe nenhuma empresa registrada com esses filtros.");
        }

        return listaEmpresasFiltradas;
    }

    @Transactional(readOnly = true)
    public EmpresaResponseDto listarEmpresaPorId(Long idEmpresa) {

        EmpresaEntity empresa = buscarEntidadePorId(idEmpresa);

        return empresaMapper.toResponseDTO(empresa);
    }

    @Transactional
    public void excluir(Long idEmpresa) {
        EmpresaEntity empresa = buscarEntidadePorId(idEmpresa);
        empresaRepository.delete(empresa);
    }

    private EmpresaEntity buscarEntidadePorId(Long id) {
        return empresaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Empresa não encontrada: " + id));
    }
}