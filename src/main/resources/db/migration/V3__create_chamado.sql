CREATE TABLE chamado (
                         id                  BIGSERIAL PRIMARY KEY,
                         titulo              VARCHAR(200) NOT NULL,
                         descricao           TEXT NOT NULL,
                         status              VARCHAR(25) NOT NULL,
                         prioridade          VARCHAR(10) NOT NULL,
                         categoria           VARCHAR(100) NOT NULL,
                         empresa_id          BIGINT NOT NULL,
                         usuario_id          BIGINT NOT NULL,
                         data_abertura       TIMESTAMP NOT NULL DEFAULT NOW(),
                         data_atualizacao    TIMESTAMP NOT NULL DEFAULT NOW(),
                         data_fechamento     TIMESTAMP,
                         CONSTRAINT fk_chamado_empresa FOREIGN KEY (empresa_id) REFERENCES empresa (id),
                         CONSTRAINT fk_chamado_usuario FOREIGN KEY (usuario_id) REFERENCES usuario (id),
                         CONSTRAINT ck_chamado_status CHECK (status IN ('ABERTO', 'EM_ATENDIMENTO', 'AGUARDANDO_CLIENTE', 'RESOLVIDO', 'FECHADO')),
                         CONSTRAINT ck_chamado_prioridade CHECK (prioridade IN ('BAIXA', 'MEDIA', 'ALTA', 'CRITICA'))
);

CREATE INDEX idx_chamado_empresa ON chamado (empresa_id);
CREATE INDEX idx_chamado_usuario ON chamado (usuario_id);
CREATE INDEX idx_chamado_status ON chamado (status);