package com.helpdesk_api.empresa.entity;

import com.helpdesk_api.entity.Chamado;
import com.helpdesk_api.entity.Usuario;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "empresa")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"usuarios", "chamados"})
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class EmpresaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "cnpj", nullable = false, unique = true, length = 14)
    private String cnpj;

    @Column(name = "telefone")
    private String telefone;

    @Column(name = "email")
    private String email;

    @Builder.Default
    @Column(nullable = false)
    private Boolean ativo = true;

    @CreationTimestamp
    @Column(name = "data_cadastro", nullable = false, updatable = false)
    private LocalDateTime dataCadastro;

    @OneToMany(mappedBy = "empresa", fetch = FetchType.LAZY)
    @Builder.Default
    private List<Usuario> usuarios = new java.util.ArrayList<>();

    @OneToMany(mappedBy = "empresa", fetch = FetchType.LAZY)
    @Builder.Default
    private List<Chamado> chamados = new java.util.ArrayList<>();
}