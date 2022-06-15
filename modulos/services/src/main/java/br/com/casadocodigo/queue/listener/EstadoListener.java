package br.com.casadocodigo.queue.listener;

import br.com.casadocodigo.service.EstadoService;
import br.com.commons.dto.EstadoDTO;
import br.com.commons.dto.LivroDTO;
import br.com.commons.dto.QueueRequestDTO;
import br.com.commons.dto.QueueResponseDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Log4j2
@Component
public class EstadoListener {

    @Autowired
    private EstadoService estadoService;

    @RabbitListener(queues = "${thanos.fila.estado.rpc.queue}")
    public QueueResponseDTO processaEnvioEstado(QueueRequestDTO request) throws Exception {
        QueueResponseDTO response = new QueueResponseDTO();

        switch (request.getCrudMethod()) {

            case LIST:
                List<EstadoDTO> listaDeEstado = new ArrayList<>();
                try {
                    listaDeEstado = estadoService.listarTodos();
                    log.info("Quantidade de estados encontrados: " + listaDeEstado.size());
                    response.setMensagemRetorno("Estados encontrados");
                    response.setObjeto(listaDeEstado);
                    response.setErro(false);
                } catch (Exception e) {
                    response.setMensagemRetorno(e.getMessage());
                    response.setErro(true);
                    response.setObjeto(e);
                    log.error("Nenhum estado encontrado: ", response);
                }

                break;

            default:
                response.setMensagemRetorno("Erro interno!");
                response.setErro(true);

                break;

            case GET:
                try {
                    UUID id = (UUID) request.getObjeto();
                    log.info("ID recebido:" + "\n" + id);

                    EstadoDTO estadoPorId = estadoService.buscarEstadoPorId(id);

                    if(estadoPorId.getId() == null) {
                        log.info("Estado não encontrado");

                        response.setMensagemRetorno("Estado não encontrado");
                        response.setErro(false);
                        response.setObjeto("Data/Horário da transação: " + LocalDateTime.now());
                    }else {
                        log.info("Estado referente ao ID:" + "\n" + estadoPorId);

                        response.setMensagemRetorno("Estado encontrado");
                        response.setErro(false);
                        response.setObjeto(estadoPorId);
                    }

                }catch (Exception e) {
                    response.setMensagemRetorno(e.getMessage());
                    response.setErro(true);
                    response.setObjeto(e);
                    log.error("Falha ao buscar Estado: ", response);
                }

                break;

            case INSERT:
                try {
                    EstadoDTO estado = (EstadoDTO) request.getObjeto();
                    log.info("Objeto recebido:" + "\n" + estado);

                    EstadoDTO estadoCadastrado = estadoService.criarEstado(estado);

                    if(estadoCadastrado == null) {
                        log.info("Listener: Informações incorretas");

                        response.setMensagemRetorno("Falha ao cadastrar. Verifique se as informações estão corretas");
                        response.setErro(false);
                        response.setObjeto("Data/Horário da transação: " + LocalDateTime.now());
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
                    log.error("Falha ao cadastrar estado: ", response);
                }

                break;
        }
        return response;
    }
}
