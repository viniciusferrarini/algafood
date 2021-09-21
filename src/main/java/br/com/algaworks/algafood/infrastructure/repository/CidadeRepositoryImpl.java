package br.com.algaworks.algafood.infrastructure.repository;

import br.com.algaworks.algafood.domain.model.Cidade;
import br.com.algaworks.algafood.domain.repository.CidadeRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Component
public class CidadeRepositoryImpl implements CidadeRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Cidade> listar() {
        return entityManager.createQuery("from Cidade", Cidade.class)
                .getResultList();
    }

    @Override
    public Cidade buscar(Long id) {
        return entityManager.find(Cidade.class, id);
    }

    @Override
    @Transactional
    public Cidade salvar(Cidade cidade) {
        return entityManager.merge(cidade);
    }

    @Override
    @Transactional
    public void remover(Long id) {
        Cidade cidade = buscar(id);

        if (cidade == null) {
            throw new EmptyResultDataAccessException(1);
        }

        entityManager.remove(cidade);
    }
}
