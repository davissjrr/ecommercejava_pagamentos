package br.com.ecommerce.pagamento.dao;

import br.com.ecommerce.pagamento.model.Pagamento;

import java.util.List;

public interface PagamentoDAO {
    String inserir(Pagamento pagamento);
    List<Pagamento> listarTodos();
    void atualizarStatus(String id, String status);
}