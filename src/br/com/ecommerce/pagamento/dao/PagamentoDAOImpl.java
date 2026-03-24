package br.com.ecommerce.pagamento.dao;

import br.com.ecommerce.pagamento.config.DatabaseConnection;
import br.com.ecommerce.pagamento.exception.PagamentoException;
import br.com.ecommerce.pagamento.model.Pagamento;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PagamentoDAOImpl implements PagamentoDAO {

    private Connection connection;

    public PagamentoDAOImpl() {
        this.connection = DatabaseConnection.getConnection();
        criarTabelaSeNaoExistir();
    }

    private void criarTabelaSeNaoExistir() {
        String sql = "CREATE TABLE T_PAGAMENTO (" +
                "id VARCHAR2(36) PRIMARY KEY, " +
                "metodo VARCHAR2(50) NOT NULL, " +
                "valor NUMBER(10,2) NOT NULL, " +
                "status VARCHAR2(20) NOT NULL" +
                ")";

        try (java.sql.Statement stmt = connection.createStatement()) {
            stmt.execute(sql);
            System.out.println("Tabela T_PAGAMENTO criada com sucesso no Oracle!");
        } catch (SQLException e) {
            // O erro 955 do Oracle significa "nome já usado por um objeto existente"
            if (e.getErrorCode() == 955) {
                System.out.println("A tabela T_PAGAMENTO já existe. Nenhuma ação necessária.");
            } else {
                System.err.println("Erro ao tentar criar a tabela: " + e.getMessage());
            }
        }
    }

    @Override
    public String inserir(Pagamento pagamento) {
        String uuid = UUID.randomUUID().toString();
        String sql = "INSERT INTO T_PAGAMENTO (id, metodo, valor, status) VALUES (?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, uuid);
            stmt.setString(2, pagamento.getMetodo());
            stmt.setBigDecimal(3, pagamento.getValor());
            stmt.setString(4, pagamento.getStatus());

            stmt.executeUpdate();
            pagamento.setId(uuid);
            return uuid;

        } catch (SQLException e) {
            throw new PagamentoException("Erro ao inserir pagamento no banco: " + e.getMessage());
        }
    }

    @Override
    public List<Pagamento> listarTodos() {
        List<Pagamento> pagamentos = new ArrayList<>();
        String sql = "SELECT * FROM T_PAGAMENTO";

        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Pagamento p = new Pagamento(
                        rs.getString("id"),
                        rs.getString("metodo"),
                        rs.getBigDecimal("valor"),
                        rs.getString("status")
                );
                pagamentos.add(p);
            }

        } catch (SQLException e) {
            throw new PagamentoException("Erro ao listar pagamentos: " + e.getMessage());
        }
        return pagamentos;
    }

    @Override
    public void atualizarStatus(String id, String status) {
        String sql = "UPDATE T_PAGAMENTO SET status = ? WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, status);
            stmt.setString(2, id);

            int linhasAfetadas = stmt.executeUpdate();
            if (linhasAfetadas == 0) {
                throw new PagamentoException("Nenhum pagamento encontrado com o ID informado.");
            }

        } catch (SQLException e) {
            throw new PagamentoException("Erro ao atualizar o status do pagamento: " + e.getMessage());
        }
    }
}