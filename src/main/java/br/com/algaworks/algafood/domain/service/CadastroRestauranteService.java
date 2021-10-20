package br.com.algaworks.algafood.domain.service;

import org.springframework.stereotype.Service;

import br.com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import br.com.algaworks.algafood.domain.model.Cozinha;
import br.com.algaworks.algafood.domain.model.Restaurante;
import br.com.algaworks.algafood.domain.repository.RestauranteRepository;

@Service
public class CadastroRestauranteService {

    public static final String RESTAURANTE_NAO_ENCONTRADO = "Restaurante de código %d não encontrado";

    private final RestauranteRepository restauranteRepository;
    private final CadastroCozinhaService cadastroCozinha;

    public CadastroRestauranteService(RestauranteRepository restauranteRepository, CadastroCozinhaService cadastroCozinha) {
        this.restauranteRepository = restauranteRepository;
        this.cadastroCozinha = cadastroCozinha;
    }

    public Restaurante buscarOuFalhar(Long id) {
        return restauranteRepository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException(String.format(RESTAURANTE_NAO_ENCONTRADO, id)));
    }

    public Restaurante salvar(Restaurante restaurante) {
        Long cozinhaId = restaurante.getCozinha().getId();
        Cozinha cozinha = cadastroCozinha.buscarOuFalhar(cozinhaId);
        restaurante.setCozinha(cozinha);
        return restauranteRepository.save(restaurante);
    }

}
