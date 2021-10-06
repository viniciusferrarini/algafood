package br.com.algaworks.algafood.domain.repository;

import br.com.algaworks.algafood.domain.model.Restaurante;

import java.math.BigDecimal;
import java.util.List;

public interface RestauranteRepositoryQueries {

    List<Restaurante> consultarPorNomeETaxa(String nome,
                                            BigDecimal taxaFreteInicial,
                                            BigDecimal taxaFreteFinal);

    List<Restaurante> consultarPorNomeETaxaCriteria(String nome,
                                            BigDecimal taxaFreteInicial,
                                            BigDecimal taxaFreteFinal);

    List<Restaurante> findComFreteGratis(String nome);

}
