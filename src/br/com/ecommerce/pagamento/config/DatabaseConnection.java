package br.com.ecommerce.pagamento.config;

import br.com.ecommerce.pagamento.exception.PagamentoException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static Connection connection;

    private static final String URL = "jdbc:oracle:thin:@oracle.fiap.com.br:1521:ORCL";
    private static final String USER = "RM559023";
    private static final String PASSWORD = "220905";

    private DatabaseConnection() {}

    public static Connection getConnection() {
        if (connection == null) {
            try {
                // Carrega o driver do Oracle
                Class.forName("oracle.jdbc.OracleDriver");
                // Estabelece a conexão com as suas constantes
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
            } catch (ClassNotFoundException | SQLException e) {
                throw new PagamentoException("Erro ao conectar no banco de dados Oracle: " + e.getMessage());
            }
        }
        return connection;
    }
}