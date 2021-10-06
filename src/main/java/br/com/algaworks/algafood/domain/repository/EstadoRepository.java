package br.com.algaworks.algafood.domain.repository;

import br.com.algaworks.algafood.domain.model.Estado;
import org.springframework.stereotype.Repository;

@Repository
public interface EstadoRepository extends CustomJpaRepository<Estado, Long> { }
