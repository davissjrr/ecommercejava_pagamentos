package br.com.ecommerce.pagamento.config;

import br.com.ecommerce.pagamento.exception.PagamentoException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static Connection connection;

    private DatabaseConnection() {}

    public static Connection getConnection() {
        if (connection == null) {
            try {
                // Configuração para banco Oracle
                Class.forName("oracle.jdbc.OracleDriver");
                connection = DriverManager.getConnection(
                        "jdbc:oracle:thin:@localhost:1521:xe", "SEU_USUARIO", "SUA_SENHA");
            } catch (ClassNotFoundException | SQLException e) {
                throw new PagamentoException("Erro ao conectar no banco de dados: " + e.getMessage());
            }
        }
        return connection;
    }
}