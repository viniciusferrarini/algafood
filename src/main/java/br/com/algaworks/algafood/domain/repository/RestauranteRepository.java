package br.com.algaworks.algafood.domain.repository;

import br.com.algaworks.algafood.domain.model.Restaurante;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface RestauranteRepository extends CustomJpaRepository<Restaurante, Long>,
        RestauranteRepositoryQueries,
        JpaSpecificationExecutor<Restaurante> {

    @Query("select distinct r from Restaurante r join fetch r.cozinha left join fetch r.formasPagamento")
    List<Restaurante> findAll();

    List<Restaurante> findByTaxaFreteBetween(BigDecimal taxaInicial, BigDecimal taxaFinal);

    List<Restaurante> consultarPorNome(String nome, @Param("id") Long cozinhaId);

}
