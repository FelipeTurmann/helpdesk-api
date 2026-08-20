package com.helpdesk_api.empresa.mapper;

import com.helpdesk_api.empresa.dto.EmpresaRequestDto;
import com.helpdesk_api.empresa.dto.EmpresaResponseDto;
import com.helpdesk_api.empresa.entity.EmpresaEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface EmpresaMapper {

    EmpresaEntity toEntity(EmpresaRequestDto dto);

    EmpresaResponseDto toResponseDTO(EmpresaEntity entity);

    void updateEntityFromDto(EmpresaRequestDto  dto, @MappingTarget EmpresaEntity entity);
}
