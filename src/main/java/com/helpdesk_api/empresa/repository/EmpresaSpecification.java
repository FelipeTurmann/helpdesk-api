package com.helpdesk_api.empresa.repository;

import com.helpdesk_api.empresa.entity.EmpresaEntity;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

public class EmpresaSpecification {

    private EmpresaSpecification() {
    }

    public static Specification<EmpresaEntity> comNome(String nome) {
        if (!StringUtils.hasText(nome)) {
            return Specification.unrestricted();
        }
        return (root, query, cb) ->
                cb.like(cb.lower(root.get("nome")), "%" + nome.toLowerCase() + "%");
    }

    public static Specification<EmpresaEntity> comCnpj(String cnpj) {
        if (!StringUtils.hasText(cnpj)) {
            return Specification.unrestricted();
        }
        return (root, query, cb) -> cb.equal(root.get("cnpj"), cnpj);
    }

    public static Specification<EmpresaEntity> comTelefone(String telefone) {
        if (!StringUtils.hasText(telefone)) {
            return Specification.unrestricted();
        }
        return (root, query, cb) -> cb.equal(root.get("telefone"), telefone);
    }

    public static Specification<EmpresaEntity> comEmail(String email) {
        if (!StringUtils.hasText(email)) {
            return Specification.unrestricted();
        }
        return (root, query, cb) ->
                cb.like(cb.lower(root.get("email")), "%" + email.toLowerCase() + "%");
    }
}