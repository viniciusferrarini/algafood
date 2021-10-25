package br.com.algaworks.algafood.domain.exception;

public class CozinhaEmUsoException extends EntidadeEmUsoException {

    private static final long serialVersionUID = 8033433638720150082L;

    public CozinhaEmUsoException(String mensagem) {
        super(mensagem);
    }

    public CozinhaEmUsoException(Long id) {
        this(String.format("Cozinha de código %d não pode ser removida, pois esta em uso", id));
    }

}
