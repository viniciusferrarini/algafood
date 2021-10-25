package br.com.algaworks.algafood.domain.exception;

public class EstadoEmUsoException extends EntidadeEmUsoException {

    private static final long serialVersionUID = -387146408835462919L;

    public EstadoEmUsoException(String mensagem) {
        super(mensagem);
    }

    public EstadoEmUsoException(Long id) {
        this(String.format("Estado de código %d não pode ser removido, pois esta em uso", id));
    }

}
