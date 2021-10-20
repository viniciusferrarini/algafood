package br.com.algaworks.algafood.domain.service;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import br.com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import br.com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import br.com.algaworks.algafood.domain.model.Estado;
import br.com.algaworks.algafood.domain.repository.EstadoRepository;

@Service
public class CadastroEstadoService {

    public static final String ESTADO_NAO_ENCONTRADO = "Estado de código %d não encontrado";
    public static final String ESTADO_EM_USO = "Estado de código %d não pode ser removido, pois esta em uso";
    private final EstadoRepository estadoRepository;

    public CadastroEstadoService(EstadoRepository estadoRepository) {
        this.estadoRepository = estadoRepository;
    }

    public Estado buscarOuFalhar(Long id) {
        return estadoRepository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException(String.format(ESTADO_NAO_ENCONTRADO, id)));
    }

    public Estado salvar(Estado estado) {
        return estadoRepository.save(estado);
    }

    public void excluir(Long id) {
        try {
            estadoRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new EntidadeNaoEncontradaException(String.format(ESTADO_NAO_ENCONTRADO, id));
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(String.format(ESTADO_EM_USO, id));
        }
    }
}
