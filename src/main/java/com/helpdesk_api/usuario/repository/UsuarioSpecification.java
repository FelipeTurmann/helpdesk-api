package com.helpdesk_api.usuario.repository;

import com.helpdesk_api.enums.CargoEnum;
import com.helpdesk_api.usuario.entity.UsuarioEntity;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

public class UsuarioSpecification {

    private UsuarioSpecification() {
    }

    public static Specification<UsuarioEntity> comNome(String nome) {
        if (!StringUtils.hasText(nome)) {
            return Specification.unrestricted();
        }
        return (root, query, cb) ->
                cb.like(cb.lower(root.get("nome")), "%" + nome.toLowerCase() + "%");
    }

    public static Specification<UsuarioEntity> comEmail(String email) {
        if (!StringUtils.hasText(email)) {
            return Specification.unrestricted();
        }
        return (root, query, cb) ->
                cb.like(cb.lower(root.get("email")), "%" + email.toLowerCase() + "%");
    }

    public static Specification<UsuarioEntity> comCargo(CargoEnum cargo) {
        if (cargo == null) {
            return Specification.unrestricted();
        }
        return (root, query, cb) -> cb.equal(root.get("cargo"), cargo);
    }

    public static Specification<UsuarioEntity> comEmpresaId(Long empresaId) {
        if (empresaId == null) {
            return Specification.unrestricted();
        }
        return (root, query, cb) -> cb.equal(root.get("empresa").get("id"), empresaId);
    }

    public static Specification<UsuarioEntity> comAtivo(Boolean ativo) {
        if (ativo == null) {
            return Specification.unrestricted();
        }
        return (root, query, cb) -> cb.equal(root.get("ativo"), ativo);
    }
}
