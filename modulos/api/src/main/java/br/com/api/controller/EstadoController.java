package br.com.api.controller;

import br.com.api.queue.sender.EstadoSender;
import br.com.commons.dto.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.UUID;

@Log4j2
@RestController
@RequestMapping(value = "/v1")
public class EstadoController {

    @Autowired
    private EstadoSender estadoSender;

    @PostMapping(value = "/public/estado/lista", produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin
    public ResponseEntity<QueueResponseDTO> listaTodosEstados(){
        QueueResponseDTO response = new QueueResponseDTO();
        try {
            QueueRequestDTO request = new QueueRequestDTO();
            request.setCrudMethod(CrudMethod.LIST);

            response = estadoSender.listarEstados(request);
            response.setMensagemRetorno("Controller: Tentativa de listagem");
            response.setErro(false);
            log.info(response);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.setErro(true);
            response.setMensagemRetorno("Controller: Erro na listagem");
            response.setObjeto(e);
            log.info(response.getMensagemRetorno(), e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(value = "public/estado/buscar/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin
    public ResponseEntity<QueueResponseDTO> buscarEstado(@PathVariable("id") UUID id) {
        QueueResponseDTO response = new QueueResponseDTO();
        try {
            QueueRequestDTO request = new QueueRequestDTO();
            request.setObjeto(id);
            request.setCrudMethod(CrudMethod.GET);

            if(request.getObjeto() == null) {
                response.setMensagemRetorno("Informe o ID do estado");
                response.setErro(false);
                response.setObjeto("Data/Horário da transação: " + LocalDateTime.now());
                return ResponseEntity.badRequest().body(response);
            }
            response = estadoSender.listarEstadoPorId(request);
            response.setMensagemRetorno("Controller: Tentativa de busca");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Erro ao enviar mensagem com id do país para o RabbitMQ", e);
            response.setMensagemRetorno("Erro ao enviar mensagem com id do estado para o RabbitMQ");
            return ResponseEntity.ok(response);
        }
    }

    @PostMapping(value = "public/estado/cadastrar", produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin
    public ResponseEntity<QueueResponseDTO> cadastrarEstado(@RequestBody @Valid EstadoDTO dto) {
        QueueResponseDTO response = new QueueResponseDTO();
        try {
            QueueRequestDTO request = new QueueRequestDTO();
            request.setObjeto(dto);
            request.setCrudMethod(CrudMethod.INSERT);

            response = estadoSender.cadastrarEstado(request);
            response.setMensagemRetorno("Controller: Cadastrado com sucesso");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Erro ao enviar estado para o RabbitMQ", e);
            response.setMensagemRetorno("Erro ao enviar estado para o RabbitMQ");
            return ResponseEntity.ok(response);
        }
    }
}
