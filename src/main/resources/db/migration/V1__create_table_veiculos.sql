CREATE TABLE veiculos (
                          id BIGSERIAL PRIMARY KEY,
                          marca VARCHAR(100) NOT NULL,
                          modelo VARCHAR(150) NOT NULL,
                          ano INTEGER NOT NULL,
                          cor VARCHAR(50) NOT NULL,
                          preco_em_dolar NUMERIC(15,2) NOT NULL,
                          placa VARCHAR(20) NOT NULL UNIQUE,
                          ativo BOOLEAN NOT NULL DEFAULT TRUE,
                          created_at TIMESTAMP WITHOUT TIME ZONE DEFAULT NOW(),
                          updated_at TIMESTAMP WITHOUT TIME ZONE DEFAULT NOW()
);

-- Índices úteis para filtros
CREATE INDEX idx_veiculos_marca ON veiculos (marca);
CREATE INDEX idx_veiculos_ano ON veiculos (ano);
CREATE INDEX idx_veiculos_cor ON veiculos (cor);
CREATE INDEX idx_veiculos_preco ON veiculos (preco_em_dolar);