package br.com.casadocodigo.queue.listener;

import br.com.casadocodigo.domain.Autor;
import br.com.casadocodigo.service.AutorService;
import br.com.casadocodigo.service.CategoriaService;
import br.com.casadocodigo.service.LivroService;
import br.com.commons.dto.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Log4j2
@Component
public class LivroListener {

    @Autowired
    private LivroService livroService;

    @Autowired
    private AutorService autorService;

    @Autowired
    private CategoriaService categoriaService;

    @RabbitListener(queues = "${ync.fila.livro.rpc.queue}")
    public QueueResponseDTO processaEnvioLivro(QueueRequestDTO request) {
        QueueResponseDTO response = new QueueResponseDTO();

        switch (request.getCrudMethod()) {

            case LIST:
                List<LivroDTO> listaDeLivros;
                try {
                    listaDeLivros = livroService.listarTodos();

                    if (!listaDeLivros.isEmpty()) {
                        log.info("Quantidade de livros encontrados: " + listaDeLivros.size());
                        response.setMensagemRetorno("Livros encontrados");
                        response.setObjeto(listaDeLivros);
                        response.setErro(false);
                    } else {
                        log.error("Nenhum livro encontrado" + response);
                        response.setMensagemRetorno("Nenhum livro encontrado");
                        response.setObjeto("Data/Horário da transação: " + LocalDateTime.now());
                        response.setErro(false);
                    }
                }catch (Exception e) {
                    response.setMensagemRetorno(e.getMessage());
                    response.setErro(true);
                    response.setObjeto(e);
                    log.error("Nenhum livro encontrado: " + response);
                }

                break;

            case GET:
                try {
                    UUID id = (UUID) request.getObjeto();
                    log.info("ID recebido:" + "\n" + id);

                    LivroDTO livroPorId = livroService.buscarLivroPorId(id);

                    if(Objects.isNull(livroPorId.getId())) {
                        log.info("Livro não encontrado");

                        response.setMensagemRetorno("Livro não encontrado");
                        response.setErro(false);
                        response.setObjeto("Data/Horário da transação: " + LocalDateTime.now());
                    }else {
                        log.info("Livro referente ao ID:" + "\n" + livroPorId);

                        response.setMensagemRetorno("Livro encontrado");
                        response.setErro(false);
                        response.setObjeto(livroPorId);
                    }
                }catch (Exception e) {
                    response.setMensagemRetorno(e.getMessage());
                    response.setErro(true);
                    response.setObjeto(e);
                    log.error("Falha ao buscar Livro: " + response);
                }

                break;

            case DETAIL:
                try {
                    UUID id = (UUID) request.getObjeto();
                    log.info("ID recebido:" + "\n" + id);

                    LivroDetalhesDTO detalhesLivroPorId = livroService.buscarDetalhesLivroPorId(id);

                    if(Objects.isNull(detalhesLivroPorId.getAutor())) {
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
                    log.error("Falha ao buscar Livro: " + response);
                }

                break;

            case INSERT:
                try {
                    LocalDate today = LocalDate.now();
                    LivroDTO livro = (LivroDTO) request.getObjeto();
                    log.info("Objeto recebido:" + "\n" + livro);

                    AutorDTO autor = autorService.buscarAutorPorId(livro.getAutor().getId());

                    if(Objects.isNull(autor.getId())) {
                        response.setMensagemRetorno("Autor não cadastrado");
                        response.setErro(false);
                        response.setObjeto("Verifique o ID do autor ou se o mesmo possui cadastrado");
                        break;
                    }

                    CategoriaDTO categoria = categoriaService.buscarCategoriaPorId(livro.getCategoria().getId());

                    if(Objects.isNull(categoria.getId())) {
                        response.setMensagemRetorno("Categoria não cadastrada");
                        response.setErro(false);
                        response.setObjeto("Verifique o ID da categoria ou se o mesma possui cadastrado");
                        break;
                    }

                    if(today.isAfter(livro.getLancamento())) {
                        response.setMensagemRetorno("Data de lançamento incorreta");
                        response.setErro(false);
                        response.setObjeto("A data de lançamento deverá sempre estar no futuro");
                        break;
                    }

                    LivroDTO livroCadastrado = livroService.criarLivro(livro);

                        if(Objects.nonNull(livroCadastrado)) {
                            log.info("Livro cadastrado:" + "\n" + livroCadastrado);

                            response.setMensagemRetorno("Livro cadastrado com sucesso");
                            response.setErro(false);
                            response.setObjeto(livroCadastrado);
                        }

                        if(Objects.isNull(livroCadastrado)) {
                            log.info("Listener: Informações incorretas");

                            response.setMensagemRetorno("Falha ao cadastrar. Título ou ISBN já cadastrados");
                            response.setErro(false);
                            response.setObjeto("Data/Horário da transação: " + LocalDateTime.now());
                        }
                }   catch (Exception e) {
                    response.setMensagemRetorno(e.getMessage());
                    response.setErro(true);
                    response.setObjeto(e);
                    log.error("Falha ao cadastrar livro: " + response);
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
