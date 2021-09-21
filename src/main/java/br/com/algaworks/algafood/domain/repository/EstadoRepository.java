package br.com.algaworks.algafood.domain.repository;

import br.com.algaworks.algafood.domain.model.Estado;

import java.util.List;

public interface EstadoRepository {

    List<Estado> listar();

    Estado buscar(Long idEstado);

    Estado salvar(Estado estado);

    void remover(Long id);

}
