package br.com.algaworks.algafood.domain.service;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import br.com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import br.com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import br.com.algaworks.algafood.domain.model.Cozinha;
import br.com.algaworks.algafood.domain.repository.CozinhaRepository;

@Service
public class CadastroCozinhaService {

    public static final String COZINHA_NAO_ENCONTRADA = "Cozinha de código %d não encontrada";
    public static final String COZINHA_EM_USO = "Cozinha de código %d não pode ser removida, pois esta em uso";

    private final CozinhaRepository cozinhaRepository;

    public CadastroCozinhaService(CozinhaRepository cozinhaRepository) {
        this.cozinhaRepository = cozinhaRepository;
    }

    public Cozinha buscarOuFalhar(Long id) {
        return cozinhaRepository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException(String.format(COZINHA_NAO_ENCONTRADA, id)));
    }

    public Cozinha salvar(Cozinha cozinha) {
        return cozinhaRepository.save(cozinha);
    }

    public void excluir(Long id) {
        try {
            cozinhaRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new EntidadeNaoEncontradaException(String.format(COZINHA_NAO_ENCONTRADA, id));
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(String.format(COZINHA_EM_USO, id));
        }
    }

}
