CREATE TABLE usuario (
                         id              BIGSERIAL PRIMARY KEY,
                         nome            VARCHAR(150) NOT NULL,
                         email           VARCHAR(150) NOT NULL,
                         senha           VARCHAR(255) NOT NULL,
                         cargo           VARCHAR(20) NOT NULL,
                         empresa_id      BIGINT,
                         ativo           BOOLEAN NOT NULL DEFAULT TRUE,
                         data_cadastro   TIMESTAMP NOT NULL DEFAULT NOW(),
                         CONSTRAINT uk_usuario_email UNIQUE (email),
                         CONSTRAINT fk_usuario_empresa FOREIGN KEY (empresa_id) REFERENCES empresa (id),
                         CONSTRAINT ck_usuario_cargo CHECK (cargo IN ('ADMIN', 'CLIENTE'))
);

CREATE INDEX idx_usuario_empresa ON usuario (empresa_id);