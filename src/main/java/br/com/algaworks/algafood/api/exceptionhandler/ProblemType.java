package br.com.algaworks.algafood.api.exceptionhandler;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public enum ProblemType {

    ENTIDADE_NAO_ENCONTRADA("/entidade-nao-encontrada", "Entidade não encontrada", HttpStatus.NOT_FOUND),
    ENTIDADE_EM_USO("/entidade-em-uso", "Entidade em uso", HttpStatus.CONFLICT),
    NEGOCIO("/erro-de-negocio", "Erro de negócio", HttpStatus.BAD_REQUEST);

    private String title;
    private String uri;
    private HttpStatus status;

    ProblemType(String path, String title, HttpStatus status) {
        this.uri = "https://algafood.com.br" + path;
        this.title = title;
        this.status = status;
    }

}
