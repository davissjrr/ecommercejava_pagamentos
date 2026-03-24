package br.com.ecommerce;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.math.BigDecimal;
import java.util.UUID;

public class Main {

    private static final String URL = "jdbc:oracle:thin:@oracle.fiap.com.br:1521:ORCL";
    // Usuário e senha
    private static final String USER = "RM559023";
    private static final String PASSWORD = "220905";

    public static void main(String[] args) {
        // CRUD
        String meuUuid = inserirPagamento("Cartão de Crédito", new BigDecimal("150.00"), "APROVADO");

        listarPagamentos();

        if (meuUuid != null) {
            atualizarStatusPagamento(meuUuid, "CONCLUIDO");
        }
    }

    public static String inserirPagamento(String metodo, BigDecimal valor, String status) {
        String sql = "INSERT INTO PAGAMENTOS (id, metodo, valor, status) VALUES (?, ?, ?, ?)";
        String novoUuid = UUID.randomUUID().toString(); // Gera o ID único

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, novoUuid);
            ps.setString(2, metodo);
            ps.setBigDecimal(3, valor);
            ps.setString(4, status);

            ps.executeUpdate();
            System.out.println("Pagamento inserido com UUID: " + novoUuid);
            return novoUuid;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void listarPagamentos() {
        String sql = "SELECT * FROM PAGAMENTOS";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            System.out.println("\n--- Lista de Pagamentos ---");
            while (rs.next()) {
                System.out.println("ID: " + rs.getString("id") +
                        " | Método: " + rs.getString("metodo") +
                        " | Valor: R$" + rs.getBigDecimal("valor") +
                        " | Status: " + rs.getString("status"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void atualizarStatusPagamento(String id, String novoStatus) {
        String sql = "UPDATE PAGAMENTOS SET status = ? WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, novoStatus);
            ps.setString(2, id);
            ps.executeUpdate();
            System.out.println("Status atualizado para o registro UUID: " + id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}