package com.helpdesk_api.comentario.mapper;

import com.helpdesk_api.comentario.dto.ComentarioResponseDto;
import com.helpdesk_api.comentario.entity.ComentarioEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ComentarioMapper {

    @Mapping(target = "usuarioId", source = "usuario.id")
    @Mapping(target = "usuarioNome", source = "usuario.nome")
    @Mapping(target = "chamadoId", source = "chamado.id")
    ComentarioResponseDto toResponseDto(ComentarioEntity entity);
}
