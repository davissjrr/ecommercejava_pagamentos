package br.com.ecommerce.pagamento.dao;

import java.util.List;

public interface PagamentoDAO {
    String inserir(Pagamento pagamento);
    List<Pagamento> listarTodos();
    void atualizarStatus(String id, String status);
}