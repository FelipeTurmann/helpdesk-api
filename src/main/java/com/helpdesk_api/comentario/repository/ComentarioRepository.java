package com.helpdesk_api.comentario.repository;

import com.helpdesk_api.comentario.entity.ComentarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComentarioRepository extends JpaRepository<ComentarioEntity, Long> {

}
