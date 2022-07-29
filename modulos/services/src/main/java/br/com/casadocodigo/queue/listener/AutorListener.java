package br.com.casadocodigo.queue.listener;

import br.com.casadocodigo.service.AutorService;
import br.com.commons.dto.AutorDTO;
import br.com.commons.dto.QueueRequestDTO;
import br.com.commons.dto.QueueResponseDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Log4j2
@Component
public class AutorListener {

	@Autowired
	private AutorService AutorService;

	@RabbitListener(queues = "${ync.fila.autor.rpc.queue}")
	public QueueResponseDTO processaEnvioAutor(QueueRequestDTO request) throws Exception {
		QueueResponseDTO response = new QueueResponseDTO();

		switch (request.getCrudMethod()) {

			case GET:
				try {
					UUID id = (UUID) request.getObjeto();
					log.info("ID recebido:" + "\n" + id);

					AutorDTO autorPorId = AutorService.buscarAutorPorId(id);

					if(Objects.isNull(autorPorId.getId())) {
						log.info("Autor não encontrado");

						response.setMensagemRetorno("Autor não encontrado");
						response.setErro(false);
						response.setObjeto("Data/Horário da transação: " + LocalDateTime.now());
					}else {
						log.info("Autor referente ao ID:" + "\n" + autorPorId);

						response.setMensagemRetorno("Autor encontrado");
						response.setErro(false);
						response.setObjeto(autorPorId);
					}

				}catch (Exception e) {
					response.setMensagemRetorno(e.getMessage());
					response.setErro(true);
					response.setObjeto(e);
					log.error("Falha ao buscar autor: " + response);
				}

				break;

			case INSERT:
				try {
					AutorDTO autor = (AutorDTO) request.getObjeto();
					log.info("Objeto recebido:" + "\n" + autor);

					AutorDTO autorIsExistente = AutorService.buscarAutorPorEmail(autor);

					if (Objects.nonNull(autorIsExistente.getEmail())) {
						log.info("Listener: Informações incorretas. Autor já cadastrado");

						response.setMensagemRetorno("Falha ao cadastrar. Autor já cadastrado.");
						response.setErro(false);
						response.setObjeto("Data/Horário da transação: " + LocalDateTime.now());
						break;
					}

					AutorDTO autorCadastrado = AutorService.criarAutor(autor);

					if(Objects.isNull(autorCadastrado.getId())) {
						log.info("Listener: Informações incorretas");

						response.setMensagemRetorno("Falha ao cadastrar. Verifique se as informações estão corretas");
						response.setErro(false);
						response.setObjeto("Data/Horário da transação: " + LocalDateTime.now());
					}else {
						log.info("Autor cadastrado:" + "\n" + autorCadastrado);

						response.setMensagemRetorno("Autor cadastrado com sucesso");
						response.setErro(false);
						response.setObjeto(autorCadastrado);
					}

				}catch (Exception e) {
					response.setMensagemRetorno("Falha ao cadastrar autor. Verifique campos nulos ou informações incorretas/já cadastradas.");
					response.setErro(true);
					response.setObjeto("Data/Horário da transação: " + LocalDateTime.now());
					log.error("Falha ao cadastrar autor: " + response);
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
