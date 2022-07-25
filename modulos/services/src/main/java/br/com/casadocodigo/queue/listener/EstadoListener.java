package br.com.casadocodigo.queue.listener;

import br.com.casadocodigo.service.EstadoService;
import br.com.casadocodigo.service.PaisService;
import br.com.commons.dto.EstadoDTO;
import br.com.commons.dto.PaisDTO;
import br.com.commons.dto.QueueRequestDTO;
import br.com.commons.dto.QueueResponseDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Log4j2
@Component
public class EstadoListener {

    @Autowired
    private EstadoService estadoService;

    @Autowired
    private PaisService paisService;

    @RabbitListener(queues = "${ync.fila.estado.rpc.queue}")
    public QueueResponseDTO processaEnvioEstado(QueueRequestDTO request) throws Exception {
        QueueResponseDTO response = new QueueResponseDTO();

        switch (request.getCrudMethod()) {

            case LIST:
                List<EstadoDTO> listaDeEstados = new ArrayList<>();
                try {
                    listaDeEstados = estadoService.listarTodos();

                    if (!listaDeEstados.isEmpty()) {
                        log.info("Quantidade de estados encontrados: " + listaDeEstados.size());
                        response.setMensagemRetorno("Estados encontrados");
                        response.setObjeto(listaDeEstados);
                        response.setErro(false);
                    } else {
                        log.info("Objeto vazio");
                        response.setMensagemRetorno("Nenhum estado encontrado");
                        response.setObjeto("Data/Horário da transação: " + LocalDateTime.now());
                        response.setErro(false);
                    }
                } catch (Exception e) {
                    response.setMensagemRetorno(e.getMessage());
                    response.setErro(true);
                    response.setObjeto(e);
                    log.error("Nenhum estado encontrado: " + response);
                }

                break;

            case GET:
                try {
                    UUID id = (UUID) request.getObjeto();
                    log.info("ID recebido:" + "\n" + id);

                    EstadoDTO estadoPorId = estadoService.buscarEstadoPorId(id);

                    if(Objects.isNull(estadoPorId.getId())) {
                        log.info("Estado não encontrado");

                        response.setMensagemRetorno("Estado não encontrado");
                        response.setErro(false);
                        response.setObjeto("Data/Horário da transação: " + LocalDateTime.now());
                    } else {
                        log.info("Estado referente ao ID:" + "\n" + estadoPorId);

                        response.setMensagemRetorno("Estado encontrado");
                        response.setErro(false);
                        response.setObjeto(estadoPorId);
                    }

                }catch (Exception e) {
                    response.setMensagemRetorno(e.getMessage());
                    response.setErro(true);
                    response.setObjeto(e);
                    log.error("Falha ao buscar Estado: " + response);
                }

                break;

            case INSERT:
                try {
                    EstadoDTO estado = (EstadoDTO) request.getObjeto();
                    log.info("Objeto recebido:" + "\n" + estado);


                    EstadoDTO estadoCadastrado = estadoService.criarEstado(estado);

                    if (Objects.isNull(estadoCadastrado.getId())) {
                        log.info("Listener: Informações incorretas. Estado já cadastrado ao país.");

                        response.setMensagemRetorno("Falha ao cadastrar. Estado já cadastrado ao país.");
                        response.setErro(false);
                        response.setObjeto("Data/Horário da transação: " + LocalDateTime.now());
                        break;
                    }else {
                        log.info("Estado cadastrado:" + "\n" + estadoCadastrado);

                        response.setMensagemRetorno("Estado cadastrado com sucesso");
                        response.setErro(false);
                        response.setObjeto(estadoCadastrado);
                    }

                }catch (Exception e) {
                    response.setMensagemRetorno(e.getMessage());
                    response.setErro(true);
                    response.setObjeto(e);
                    log.error("Falha ao cadastrar estado: " + response);
                }

                break;

            default:
                response.setMensagemRetorno("Erro interno!");
                response.setErro(true);

                break;
        }
        return response;
    }
}
