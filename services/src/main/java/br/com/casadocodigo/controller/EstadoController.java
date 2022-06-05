package br.com.casadocodigo.controller;

import br.com.casadocodigo.dto.EstadoDTO;
import br.com.casadocodigo.service.EstadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping(value = "/estado")
public class EstadoController {

    @Autowired
    private EstadoService estadoService;

    @GetMapping(value = "/listarTodos", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> listaTodosEstados() {
        return new ResponseEntity<>(estadoService.listarTodos(), HttpStatus.OK);
    }

    @GetMapping(value = "/buscar/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> buscarEstado(@PathVariable("id") UUID id) {
        EstadoDTO dto  = estadoService.buscarEstadoPorId(id);
        if(dto.getId() == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @PostMapping(value = "/cadastrar", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> cadastrarEstado(@RequestBody @Valid EstadoDTO dto) {
        EstadoDTO estado = estadoService.criarEstado(dto);
        if(estado == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(estado, HttpStatus.OK);
    }
}
