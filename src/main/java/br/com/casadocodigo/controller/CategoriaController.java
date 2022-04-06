package br.com.casadocodigo.controller;

import br.com.casadocodigo.dto.CategoriaDTO;
import br.com.casadocodigo.service.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping(value = "/categoria")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    @GetMapping(value = "/buscar/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> buscarCategoria(@PathVariable("id") UUID id) {
        CategoriaDTO dto = categoriaService.buscarCategoriaPorId(id);
        if(dto.getId() == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @PostMapping(value = "/cadastrar", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> cadastrarCategoria(@RequestBody @Valid CategoriaDTO dto) {
        CategoriaDTO categoria = categoriaService.criarCategoria(dto);
        if(categoria == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(categoria, HttpStatus.OK);
    }
}
