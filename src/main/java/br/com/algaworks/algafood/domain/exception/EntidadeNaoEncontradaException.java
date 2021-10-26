package br.com.algaworks.algafood.domain.exception;

public abstract class EntidadeNaoEncontradaException extends RuntimeException {

    private static final long serialVersionUID = 2086784792471932447L;

    EntidadeNaoEncontradaException(String mensagem) {
        super(mensagem);
    }

}
