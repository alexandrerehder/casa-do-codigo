package br.com.casadocodigo.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AmqpConfiguration {

	@Value("${amqp.username}")
	private String userName;

	@Value("${amqp.password}")
	private String password;

	@Value("${amqp.virtualHost}")
	private String virtualHost;

	@Value("${amqp.host}")
	private String host;

	@Value("${amqp.port}")
	private String port;

	@Value("${amqp.uri}")
	private String uri;

	@Value("${ync.fila.autor.rpc.queue}")
	private String filaAutorRpcQueue;

	@Value("${ync.fila.categoria.rpc.queue}")
	private String filaCategoriaRpcQueue;

	@Value("${ync.fila.livro.rpc.queue}")
	private String filaLivroRpcQueue;

	@Value("${ync.fila.pais.rpc.queue}")
	private String filaPaisRpcQueue;

	@Value("${ync.fila.estado.rpc.queue}")
	private String filaEstadoRpcQueue;

	@Value("${ync.fila.cliente.rpc.queue}")
	private String filaClienteRpcQueue;

	@Value("${ync.direct.exchange.estudos.casadocodigo}")
	private String directExchange;

	@Bean
	public ConnectionFactory jmsConnectionFactory() {
		CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
		connectionFactory.setUsername(userName);
		connectionFactory.setPassword(password);
		connectionFactory.setVirtualHost(virtualHost);
		connectionFactory.setPort(Integer.parseInt(port));
		connectionFactory.setUri(uri);
		connectionFactory.setHost(host);
		return connectionFactory;
	}

	@Bean
	public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
		return new RabbitTemplate(connectionFactory);
	}

	@Bean
	public SimpleRabbitListenerContainerFactory tenantRabbitListenerContainerFactory(
			SimpleRabbitListenerContainerFactoryConfigurer configurer, ConnectionFactory connectionFactory) {
		SimpleRabbitListenerContainerFactory f = new SimpleRabbitListenerContainerFactory();
		configurer.configure(f, connectionFactory);
		return f;
	}

	@Bean
	public Queue filaAutorRpcQueue() {
		return new Queue(filaAutorRpcQueue);
	}

	@Bean
	public Queue filaCategoriaRpcQueue() {
		return new Queue(filaCategoriaRpcQueue);
	}

	@Bean
	public Queue filaLivroRpcQueue() {
		return new Queue(filaLivroRpcQueue);
	}

	@Bean
	public Queue filaPaisRpcQueue() {
		return new Queue(filaPaisRpcQueue);
	}

	@Bean
	public Queue filaEstadoRpcQueue() {
		return new Queue(filaEstadoRpcQueue);
	}

	@Bean
	public Queue filaClienteRpcQueue() {
		return new Queue(filaClienteRpcQueue);
	}

	@Bean
	public DirectExchange exchange() {
		return new DirectExchange(directExchange);
	}

	@Bean
	Binding bindingFilaAutorRpcQueue() {
		return BindingBuilder.bind(filaAutorRpcQueue()).to(exchange()).with("filaAutorRpcQueue");
	}

	@Bean
	Binding bindingFilaCategoriaRpcQueue() {
		return BindingBuilder.bind(filaCategoriaRpcQueue()).to(exchange()).with("filaCategoriaRpcQueue");
	}

	@Bean
	Binding bindingFilaLivroRpcQueue() {
		return BindingBuilder.bind(filaLivroRpcQueue()).to(exchange()).with("filaLivroRpcQueue");
	}

	@Bean
	Binding bindingFilaPaisRpcQueue() {
		return BindingBuilder.bind(filaPaisRpcQueue()).to(exchange()).with("filaPaisRpcQueue");
	}

	@Bean
	Binding bindingFilaEstadoRpcQueue() {
		return BindingBuilder.bind(filaEstadoRpcQueue()).to(exchange()).with("filaEstadoRpcQueue");
	}

	@Bean
	Binding bindingFilaClienteRpcQueue() {
		return BindingBuilder.bind(filaClienteRpcQueue()).to(exchange()).with("filaClienteRpcQueue");
	}
}