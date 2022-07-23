package br.com.api.queue.sender;

import br.com.commons.dto.QueueRequestDTO;
import br.com.commons.dto.QueueResponseDTO;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class AutorSender {

	@Autowired
	private RabbitTemplate template;

	@Value("${ync.direct.exchange.estudos.casadocodigo}")
	private String directExchange;

	public QueueResponseDTO listarAutorPorId(QueueRequestDTO request) throws Exception {
		QueueResponseDTO response = new QueueResponseDTO();
		try {
			response = (QueueResponseDTO) template.convertSendAndReceive(directExchange, "filaAutorRpcQueue", request);
			return response;
		} catch (Exception e) {
			response.setMensagemRetorno("Erro ao enviar ID !");
			return response;
		}
	}
	
	public QueueResponseDTO cadastrarAutor(QueueRequestDTO request) throws Exception {
		QueueResponseDTO response = new QueueResponseDTO();
		try {
			response = (QueueResponseDTO) template.convertSendAndReceive(directExchange, "filaAutorRpcQueue", request);
			return response;
		} catch (Exception e) {
			response.setMensagemRetorno("Erro ao enviar autor !");
			return response;
		}
	}
}
