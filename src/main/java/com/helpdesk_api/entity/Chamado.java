package com.helpdesk_api.entity;

import com.helpdesk_api.empresa.entity.EmpresaEntity;
import com.helpdesk_api.enums.PrioridadeEnum;
import com.helpdesk_api.enums.StatusChamadoEnum;
import com.helpdesk_api.usuario.entity.UsuarioEntity;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "chamado")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"empresa", "usuarioAbertura", "comentarios"})
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Chamado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(nullable = false)
    private String titulo;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String descricao;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusChamadoEnum status;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PrioridadeEnum prioridade;

    @Column(nullable = false)
    private String categoria;

    @CreationTimestamp
    @Column(name = "data_abertura", nullable = false, updatable = false)
    private LocalDateTime dataAbertura;

    @UpdateTimestamp
    @Column(name = "data_atualizacao", nullable = false)
    private LocalDateTime dataAtualizacao;

    @Column(name = "data_fechamento")
    private LocalDateTime dataFechamento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "empresa_id", nullable = false)
    private EmpresaEntity empresa;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private UsuarioEntity usuarioAbertura;

    @OneToMany(mappedBy = "chamado", fetch = FetchType.LAZY)
    @Builder.Default
    private List<Comentario> comentarios = new java.util.ArrayList<>();
}