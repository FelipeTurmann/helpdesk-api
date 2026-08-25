package com.helpdesk_api.entity;

import com.helpdesk_api.usuario.entity.UsuarioEntity;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "comentario")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"usuario", "chamado"})
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Comentario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String texto;

    @CreationTimestamp
    @Column(name = "data_comentario", nullable = false, updatable = false)
    private LocalDateTime dataComentario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private UsuarioEntity usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chamado_id", nullable = false)
    private Chamado chamado;
}