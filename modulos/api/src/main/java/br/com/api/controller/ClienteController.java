package br.com.api.controller;

import br.com.api.queue.sender.ClienteSender;
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
public class ClienteController {

    @Autowired
    private ClienteSender clienteSender;

    @PostMapping(value = "/public/cliente/lista", produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin
    public ResponseEntity<QueueResponseDTO> listaTodosClientes(){
        QueueResponseDTO response = new QueueResponseDTO();
        try {
            QueueRequestDTO request = new QueueRequestDTO();
            request.setCrudMethod(CrudMethod.LIST);

            response = clienteSender.listarClientes(request);
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

    @PostMapping(value = "public/cliente/buscar/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin
    public ResponseEntity<QueueResponseDTO> buscarCliente(@PathVariable("id") UUID id) {
        QueueResponseDTO response = new QueueResponseDTO();
        try {
            QueueRequestDTO request = new QueueRequestDTO();
            request.setObjeto(id);
            request.setCrudMethod(CrudMethod.GET);

            if (request.getObjeto() == null) {
                response.setMensagemRetorno("Informe o ID do cliente");
                response.setErro(false);
                response.setObjeto("Data/Hor??rio da transa????o: " + LocalDateTime.now());
                return ResponseEntity.badRequest().body(response);
            }
            response = clienteSender.listarClientePorId(request);
            response.setMensagemRetorno("Controller: Tentativa de busca");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Erro ao enviar mensagem com id do cliente para o RabbitMQ", e);
            response.setMensagemRetorno("Erro ao enviar mensagem com id do cliente para o RabbitMQ");
            return ResponseEntity.ok(response);
        }
    }

    @PostMapping(value = "public/cliente/cadastrar", produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin
    public ResponseEntity<QueueResponseDTO> cadastrarCliente(@RequestBody @Valid ClienteDTO dto) {
        QueueResponseDTO response = new QueueResponseDTO();
        try {
            QueueRequestDTO request = new QueueRequestDTO();
            request.setObjeto(dto);
            request.setCrudMethod(CrudMethod.INSERT);

            response = clienteSender.cadastrarCliente(request);
            response.setMensagemRetorno("Controller: Cadastrado com sucesso");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Erro ao enviar cliente para o RabbitMQ", e);
            response.setMensagemRetorno("Erro ao enviar cliente para o RabbitMQ");
            return ResponseEntity.ok(response);
        }
    }
}
