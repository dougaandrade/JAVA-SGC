CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE PRODUTO (
    id_produto UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    nm_produto VARCHAR(255) NOT NULL,
    qt_produto INT NOT NULL,
    valor_produto DECIMAL(10,2),
    data_cadastro TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    );

CREATE TABLE PRODUTOS_TOTAL (
    id_produto SERIAL PRIMARY KEY,
    nm_produto VARCHAR(255) NOT NULL,
    qt_produto INT NOT NULL,
    valor_produto DECIMAL(10,2),
    data_cadastro TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);


