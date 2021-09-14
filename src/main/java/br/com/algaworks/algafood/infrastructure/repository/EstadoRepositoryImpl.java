package br.com.algaworks.algafood.infrastructure.repository;

import br.com.algaworks.algafood.domain.model.Estado;
import br.com.algaworks.algafood.domain.repository.EstadoRepository;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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


}
