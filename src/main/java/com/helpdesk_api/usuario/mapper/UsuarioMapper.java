package com.helpdesk_api.usuario.mapper;

import com.helpdesk_api.usuario.dto.UsuarioRequestDto;
import com.helpdesk_api.usuario.dto.UsuarioResponseDto;
import com.helpdesk_api.usuario.entity.UsuarioEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    @Mapping(target = "empresa", ignore = true)
    UsuarioEntity toEntity(UsuarioRequestDto dto);

    @Mapping(target = "empresaId", source = "empresa.id")
    @Mapping(target = "empresaNome", source = "empresa.nome")
    UsuarioResponseDto toResponseDto(UsuarioEntity entity);

    @Mapping(target = "empresa", ignore = true)
    @Mapping(target = "senha", ignore = true)
    void updateEntityFromDto(UsuarioRequestDto dto, @MappingTarget UsuarioEntity entity);
}
