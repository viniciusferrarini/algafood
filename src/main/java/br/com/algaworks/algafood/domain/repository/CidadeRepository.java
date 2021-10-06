package br.com.algaworks.algafood.domain.repository;

import br.com.algaworks.algafood.domain.model.Cidade;
import org.springframework.stereotype.Repository;

@Repository
public interface CidadeRepository extends CustomJpaRepository<Cidade, Long> { }
