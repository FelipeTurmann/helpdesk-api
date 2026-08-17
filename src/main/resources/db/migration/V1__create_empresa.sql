CREATE TABLE empresa (
                         id              BIGSERIAL PRIMARY KEY,
                         nome            VARCHAR(150) NOT NULL,
                         cnpj            VARCHAR(14) NOT NULL,
                         telefone        VARCHAR(20),
                         email           VARCHAR(150),
                         ativo           BOOLEAN NOT NULL DEFAULT TRUE,
                         data_cadastro   TIMESTAMP NOT NULL DEFAULT NOW(),
                         CONSTRAINT uk_empresa_cnpj UNIQUE (cnpj)
);