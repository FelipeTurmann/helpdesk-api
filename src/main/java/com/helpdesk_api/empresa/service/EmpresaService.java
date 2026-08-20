package com.helpdesk_api.empresa.service;

import com.helpdesk_api.empresa.dto.EmpresaRequestDto;
import com.helpdesk_api.empresa.dto.EmpresaResponseDto;
import com.helpdesk_api.empresa.entity.EmpresaEntity;
import com.helpdesk_api.empresa.mapper.EmpresaMapper;
import com.helpdesk_api.exception.BusinessException;
import com.helpdesk_api.repository.EmpresaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmpresaService {

    private static EmpresaRepository empresaRepository;
    private static EmpresaMapper empresaMapper;

    public EmpresaResponseDto criarEmpresa(EmpresaRequestDto request) {

        if (empresaRepository.existsByEmail(request.cnpj())) {
            throw new BusinessException("Já existe uma empresa com esse cnpj cadastrada.");
        }

        EmpresaEntity empresaEntity = empresaMapper.toEntity(request);
        empresaEntity.setAtivo(Boolean.TRUE);

        EmpresaEntity empresaSalva = empresaRepository.save(empresaEntity);
        return empresaMapper.toResponseDTO(empresaSalva);
    }
}
