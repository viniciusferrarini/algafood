package br.com.algaworks.algafood.api.controller;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import br.com.algaworks.algafood.domain.model.Restaurante;
import br.com.algaworks.algafood.domain.repository.RestauranteRepository;
import br.com.algaworks.algafood.domain.service.CadastroRestauranteService;

@RestController
@RequestMapping("/restaurantes")
public class RestauranteController {

    private final RestauranteRepository restauranteRepository;
    private final CadastroRestauranteService cadastroRestaurante;

    public RestauranteController(RestauranteRepository restauranteRepository, CadastroRestauranteService cadastroRestaurante) {
        this.restauranteRepository = restauranteRepository;
        this.cadastroRestaurante = cadastroRestaurante;
    }

    @GetMapping
    public List<Restaurante> listar() {
        return restauranteRepository.findAll();
    }

    @GetMapping("/{id}")
    public Restaurante buscar(@PathVariable Long id) {
        return cadastroRestaurante.buscarOuFalhar(id);
    }

    @PostMapping
    public ResponseEntity<Object> adicionar(@RequestBody Restaurante restaurante) {
        try {
            restaurante = cadastroRestaurante.salvar(restaurante);
            return ResponseEntity.status(HttpStatus.CREATED).body(restaurante);
        } catch (EntidadeNaoEncontradaException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public Restaurante atualizar(@PathVariable Long id, @RequestBody Restaurante restaurante) {
        Restaurante restauranteAtual = cadastroRestaurante.buscarOuFalhar(id);
        BeanUtils.copyProperties(restaurante, restauranteAtual,
                   "id", "formasPagamento", "endereco", "dataCadastro", "produtos");
        return cadastroRestaurante.salvar(restauranteAtual);
    }

    @PatchMapping("/{id}")
    public Restaurante atualizarParcial(@PathVariable Long id,
                                        @RequestBody Map<String, Object> campos) {
        Restaurante restaurante = cadastroRestaurante.buscarOuFalhar(id);
        merge(campos, restaurante);
        return atualizar(id, restaurante);
    }

    @GetMapping("/taxa-inicial/{taxaInicial}/taxa-final/{taxaFinal}")
    public List<Restaurante> buscaPorTaxaEntre(@PathVariable BigDecimal taxaInicial, @PathVariable BigDecimal taxaFinal) {
        return restauranteRepository.findByTaxaFreteBetween(taxaInicial, taxaFinal);
    }

    @GetMapping("/frete-gratis/nome/{nome}")
    public List<Restaurante> buscaRestaurantePorNomeComFreteGratis(@PathVariable String nome) {
        return restauranteRepository.findComFreteGratis(nome);
    }

    @GetMapping("/primeiro")
    public Optional<Restaurante> buscarPrimeiro() {
        return restauranteRepository.buscarPrimeiro();
    }

    public void merge(Map<String, Object> camposOrigem, Restaurante restauranteDestino) {

        ObjectMapper objectMapper = new ObjectMapper();
        Restaurante restauranteOrigem = objectMapper.convertValue(camposOrigem, Restaurante.class);

        camposOrigem.forEach((key, value) -> {
            Field field = ReflectionUtils.findField(Restaurante.class, key);
            if (field != null) {
                field.setAccessible(true);
                Object novoValor = ReflectionUtils.getField(field, restauranteOrigem);
                ReflectionUtils.setField(field, restauranteDestino, novoValor);
            }
        });
    }

}
