package br.com.ecommerce.pagamento.main;

import br.com.ecommerce.pagamento.dao.PagamentoDAO;
import br.com.ecommerce.pagamento.dao.PagamentoDAOImpl;
import br.com.ecommerce.pagamento.exception.PagamentoException;
import br.com.ecommerce.pagamento.model.Cartao;
import br.com.ecommerce.pagamento.model.Pagamento;

import java.math.BigDecimal;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        try {
            PagamentoDAO pagamentoDAO = new PagamentoDAOImpl();

            // Demonstração da Entidade Principal (Inserção)
            Pagamento novoPagamento = new Pagamento(null, "Cartão de Crédito", new BigDecimal("150.00"), "APROVADO");
            String uuid = pagamentoDAO.inserir(novoPagamento);
            System.out.println("Pagamento inserido com UUID: " + uuid);

            // Demonstração da Segunda Entidade
            System.out.println("\n--- Demonstração da Entidade Cartão ---");
            Cartao cartaoVinculado = new Cartao("C-001", "****.****.****.1234", "ALUNO CHECKPOINT");
            System.out.println("Cartão utilizado: " + cartaoVinculado.getNumeroMascarado() +
                    " | Titular: " + cartaoVinculado.getTitular());

            // Listagem
            List<Pagamento> pagamentos = pagamentoDAO.listarTodos();
            System.out.println("\n--- Lista de Pagamentos ---");
            for (Pagamento p : pagamentos) {
                System.out.println("ID: " + p.getId() +
                        " | Método: " + p.getMetodo() +
                        " | Valor: R$" + p.getValor() +
                        " | Status: " + p.getStatus());
            }

            // Atualização
            if (uuid != null) {
                pagamentoDAO.atualizarStatus(uuid, "CONCLUIDO");
                System.out.println("\nStatus atualizado para o registo UUID: " + uuid);
            }

            // Listagem
            System.out.println("\n--- Lista de Pagamentos Após Atualização ---");
            pagamentos = pagamentoDAO.listarTodos();
            for (Pagamento p : pagamentos) {
                System.out.println("ID: " + p.getId() +
                        " | Método: " + p.getMetodo() +
                        " | Valor: R$" + p.getValor() +
                        " | Status: " + p.getStatus());
            }

        } catch (PagamentoException e) {
            System.err.println("Erro na operação de pagamento: " + e.getMessage());
            if (e.getCause() != null) {
                System.err.println("Causa: " + e.getCause().getMessage());
            }
        }
    }
}