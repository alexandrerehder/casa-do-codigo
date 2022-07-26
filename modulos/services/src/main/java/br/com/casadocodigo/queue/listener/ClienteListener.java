package br.com.casadocodigo.queue.listener;

import br.com.casadocodigo.service.ClienteService;
import br.com.commons.dto.ClienteDTO;
import br.com.commons.dto.ClienteIdDTO;
import br.com.commons.dto.QueueRequestDTO;
import br.com.commons.dto.QueueResponseDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Log4j2
@Component
public class ClienteListener {

    @Autowired
    private ClienteService clienteService;

    @RabbitListener(queues = "${ync.fila.cliente.rpc.queue}")
    public QueueResponseDTO processaEnvioLivro(QueueRequestDTO request) {
        QueueResponseDTO response = new QueueResponseDTO();

        switch (request.getCrudMethod()) {

            case LIST:
                List<ClienteDTO> listaDeClientes;
                try {
                    listaDeClientes = clienteService.listarTodos();

                    if (!listaDeClientes.isEmpty()) {
                        log.info("Quantidade de clientes encontrados: " + listaDeClientes.size());
                        response.setMensagemRetorno("Clientes encontrados");
                        response.setObjeto(listaDeClientes);
                        response.setErro(false);
                    } else {
                        log.info("Objeto vazio");
                        response.setMensagemRetorno("Nenhum cliente encontrado");
                        response.setObjeto("Data/Horário da transação: " + LocalDateTime.now());
                        response.setErro(false);
                    }
                }catch (Exception e) {
                    response.setMensagemRetorno(e.getMessage());
                    response.setErro(true);
                    response.setObjeto(e);
                    log.error("Nenhum cliente encontrado: " + response);
                }

                break;

            case GET:
                try {
                    UUID id = (UUID) request.getObjeto();
                    log.info("ID recebido:" + "\n" + id);

                    ClienteDTO clientePorId = clienteService.buscarClientePorId(id);

                    if(Objects.isNull(clientePorId.getId())) {
                        log.info("Cliente não encontrado");

                        response.setMensagemRetorno("Cliente não encontrado");
                        response.setErro(false);
                        response.setObjeto("Data/Horário da transação: " + LocalDateTime.now());
                    }else {
                        log.info("Livro referente ao ID:" + "\n" + clientePorId);

                        response.setMensagemRetorno("Cliente encontrado");
                        response.setErro(false);
                        response.setObjeto(clientePorId);
                    }

                }catch (Exception e) {
                    response.setMensagemRetorno(e.getMessage());
                    response.setErro(true);
                    response.setObjeto(e);
                    log.error("Falha ao buscar cliente: " + response);
                }

                break;

            default:
                response.setMensagemRetorno("Erro interno!");
                response.setErro(true);

                break;

            case INSERT:
                try {
                    ClienteDTO cliente = (ClienteDTO) request.getObjeto();
                    log.info("Objeto recebido:" + "\n" + cliente);

                    ClienteIdDTO clienteCadastrado = clienteService.criarCliente(cliente);

                    if(Objects.isNull(clienteCadastrado.getId())) {
                        log.info("Listener: Informações incorretas");

                        response.setMensagemRetorno("Falha ao cadastrar. Verifique se as informações estão corretas");
                        response.setErro(false);
                        response.setObjeto("Data/Horário da transação: " + LocalDateTime.now());
                    }else {
                        log.info("Cliente cadastrado:" + "\n" + clienteCadastrado);

                        response.setMensagemRetorno("Cliente cadastrado com sucesso");
                        response.setErro(false);
                        response.setObjeto(clienteCadastrado);
                    }

                }catch (Exception e) {
                    response.setMensagemRetorno(e.getMessage());
                    response.setErro(true);
                    response.setObjeto(e);
                    log.error("Falha ao cadastrar cliente: " + response);
                }

                break;
        }
        return response;
    }
}
