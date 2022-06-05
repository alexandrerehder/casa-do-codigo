package br.com.casadocodigo.queue.listener;

import br.com.casadocodigo.nativeQueryProjection.LivroResumido;
import br.com.casadocodigo.service.LivroService;
import br.com.commons.dto.LivroDTO;
import br.com.commons.dto.LivroDetalhesDTO;
import br.com.commons.dto.QueueRequestDTO;
import br.com.commons.dto.QueueResponseDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Log4j2
@Component
public class LivroListener {

    @Autowired
    private LivroService livroService;

    @RabbitListener(queues = "${thanos.fila.livro.rpc.queue}")
    public QueueResponseDTO processaEnvioLivro(QueueRequestDTO request) throws Exception {
        QueueResponseDTO response = new QueueResponseDTO();

        switch (request.getCrudMethod()) {

            case LIST:
                try {
                    List<LivroResumido> listaDeLivros = livroService.listarTodos();
                    if(listaDeLivros == null) {
                        response.setMensagemRetorno("Livros não encontrados");
                        response.setErro(false);
                        response.setObjeto("Empty");
                    }else {
                        log.info("Quantidade de livros encontrados: " + listaDeLivros.size());

                        response.setMensagemRetorno("Livros encontrados");
                        response.setErro(false);
                        response.setObjeto(listaDeLivros);
                    }

                }catch (Exception e) {
                    response.setMensagemRetorno(e.getMessage());
                    response.setErro(true);
                    response.setObjeto(e);
                    log.error("Nenhum livro encontrado: ", response);
                }

                break;

            case GET:
                try {
                    UUID id = (UUID) request.getObjeto();
                    log.info("ID recebido:" + "\n" + id);

                    LivroDTO LivroPorId = livroService.buscarLivroPorId(id);

                    if(LivroPorId.getId() == null) {
                        log.info("Livro não encontrado");

                        response.setMensagemRetorno("Livro não encontrado");
                        response.setErro(false);
                        response.setObjeto("Data/Horário da transação: " + LocalDateTime.now());
                    }else {
                        log.info("Livro referente ao ID:" + "\n" + LivroPorId);

                        response.setMensagemRetorno("Livro encontrado");
                        response.setErro(false);
                        response.setObjeto(LivroPorId);
                    }

                }catch (Exception e) {
                    response.setMensagemRetorno(e.getMessage());
                    response.setErro(true);
                    response.setObjeto(e);
                    log.error("Falha ao buscar Livro: ", response);
                }

                break;

            case DETAIL:
                try {
                    UUID id = (UUID) request.getObjeto();
                    log.info("ID recebido:" + "\n" + id);

                    LivroDetalhesDTO detalhesLivroPorId = livroService.buscarDetalhesLivroPorId(id);

                    if(detalhesLivroPorId.getAutor() == null) {
                        log.info("Livro não encontrado");

                        response.setMensagemRetorno("Livro não encontrado");
                        response.setErro(false);
                        response.setObjeto("Data/Horário da transação: " + LocalDateTime.now());
                    }else {
                        log.info("Livro referente ao ID:" + "\n" + detalhesLivroPorId);

                        response.setMensagemRetorno("Livro encontrado");
                        response.setErro(false);
                        response.setObjeto(detalhesLivroPorId);
                    }

                }catch (Exception e) {
                    response.setMensagemRetorno(e.getMessage());
                    response.setErro(true);
                    response.setObjeto(e);
                    log.error("Falha ao buscar Livro: ", response);
                }

                break;

            case INSERT:
                try {
                    LivroDTO livro = (LivroDTO) request.getObjeto();
                    log.info("Objeto recebido:" + "\n" + livro);

                    LivroDTO livroCadastrado = livroService.criarLivro(livro);

                    if(livroCadastrado == null) {
                        log.info("Listener: Informações incorretas");

                        response.setMensagemRetorno("Falha ao cadastrar. Verifique se as informações estão corretas");
                        response.setErro(false);
                        response.setObjeto("Data/Horário da transação: " + LocalDateTime.now());
                    }else {
                        log.info("Livro cadastrada:" + "\n" + livroCadastrado);

                        response.setMensagemRetorno("Livro cadastrado com sucesso");
                        response.setErro(false);
                        response.setObjeto(livroCadastrado);
                    }

                }catch (Exception e) {
                    response.setMensagemRetorno(e.getMessage());
                    response.setErro(true);
                    response.setObjeto(e);
                    log.error("Falha ao cadastrar livro: ", response);
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
