package br.com.algaworks.algafood.domain.service;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import br.com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import br.com.algaworks.algafood.domain.model.Cidade;
import br.com.algaworks.algafood.domain.model.Estado;
import br.com.algaworks.algafood.domain.repository.CidadeRepository;

@Service
public class CadastroCidadeService {

    public static final String CIDADE_NAO_ENCONTRADA = "Cidade de código %d não encontrada";
    private final CadastroEstadoService cadastroEstado;
    private final CidadeRepository cidadeRepository;

    public CadastroCidadeService(CadastroEstadoService cadastroEstado, CidadeRepository cidadeRepository) {
        this.cadastroEstado = cadastroEstado;
        this.cidadeRepository = cidadeRepository;
    }

    public Cidade buscarOuFalhar(Long id) {
        return cidadeRepository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException(String.format(CIDADE_NAO_ENCONTRADA, id)));
    }

    public Cidade salvar(Cidade cidade) {
        Long idEstado = cidade.getEstado().getId();
        Estado estado = cadastroEstado.buscarOuFalhar(idEstado);
        cidade.setEstado(estado);
        return cidadeRepository.save(cidade);
    }

    public void excluir(Long id) {
        try {
            cidadeRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new EntidadeNaoEncontradaException(String.format(CIDADE_NAO_ENCONTRADA, id));
        }
    }

}
