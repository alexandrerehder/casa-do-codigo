package br.com.api.queue.sender;

import br.com.commons.dto.QueueRequestDTO;
import br.com.commons.dto.QueueResponseDTO;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ClienteSender {

    @Autowired
    private RabbitTemplate template;

    @Value("${ync.direct.exchange.estudos.casadocodigo}")
    private String directExchange;

    public QueueResponseDTO listarClientes(QueueRequestDTO request) throws Exception {
        QueueResponseDTO response = new QueueResponseDTO();
        try {
            response = (QueueResponseDTO) template.convertSendAndReceive(directExchange, "filaClienteRpcQueue", request);
            return response;
        } catch (Exception e) {
            response.setMensagemRetorno("Sender: Erro ao enviar requisição");
            return response;
        }
    }

    public QueueResponseDTO listarClientePorId(QueueRequestDTO request) throws Exception {
        QueueResponseDTO response = new QueueResponseDTO();
        try {
            response = (QueueResponseDTO) template.convertSendAndReceive(directExchange, "filaClienteRpcQueue", request);
            return response;
        } catch (Exception e) {
            response.setMensagemRetorno("Sender: Erro ao enviar ID");
            return response;
        }
    }

    public QueueResponseDTO cadastrarCliente(QueueRequestDTO request) throws Exception {
        QueueResponseDTO response = new QueueResponseDTO();
        try {
            response = (QueueResponseDTO) template.convertSendAndReceive(directExchange, "filaClienteRpcQueue", request);
            return response;
        } catch (Exception e) {
            response.setMensagemRetorno("Sender: Erro ao enviar cliente");
            return response;
        }
    }
}
