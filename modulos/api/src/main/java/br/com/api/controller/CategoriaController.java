package br.com.api.controller;

import br.com.api.queue.sender.CategoriaSender;
import br.com.commons.dto.CategoriaDTO;
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
public class CategoriaController {

    @Autowired
    private CategoriaSender categoriaSender;

    @PostMapping(value = "public/categoria/buscar/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin
    public ResponseEntity<QueueResponseDTO> buscarCategoria(@PathVariable("id") UUID id) {
        QueueResponseDTO response = new QueueResponseDTO();
        try {
            QueueRequestDTO request = new QueueRequestDTO();
            request.setObjeto(id);
            request.setCrudMethod(CrudMethod.GET);

            response = categoriaSender.listarCategoriaPorId(request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Erro ao enviar categoria para o RabbitMQ", e);
            response.setMensagemRetorno("Erro ao enviar categoria para o RabbitMQ");
            return ResponseEntity.ok(response);
        }
    }

    @PostMapping(value = "public/categoria/cadastrar", produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin
    public ResponseEntity<QueueResponseDTO> cadastrarCategoria(@RequestBody @Valid CategoriaDTO dto) {
        QueueResponseDTO response = new QueueResponseDTO();
        try {
            QueueRequestDTO request = new QueueRequestDTO();
            request.setObjeto(dto);
            request.setCrudMethod(CrudMethod.INSERT);

            response = categoriaSender.cadastrarCategoria(request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Erro ao enviar categoria para o RabbitMQ", e);
            response.setMensagemRetorno("Erro ao enviar categoria para o RabbitMQ");
            return ResponseEntity.ok(response);
        }
    }
}
