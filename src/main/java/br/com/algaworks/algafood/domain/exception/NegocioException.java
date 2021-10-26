package br.com.algaworks.algafood.domain.exception;

public class NegocioException extends RuntimeException {

    private static final long serialVersionUID = 2086784792471932447L;

    public NegocioException(String mensagem) {
        super(mensagem);
    }

    public NegocioException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }

}
