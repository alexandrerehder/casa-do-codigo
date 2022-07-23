package br.com.casadocodigo.queue.listener;

import br.com.casadocodigo.service.PaisService;
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
import java.util.UUID;

@Log4j2
@Component
public class PaisListener {

    @Autowired
    private PaisService paisService;

    @RabbitListener(queues = "${ync.fila.pais.rpc.queue}")
    public QueueResponseDTO processaEnvioPais(QueueRequestDTO request) throws Exception {
        QueueResponseDTO response = new QueueResponseDTO();

        switch (request.getCrudMethod()) {

            case LIST:
                List<PaisDTO> listaDePaises = new ArrayList<>();
                try {
                    listaDePaises = paisService.listarTodos();
                    log.info("Quantidade de países encontrados: " + listaDePaises.size());
                    response.setMensagemRetorno("Países encontrados");
                    response.setObjeto(listaDePaises);
                    response.setErro(false);
                }catch (Exception e) {
                    response.setMensagemRetorno(e.getMessage());
                    response.setErro(true);
                    response.setObjeto(e);
                    log.error("Nenhum país encontrado: ", response);
                }

                break;

            case GET:
                try {
                    UUID id = (UUID) request.getObjeto();
                    log.info("ID recebido:" + "\n" + id);

                    PaisDTO PaisPorId = paisService.buscarPaisPorId(id);

                    if(PaisPorId.getId() == null) {
                        log.info("Pais não encontrado");

                        response.setMensagemRetorno("Pais não encontrado");
                        response.setErro(false);
                        response.setObjeto("Data/Horário da transação: " + LocalDateTime.now());
                    }else {
                        log.info("Pais referente ao ID:" + "\n" + PaisPorId);

                        response.setMensagemRetorno("Pais encontrado");
                        response.setErro(false);
                        response.setObjeto(PaisPorId);
                    }

                }catch (Exception e) {
                    response.setMensagemRetorno(e.getMessage());
                    response.setErro(true);
                    response.setObjeto(e);
                    log.error("Falha ao buscar Pais: ", response);
                }

                break;

            case INSERT:
                try {
                    PaisDTO pais = (PaisDTO) request.getObjeto();
                    log.info("Objeto recebido:" + "\n" + pais);

                    PaisDTO paisCadastrado = paisService.criarPais(pais);

                    if(paisCadastrado == null) {
                        log.info("Listener: Informações incorretas");

                        response.setMensagemRetorno("Falha ao cadastrar. Verifique se as informações estão corretas");
                        response.setErro(false);
                        response.setObjeto("Data/Horário da transação: " + LocalDateTime.now());
                    }else {
                        log.info("País cadastrado:" + "\n" + paisCadastrado);

                        response.setMensagemRetorno("País cadastrado com sucesso");
                        response.setErro(false);
                        response.setObjeto(paisCadastrado);
                    }

                }catch (Exception e) {
                    response.setMensagemRetorno(e.getMessage());
                    response.setErro(true);
                    response.setObjeto(e);
                    log.error("Falha ao cadastrar país: ", response);
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
