CREATE TABLE produto (
    id_produto SERIAL PRIMARY KEY,
    nm_produto VARCHAR(255) NOT NULL,
    qt_produto INT NOT NULL
);

CREATE TABLE transacao (
    ID SERIAL PRIMARY KEY,
    TIPOPAG VARCHAR(50) NOT NULL,
    VALOR DECIMAL(10,2) NOT NULL,
    DATA VARCHAR(50) NOT null
);