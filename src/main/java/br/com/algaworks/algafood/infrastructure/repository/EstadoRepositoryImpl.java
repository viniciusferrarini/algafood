package br.com.algaworks.algafood.infrastructure.repository;

import br.com.algaworks.algafood.domain.model.Estado;
import br.com.algaworks.algafood.domain.repository.EstadoRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Component
public class EstadoRepositoryImpl implements EstadoRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Estado> listar() {
        return entityManager.createQuery("from Estado", Estado.class)
                .getResultList();
    }

    @Override
    public Estado buscar(Long id) {
        return entityManager.find(Estado.class, id);
    }

    @Override
    public Estado salvar(Estado estado) {
        return entityManager.merge(estado);
    }

    @Override
    @Transactional
    public void remover(Long id) {
        Estado estado = buscar(id);

        if (estado == null) {
            throw new EmptyResultDataAccessException(1);
        }

        entityManager.remove(estado);
    }
}
