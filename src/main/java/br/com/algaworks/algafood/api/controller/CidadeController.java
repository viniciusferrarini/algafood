package br.com.algaworks.algafood.api.controller;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import br.com.algaworks.algafood.domain.model.Cidade;
import br.com.algaworks.algafood.domain.model.Cozinha;
import br.com.algaworks.algafood.domain.repository.CidadeRepository;
import br.com.algaworks.algafood.domain.service.CadastroCidadeService;

@RestController
@RequestMapping("/cidades")
public class CidadeController {

    private final CidadeRepository cidadeRepository;

    private final CadastroCidadeService cadastroCidade;

    public CidadeController(CidadeRepository cidadeRepository, CadastroCidadeService cadastroCidade) {
        this.cidadeRepository = cidadeRepository;
        this.cadastroCidade = cadastroCidade;
    }

    @GetMapping
    public List<Cidade> listar() {
        return cidadeRepository.findAll();
    }

    @GetMapping("/{id}")
    public Cidade buscar(@PathVariable Long id) {
        return cadastroCidade.buscarOuFalhar(id);
    }

    @PostMapping
    public ResponseEntity<Object> adicionar(@RequestBody Cidade cidade) {
        try {
            cidade = cadastroCidade.salvar(cidade);
            return ResponseEntity.status(HttpStatus.CREATED).body(cidade);
        } catch (EntidadeNaoEncontradaException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public Cidade atualizar(@PathVariable Long id, @RequestBody Cidade cidade) {
        Cidade cidadeAtual =  cadastroCidade.buscarOuFalhar(id);
        BeanUtils.copyProperties(cidade, cidadeAtual, "id");
        return cadastroCidade.salvar(cidadeAtual);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Cozinha> delete(@PathVariable Long id) {
        try {
            cadastroCidade.excluir(id);
            return ResponseEntity
                    .noContent()
                    .build();
        } catch (EntidadeNaoEncontradaException e) {
            return ResponseEntity
                    .notFound()
                    .build();
        }
    }

}
