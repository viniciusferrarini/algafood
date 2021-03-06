package br.com.algaworks.algafood.domain.repository;

import br.com.algaworks.algafood.domain.model.Cozinha;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CozinhaRepository extends CustomJpaRepository<Cozinha, Long> {

    List<Cozinha> findByNomeContaining(String nome);

    // List<Cozinha> buscarPorNome(String nome);

}
