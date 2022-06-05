package br.com.api.queue.sender;

import br.com.commons.dto.QueueRequestDTO;
import br.com.commons.dto.QueueResponseDTO;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class LivroSender {

    @Autowired
    private RabbitTemplate template;

    @Value("${thanos.direct.exchange.estudos.operacao}")
    private String directExchange;

    public QueueResponseDTO listarLivros(QueueRequestDTO request) throws Exception {
        QueueResponseDTO response = new QueueResponseDTO();
        try {
            response = (QueueResponseDTO) template.convertSendAndReceive(directExchange, "filaLivroRpcQueue", request);
            return response;
        } catch (Exception e) {
            response.setMensagemRetorno("Sender: Erro ao enviar requisição");
            return response;
        }
    }

    public QueueResponseDTO listarLivroPorId(QueueRequestDTO request) throws Exception {
        QueueResponseDTO response = new QueueResponseDTO();
        try {
            response = (QueueResponseDTO) template.convertSendAndReceive(directExchange, "filaLivroRpcQueue", request);
            return response;
        } catch (Exception e) {
            response.setMensagemRetorno("Sender: Erro ao enviar ID");
            return response;
        }
    }

    public QueueResponseDTO listarDetalhesLivroPorId(QueueRequestDTO request) throws Exception {
        QueueResponseDTO response = new QueueResponseDTO();
        try {
            response = (QueueResponseDTO) template.convertSendAndReceive(directExchange, "filaLivroRpcQueue", request);
            return response;
        } catch (Exception e) {
            response.setMensagemRetorno("Sender: Erro ao enviar ID");
            return response;
        }
    }

    public QueueResponseDTO cadastrarLivro(QueueRequestDTO request) throws Exception {
        QueueResponseDTO response = new QueueResponseDTO();
        try {
            response = (QueueResponseDTO) template.convertSendAndReceive(directExchange, "filaLivroRpcQueue", request);
            return response;
        } catch (Exception e) {
            response.setMensagemRetorno("Sender: Erro ao enviar livro");
            return response;
        }
    }
}
