CREATE TABLE comentario (
                            id                  BIGSERIAL PRIMARY KEY,
                            texto               TEXT NOT NULL,
                            usuario_id          BIGINT NOT NULL,
                            chamado_id          BIGINT NOT NULL,
                            data_comentario     TIMESTAMP NOT NULL DEFAULT NOW(),
                            CONSTRAINT fk_comentario_usuario FOREIGN KEY (usuario_id) REFERENCES usuario (id),
                            CONSTRAINT fk_comentario_chamado FOREIGN KEY (chamado_id) REFERENCES chamado (id)
);

CREATE INDEX idx_comentario_chamado ON comentario (chamado_id);