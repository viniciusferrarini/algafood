package br.com.algaworks.algafood.api.exceptionhandler;

import java.util.Objects;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import br.com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import br.com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import br.com.algaworks.algafood.domain.exception.NegocioException;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(EntidadeNaoEncontradaException.class)
    public ResponseEntity<Object> handleEntidadeNaoEncontradaException(EntidadeNaoEncontradaException e, WebRequest webRequest){
        return createProblem(e, webRequest, ProblemType.ENTIDADE_NAO_ENCONTRADA);
    }

    @ExceptionHandler(NegocioException.class)
    public ResponseEntity<Object> handleNegocioException(NegocioException e, WebRequest webRequest) {
        return createProblem(e, webRequest, ProblemType.NEGOCIO);
    }

    @ExceptionHandler(EntidadeEmUsoException.class)
    public ResponseEntity<Object> handleEntidadeEmUsoException(EntidadeEmUsoException e, WebRequest webRequest) {
        return createProblem(e, webRequest, ProblemType.ENTIDADE_EM_USO);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {

        if (Objects.isNull(body)) {
            body = Problem.builder()
                    .status(status.value())
                    .detail(ex.getMessage())
                    .build();
        }
        else if (body instanceof String) {
            body = Problem.builder()
                    .status(status.value())
                    .detail((String) body)
                    .build();
        }

        return super.handleExceptionInternal(ex, body, headers, status, request);
    }

    private ResponseEntity<Object> createProblem(Exception e, WebRequest webRequest, ProblemType problemType) {
        Problem problem = createProblemBuilder(problemType.getStatus(), problemType, e.getMessage()).build();
        return handleExceptionInternal(e, problem, new HttpHeaders(), problemType.getStatus(), webRequest);
    }

    private Problem.ProblemBuilder createProblemBuilder(HttpStatus status, ProblemType problemType, String detail) {
        return Problem.builder()
                .status(status.value())
                .type(problemType.getUri())
                .title(problemType.getTitle())
                .detail(detail);
    }

}
