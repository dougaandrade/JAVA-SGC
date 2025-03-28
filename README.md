# 📌 Sistema de Gerenciamento de Caixa (SGC)

Este projeto é um sistema automatizado para registrar os lucros diários de uma empresa, substituindo processos manuais que podem resultar em imprecisões contábeis. Desenvolvido em **Java** e utilizando **PostgreSQL** como banco de dados, o SGC visa melhorar a eficiência operacional e garantir a precisão dos registros financeiros.

## 🚀 Tecnologias Utilizadas

- ✅ **Java** – Linguagem de programação principal.
- ✅ **PostgreSQL** – Banco de dados para armazenamento das transações.
- ✅ **JDBC** – Conector para integração entre Java e PostgreSQL.

## 🔥 Funcionalidades

- 🔹 Registro diário dos lucros.
- 🔹 Geração de relatórios financeiros.
- 🔹 Interface amigável para facilitar o uso.
- 🔹 Validação e segurança dos dados registrados.

## 📂 Como Executar o Projeto

### 📌 Pré-requisitos

Antes de começar, você precisará ter instalado em sua máquina:

- **JDK 17** ou superior.
- **PostgreSQL** (com o banco de dados configurado).
- **Maven**.
- **Git**.

### 🚀 Clonando e Executando

```bash
# Clone este repositório
git clone https://github.com/dougaandrade/JAVA-SGC.git

# Acesse a pasta do projeto
cd JAVA-SGC

# Compile o projeto
mvn clean install

# Execute a aplicação
java -jar target/sgc.jar


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
```
