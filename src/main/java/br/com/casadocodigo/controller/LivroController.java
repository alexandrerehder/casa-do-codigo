package br.com.casadocodigo.controller;

import br.com.casadocodigo.dto.LivroDTO;
import br.com.casadocodigo.dto.LivroDetalhesDTO;
import br.com.casadocodigo.nativeQueryProjection.LivroDetalhes;
import br.com.casadocodigo.service.LivroService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping(value = "/livros")
public class LivroController {

    @Autowired
    private LivroService livroService;

    Gson gson = new Gson();

    @GetMapping(value = "/listarTodos", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> listaTodosLivros() {
        return new ResponseEntity<>(livroService.listarTodos(), HttpStatus.OK);
    }

    @GetMapping(value = "/buscar/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> buscarLivro(@PathVariable("id") UUID id) {
        LivroDTO dto = livroService.buscarLivroPorId(id);
        if(dto == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @GetMapping(value = "/buscar/detalhes/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> buscarDetalhesLivro(@PathVariable("id") UUID id) {
        LivroDetalhesDTO dto = livroService.buscarDetalhesLivroPorId(id);
        if(dto == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @PostMapping(value = "/cadastrar", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> cadastrarLivro(@RequestBody @Valid LivroDTO dto) {
        LivroDTO livro = livroService.criarLivro(dto);
        if(livro == null) {
            return new ResponseEntity<>(gson.toJson("Problemas de validação"), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(livro, HttpStatus.OK);
    }
}
