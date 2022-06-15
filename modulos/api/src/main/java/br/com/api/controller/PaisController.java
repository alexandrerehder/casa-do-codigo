package br.com.api.controller;

import br.com.api.queue.sender.PaisSender;
import br.com.commons.dto.CrudMethod;
import br.com.commons.dto.PaisDTO;
import br.com.commons.dto.QueueRequestDTO;
import br.com.commons.dto.QueueResponseDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@Log4j2
@RestController
@RequestMapping(value = "/v1")
public class PaisController {

    @Autowired
    private PaisSender paisSender;

    @PostMapping(value = "/public/pais/lista", produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin
    public ResponseEntity<QueueResponseDTO> listaTodosPaises(){
        QueueResponseDTO response = new QueueResponseDTO();
        try {
            QueueRequestDTO request = new QueueRequestDTO();
            request.setCrudMethod(CrudMethod.LIST);

            response = paisSender.listarPaises(request);
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

    @PostMapping(value = "public/pais/buscar/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin
    public ResponseEntity<QueueResponseDTO> buscarPais(@PathVariable("id") UUID id) {
        QueueResponseDTO response = new QueueResponseDTO();
        try {
            QueueRequestDTO request = new QueueRequestDTO();
            request.setObjeto(id);
            request.setCrudMethod(CrudMethod.GET);

            response = paisSender.listarPaisPorId(request);
            response.setMensagemRetorno("Controller: Tentativa de busca");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Erro ao enviar mensagem com id do país para o RabbitMQ", e);
            response.setMensagemRetorno("Erro ao enviar mensagem com id do país para o RabbitMQ");
            return ResponseEntity.ok(response);
        }
    }

    @PostMapping(value = "public/pais/cadastrar", produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin
    public ResponseEntity<QueueResponseDTO> cadastrarLivro(@RequestBody @Valid PaisDTO dto) {
        QueueResponseDTO response = new QueueResponseDTO();
        try {
            QueueRequestDTO request = new QueueRequestDTO();
            request.setObjeto(dto);
            request.setCrudMethod(CrudMethod.INSERT);

            response = paisSender.cadastrarPais(request);
            response.setMensagemRetorno("Controller: Cadastrado com sucesso");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Erro ao enviar país para o RabbitMQ", e);
            response.setMensagemRetorno("Erro ao enviar país para o RabbitMQ");
            return ResponseEntity.ok(response);
        }
    }

}
