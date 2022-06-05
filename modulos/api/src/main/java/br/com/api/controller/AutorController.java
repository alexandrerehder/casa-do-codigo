package br.com.casadocodigo.controller;

import br.com.casadocodigo.dto.AutorDTO;
import br.com.casadocodigo.service.AutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping(value = "/autor")
public class AutorController {

    @Autowired
    private AutorService autorService;

    @GetMapping(value = "/buscar/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> buscarAutor(@PathVariable("id") UUID id) {
        AutorDTO dto = autorService.buscarAutorPorId(id);
        if(dto.getId() == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @PostMapping(value = "/cadastrar", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> cadastrarAutor(@RequestBody @Valid AutorDTO dto) {
        AutorDTO autor = autorService.criarAutor(dto);
        if(autor == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(autor, HttpStatus.OK);
    }
}


