package br.com.api.controller;

import br.com.api.queue.sender.AutorSender;
import br.com.commons.dto.AutorDTO;
import br.com.commons.dto.CrudMethod;
import br.com.commons.dto.QueueRequestDTO;
import br.com.commons.dto.QueueResponseDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@Log4j2
@RestController
@RequestMapping(value = "/v1")
public class AutorController {

    @Autowired
    private AutorSender autorSender;

    @PostMapping(value = "public/autor/buscar/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin
    public ResponseEntity<QueueResponseDTO> buscarAutor(@PathVariable("id") UUID id) {
        QueueResponseDTO response = new QueueResponseDTO();
        try {
            QueueRequestDTO request = new QueueRequestDTO();
            request.setObjeto(id);
            request.setCrudMethod(CrudMethod.GET);

            return ResponseEntity.ok(autorSender.listarAutorPorId(request));
        } catch (Exception e) {
            log.error("Erro ao enviar autor para o RabbitMQ", e);
            response.setMensagemRetorno("Erro ao enviar autor para o RabbitMQ");
            return ResponseEntity.ok(response);
        }
    }

    @PostMapping(value = "public/autor/cadastrar", produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin
    public ResponseEntity<QueueResponseDTO> cadastrarAutor(@RequestBody @Valid AutorDTO dto) {
        QueueResponseDTO response = new QueueResponseDTO();
        try {
            QueueRequestDTO request = new QueueRequestDTO();
            request.setObjeto(dto);
            request.setCrudMethod(CrudMethod.INSERT);

            return ResponseEntity.ok(autorSender.cadastrarAutor(request));
        } catch (Exception e) {
            log.error("Erro ao enviar autor para o RabbitMQ", e);
            response.setMensagemRetorno("Erro ao enviar autor para o RabbitMQ");
            return ResponseEntity.ok(response);
        }
    }
}


