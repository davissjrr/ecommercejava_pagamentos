# Checkpoint 1 - Módulo de Pagamentos

## Descrição do Projeto
Este projeto consiste na entrega do módulo de **Pagamentos** para o domínio do produto `www.ecommerce.com.br`. O sistema foi desenvolvido em Java e implementa operações de persistência de dados em um banco relacional, focando em boas práticas e estruturação de código.

## Composição do Grupo
* Aluno 1: Witalon Antonio Rodrigues - RM559023  
* Aluno 2: Davis Cardoso de Lima Junior - RM560723
* Aluno 3: Luigi Thiengo Pires - RM560755

## Requisitos Atendidos
* **Entidades:** O projeto contém as entidades `Pagamento` e `Cartao`.
* **Funcionalidades:** Inserção de novo pagamento, listagem de todos os registros e atualização de status de um pagamento específico.
* **Tratamento de Erros:** Implementação da exceção customizada `PagamentoException`.
* **Integração com Banco de Dados:** Persistência de dados utilizando a API JDBC para Oracle.
* **Padrões de Projeto:** Aplicação obrigatória dos padrões **DAO** (Data Access Object) para o isolamento das operações de banco e **Singleton** para o gerenciamento de uma instância única de conexão.

## Pré-requisitos
Para rodar este projeto na sua máquina, você vai precisar de:
* Java Development Kit (JDK) 8 ou superior instalado.
* Banco de Dados Oracle (Express Edition - XE recomendado) rodando localmente.
* Driver JDBC do Oracle (`ojdbc8.jar` ou similar) adicionado às bibliotecas (classpath) do seu projeto na IDE.

## Configuração do Banco de Dados
Antes de rodar a aplicação pela primeira vez, é obrigatório criar a tabela no banco de dados. Acesse o seu banco Oracle e execute o script SQL abaixo:

```sql
CREATE TABLE T_PAGAMENTO (
    id VARCHAR2(36) PRIMARY KEY,
    metodo VARCHAR2(50) NOT NULL,
    valor NUMBER(10,2) NOT NULL,
    status VARCHAR2(20) NOT NULL
);
