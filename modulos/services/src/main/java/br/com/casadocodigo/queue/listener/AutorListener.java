package br.com.casadocodigo.queue.listener;

import br.com.casadocodigo.service.AutorService;
import br.com.commons.dto.AutorDTO;
import br.com.commons.dto.QueueRequestDTO;
import br.com.commons.dto.QueueResponseDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Log4j2
@Component
public class AutorListener {

	@Autowired
	private AutorService AutorService;

	@RabbitListener(queues = "${casadocodigo.fila.autor.rpc.queue}")
	public QueueResponseDTO processaEnvioAutor(QueueRequestDTO request) throws Exception {
		QueueResponseDTO response = new QueueResponseDTO();

		switch (request.getCrudMethod()) {

			case GET:
				try {
					AutorDTO id = (AutorDTO) request.getObjeto();
					log.info("Objeto recebido:" + "\n" + id);

					List<AutorDTO> listaDeAutores = AutorService.buscarAutorPorId(id);

					log.info("Quantidade de autores encontrados:" + listaDeAutores.size());

					response.setMensagemRetorno("Autores encontrados");
					response.setErro(false);
					response.setObjeto(listaDeAutores);

				}catch (Exception e) {
					response.setMensagemRetorno(e.getMessage());
					response.setErro(true);
					response.setObjeto(e);
					log.error("Nenhuma Operação encontrada: ", response);
				}

				break;

			case INSERT:
				try {
					AutorDTO Autor = (AutorDTO) request.getObjeto();
					log.info("Objecto recebido:" + "\n" + Autor);

					AutorDTO AutorCadastrada = AutorService.save(Autor);

					log.info("Operação cadastrada:" + "\n" + AutorCadastrada);

					response.setMensagemRetorno("Operação cadastrada com sucesso");
					response.setErro(false);
					response.setObjeto(AutorCadastrada);
				}catch (Exception e) {
					response.setMensagemRetorno(e.getMessage());
					response.setErro(true);
					response.setObjeto(e);
					log.error("Falha ao cadastrar operação: ", response);
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
