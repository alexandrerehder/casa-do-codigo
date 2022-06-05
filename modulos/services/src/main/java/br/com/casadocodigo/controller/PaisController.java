package br.com.casadocodigo.controller;

import br.com.commons.dto.PaisDTO;
import br.com.casadocodigo.service.PaisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping(value = "/pais")
public class PaisController {

    @Autowired
    private PaisService paisService;

    @GetMapping(value = "/listarTodos", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> listaTodosPaises() {
        return new ResponseEntity<>(paisService.listarTodos(), HttpStatus.OK);
    }

    @GetMapping(value = "/buscar/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> buscarPais(@PathVariable("id") UUID id) {
        PaisDTO dto = paisService.buscarPaisPorId(id);
        if(dto.getId() == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @PostMapping(value = "/cadastrar", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> cadastrarPais(@RequestBody @Valid PaisDTO dto) {
        PaisDTO pais = paisService.criarPais(dto);
        if(pais == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(pais, HttpStatus.OK);
    }
}
