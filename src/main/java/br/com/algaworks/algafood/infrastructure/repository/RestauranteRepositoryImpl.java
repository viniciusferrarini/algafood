package br.com.algaworks.algafood.infrastructure.repository;

import br.com.algaworks.algafood.domain.model.Restaurante;
import br.com.algaworks.algafood.domain.repository.RestauranteRepository;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Component
public class RestauranteRepositoryImpl implements RestauranteRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Restaurante> listar() {
        return entityManager.createQuery("from Restaurante", Restaurante.class).getResultList();
    }

    @Override
    public Restaurante buscar(Long id) {
        return entityManager.find(Restaurante.class, id);
    }

    @Override
    @Transactional
    public Restaurante salvar(Restaurante restaurante) {
        return entityManager.merge(restaurante);
    }
}
