package br.com.api.controller;

import br.com.api.queue.sender.LivroSender;
import br.com.commons.dto.CrudMethod;
import br.com.commons.dto.LivroDTO;
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
public class LivroController {

    @Autowired
    private LivroSender livroSender;

    @PostMapping(value = "/public/livro/lista", produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin
    public ResponseEntity<QueueResponseDTO> listaTodosLivros(){
        QueueResponseDTO response = new QueueResponseDTO();
        try {
            QueueRequestDTO request = new QueueRequestDTO();
            request.setCrudMethod(CrudMethod.LIST);

            response = livroSender.listarLivros(request);
            response.setMensagemRetorno("Controller: Tentativa de listagem");
            log.info(response);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.setErro(true);
            response.setMensagemRetorno("Controller: Erro na listagem");
            response.setObjeto(e);
            log.info(response.getMensagemRetorno(), e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

    }

    @PostMapping(value = "public/livro/buscar/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin
    public ResponseEntity<QueueResponseDTO> buscarLivro(@PathVariable("id") UUID id) {
        QueueResponseDTO response = new QueueResponseDTO();
        try {
            QueueRequestDTO request = new QueueRequestDTO();
            request.setObjeto(id);
            request.setCrudMethod(CrudMethod.GET);

            response = livroSender.listarLivroPorId(request);
            response.setMensagemRetorno("Controller: Tentativa de buscar");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Erro ao enviar livro para o RabbitMQ", e);
            response.setMensagemRetorno("Erro ao enviar livro para o RabbitMQ");
            return ResponseEntity.ok(response);
        }
    }

    @PostMapping(value = "public/livro/detalhes/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin
    public ResponseEntity<QueueResponseDTO> buscarDetalhesLivro(@PathVariable("id") UUID id) {
        QueueResponseDTO response = new QueueResponseDTO();
        try {
            QueueRequestDTO request = new QueueRequestDTO();
            request.setObjeto(id);
            request.setCrudMethod(CrudMethod.DETAIL);

            response = livroSender.listarDetalhesLivroPorId(request);
            response.setMensagemRetorno("Controller: Tentativa de buscar");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Erro ao enviar livro para o RabbitMQ", e);
            response.setMensagemRetorno("Erro ao enviar livro para o RabbitMQ");
            return ResponseEntity.ok(response);
        }
    }

    @PostMapping(value = "public/livro/cadastrar", produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin
    public ResponseEntity<QueueResponseDTO> cadastrarLivro(@RequestBody @Valid LivroDTO dto) {
        QueueResponseDTO response = new QueueResponseDTO();
        try {
            QueueRequestDTO request = new QueueRequestDTO();
            request.setObjeto(dto);
            request.setCrudMethod(CrudMethod.INSERT);

            response = livroSender.cadastrarLivro(request);
            response.setMensagemRetorno("Controller: Cadastrado com sucesso");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Erro ao enviar livro para o RabbitMQ", e);
            response.setMensagemRetorno("Erro ao enviar livro para o RabbitMQ");
            return ResponseEntity.ok(response);
        }
    }
}
