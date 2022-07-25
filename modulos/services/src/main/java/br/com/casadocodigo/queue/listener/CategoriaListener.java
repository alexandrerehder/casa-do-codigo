package br.com.casadocodigo.queue.listener;

import br.com.casadocodigo.service.CategoriaService;
import br.com.commons.dto.CategoriaDTO;
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
public class CategoriaListener {

    @Autowired
    private CategoriaService categoriaService;

    @RabbitListener(queues = "${ync.fila.categoria.rpc.queue}")
    public QueueResponseDTO processaEnvioAutor(QueueRequestDTO request) throws Exception {
        QueueResponseDTO response = new QueueResponseDTO();

        switch (request.getCrudMethod()) {

            case GET:
                try {
                    UUID id = (UUID) request.getObjeto();
                    log.info("ID recebido:" + "\n" + id);

                    CategoriaDTO categoriaPorId = categoriaService.buscarCategoriaPorId(id);

                    if(Objects.isNull(categoriaPorId.getId())) {
                        log.info("Categoria não encontrada");

                        response.setMensagemRetorno("Categoria não encontrada");
                        response.setErro(false);
                        response.setObjeto("Data/Horário da transação: " + LocalDateTime.now());
                    }else {
                        log.info("Autor referente ao ID:" + "\n" + categoriaPorId);

                        response.setMensagemRetorno("Categoria encontrada");
                        response.setErro(false);
                        response.setObjeto(categoriaPorId);
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
                    CategoriaDTO categoria = (CategoriaDTO) request.getObjeto();
                    log.info("Objeto recebido:" + "\n" + categoria);

                    CategoriaDTO categoriaCadastrada = categoriaService.criarCategoria(categoria);

                    if(Objects.isNull(categoriaCadastrada.getId())) {
                        log.info("Listener: Informações incorretas. Categoria já cadastrada.");

                        response.setMensagemRetorno("Falha ao cadastrar. Categoria já cadastrada.");
                        response.setErro(false);
                        response.setObjeto("Data/Horário da transação: " + LocalDateTime.now());
                        break;
                    }else {
                        log.info("Categoria cadastrada:" + "\n" + categoriaCadastrada);

                        response.setMensagemRetorno("Categoria cadastrada com sucesso");
                        response.setErro(false);
                        response.setObjeto(categoriaCadastrada);
                    }

                }catch (Exception e) {
                    response.setMensagemRetorno(e.getMessage());
                    response.setErro(true);
                    response.setObjeto(e);
                    log.error("Falha ao cadastrar categoria: " + response);
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
