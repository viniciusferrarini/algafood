package br.com.algaworks.algafood.domain.repository;

import br.com.algaworks.algafood.domain.model.FormaPagamento;
import org.springframework.stereotype.Repository;

@Repository
public interface FormaPagamentoRepository extends CustomJpaRepository<FormaPagamento, Long> { }
