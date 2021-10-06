package br.com.algaworks.algafood.domain.repository;

import br.com.algaworks.algafood.domain.model.Permissao;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissaoRepository extends CustomJpaRepository<Permissao, Long> { }
