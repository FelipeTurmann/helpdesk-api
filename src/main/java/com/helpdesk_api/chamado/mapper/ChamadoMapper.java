package com.helpdesk_api.chamado.mapper;

import com.helpdesk_api.chamado.dto.ChamadoRequestDto;
import com.helpdesk_api.chamado.dto.ChamadoResponseDto;
import com.helpdesk_api.chamado.entity.ChamadoEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ChamadoMapper {

    @Mapping(target = "status", ignore = true)
    @Mapping(target = "empresa", ignore = true)
    @Mapping(target = "usuarioAbertura", ignore = true)
    ChamadoEntity toEntity(ChamadoRequestDto dto);

    @Mapping(target = "empresaId", source = "empresa.id")
    @Mapping(target = "empresaNome", source = "empresa.nome")
    @Mapping(target = "usuarioAberturaId", source = "usuarioAbertura.id")
    @Mapping(target = "usuarioAberturaNome", source = "usuarioAbertura.nome")
    ChamadoResponseDto toResponseDto(ChamadoEntity entity);
}
