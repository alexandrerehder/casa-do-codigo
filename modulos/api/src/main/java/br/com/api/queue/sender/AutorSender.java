package br.com.api.queue.sender;

import br.com.commons.dto.QueueRequestDTO;
import br.com.commons.dto.QueueResponseDTO;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class UsuarioSender {

	@Autowired
	private RabbitTemplate template;

	@Value("${casadocodigo.direct.exchange.general.queues}")
	private String directExchange;
	
	public QueueResponseDTO listarAutores(QueueRequestDTO request) throws Exception {
		QueueResponseDTO response = new QueueResponseDTO();
		try {
			response = (QueueResponseDTO) template.convertSendAndReceive(directExchange, "filaUsuarioRpcQueue", request);
			return response;
		} catch (Exception e) {
			response.setMensagemRetorno("Erro na request!");
			return response;
		}
	}
	
	public QueueResponseDTO listarAutorPorId(QueueRequestDTO request) throws Exception {
		QueueResponseDTO response = new QueueResponseDTO();
		try {
			response = (QueueResponseDTO) template.convertSendAndReceive(directExchange, "filaUsuarioRpcQueue", request);
			return response;
		} catch (Exception e) {
			response.setMensagemRetorno("Erro ao enviar ID !");
			return response;
		}
	}
	
	public QueueResponseDTO cadastrarAutor(QueueRequestDTO request) throws Exception {
		QueueResponseDTO response = new QueueResponseDTO();
		try {
			response = (QueueResponseDTO) template.convertSendAndReceive(directExchange, "filaUsuarioRpcQueue", request);
			return response;
		} catch (Exception e) {
			response.setMensagemRetorno("Erro ao enviar usuário !");
			return response;
		}
	}
	
	public QueueResponseDTO deletarAutorPorId(QueueRequestDTO request) throws Exception {
		QueueResponseDTO response = new QueueResponseDTO();
		try {
			response = (QueueResponseDTO) template.convertSendAndReceive(directExchange, "filaUsuarioRpcQueue", request);
			return response;
		} catch (Exception e) {
			response.setMensagemRetorno("Erro ao enviar ID !");
			return response;
		}
	}	
	
}
