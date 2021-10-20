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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import br.com.algaworks.algafood.domain.model.Estado;
import br.com.algaworks.algafood.domain.repository.EstadoRepository;
import br.com.algaworks.algafood.domain.service.CadastroEstadoService;

@RestController
@RequestMapping("/estados")
public class EstadoController {

    private final EstadoRepository estadoRepository;

    private final CadastroEstadoService cadastroEstado;

    public EstadoController(EstadoRepository estadoRepository, CadastroEstadoService cadastroEstado) {
        this.estadoRepository = estadoRepository;
        this.cadastroEstado = cadastroEstado;
    }

    @GetMapping
    public List<Estado> listar() {
        return estadoRepository.findAll();
    }

    @GetMapping("/{id}")
    public Estado buscar(@PathVariable Long id) {
        return cadastroEstado.buscarOuFalhar(id);
    }

    @PostMapping
    public ResponseEntity<Object> adicionar(@RequestBody Estado estado) {
        try {
            estado = cadastroEstado.salvar(estado);
            return ResponseEntity.status(HttpStatus.CREATED).body(estado);
        } catch (EntidadeNaoEncontradaException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public Estado atualizar(@PathVariable Long id, @RequestBody Estado estado) {
        Estado estadoAtual = cadastroEstado.buscarOuFalhar(id);
        BeanUtils.copyProperties(estado, estadoAtual, "id");
        return estadoRepository.save(estadoAtual);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        cadastroEstado.excluir(id);
    }

}
