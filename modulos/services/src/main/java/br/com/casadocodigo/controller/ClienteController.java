package br.com.casadocodigo.controller;

import br.com.commons.dto.ClienteDTO;
import br.com.commons.dto.ClienteIdDTO;
import br.com.casadocodigo.service.ClienteService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping(value = "/cliente")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    Gson gson = new Gson();

    @GetMapping(value = "/listarTodos", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> listaTodosClientes() {
        return new ResponseEntity<>(clienteService.listarTodos(), HttpStatus.OK);
    }

    @GetMapping(value = "/buscar/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> buscarCliente(@PathVariable("id") UUID id) {
        ClienteDTO dto = clienteService.buscarClientePorId(id);
        if(dto == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @PostMapping(value = "/cadastrar", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> cadastrarCliente(@RequestBody @Valid ClienteDTO dto) {
        ClienteIdDTO id = clienteService.criarCliente(dto);
        if(id == null) {
            return new ResponseEntity<>(gson.toJson("Problemas de validação"), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(id, HttpStatus.OK);
    }

}
