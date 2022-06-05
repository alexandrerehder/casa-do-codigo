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

	@Value("${thanos.mail.queue}")
	private String sendMailQueue;

	@Value("${thanos.fila.teste.queue}")
	private String filaTesteQueue;

	@Value("${thanos.fila.eventos.queue}")
	private String filaEventosQueue;

	@Value("${thanos.fila.eventos.rpc.queue}")
	private String filaEventosRPCQueue;

	@Value("${thanos.fila.perfis.rpc.queue}")
	private String filaPerfilRPCQueue;

	@Value("${thanos.fila.operacao.queue}")
	private String filaOperacaoQueue;

	@Value("${thanos.fila.teste.rpc.queue}")
	private String filaTesteRpcQueue;
	
	@Value("${thanos.fila.operacao.rpc.queue}")
	private String filaOperacaoRpcQueue;
	
	@Value("${thanos.fila.usuario.rpc.queue}")
	private String filaUsuarioRpcQueue;

	@Value("${thanos.direct.exchange.estudos.operacao}")
	private String directExchange;
	
	@Bean
	public ConnectionFactory jmsConnectionFactory() {
		CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
		connectionFactory.setUsername(userName);
		connectionFactory.setPassword(password);
		connectionFactory.setVirtualHost(virtualHost);
		connectionFactory.setPort(Integer.valueOf(port));
		connectionFactory.setUri(uri);
		connectionFactory.setHost(host);
		return connectionFactory;
	}

	@Bean
	public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
		RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
		return rabbitTemplate;
	}

	@Bean
	public SimpleRabbitListenerContainerFactory tenantRabbitListenerContainerFactory(
			SimpleRabbitListenerContainerFactoryConfigurer configurer, ConnectionFactory connectionFactory) {
		SimpleRabbitListenerContainerFactory f = new SimpleRabbitListenerContainerFactory();
		configurer.configure(f, connectionFactory);
		return f;
	}

	@Bean
	public Queue sendMailQueue() {
		return new Queue(sendMailQueue);
	}

	@Bean
	public Queue filaTesteQueue() {
		return new Queue(filaTesteQueue);
	}

	@Bean
	public Queue filaEventosQueue() {
		return new Queue(filaEventosQueue);
	}
	
	@Bean
	public Queue filaEventosRPCQueue() {
		return new Queue(filaEventosRPCQueue);
	}
	
	@Bean
	public Queue filaPerfilRPCQueue() {
		return new Queue(filaPerfilRPCQueue);
	}

	@Bean
	public Queue filaOperacaoQueue() {
		return new Queue(filaOperacaoQueue);
	}

	@Bean
	public Queue filaTesteRpcQueue() {
		return new Queue(filaTesteRpcQueue);
	}
	
	@Bean
	public Queue filaOperacaoRpcQueue() {
		return new Queue(filaOperacaoRpcQueue);
	}
	
	@Bean
	public Queue filaUsuarioRpcQueue() {
		return new Queue(filaUsuarioRpcQueue);
	}

	@Bean
	public DirectExchange exchange() {
		return new DirectExchange(directExchange);
	}

	@Bean
	Binding bindingEmail() {
		return BindingBuilder.bind(sendMailQueue()).to(exchange()).with("enviarEmail");
	}

	@Bean
	Binding bindingFilaTesteQueue() {
		return BindingBuilder.bind(filaTesteQueue()).to(exchange()).with("filaTesteQueue");
	}

	@Bean
	Binding bindingFilaOperacaoQueue() {
		return BindingBuilder.bind(filaOperacaoQueue()).to(exchange()).with("filaOperacaoQueue");
	}

	@Bean
	Binding bindingfilaEventosRPCQueue() {
		return BindingBuilder.bind(filaEventosRPCQueue()).to(exchange()).with("filaEventosRPCQueue");
	}

	@Bean
	Binding bindingFilaEventosQueue() {
		return BindingBuilder.bind(filaEventosQueue()).to(exchange()).with("filaEventosQueue");
	}


	@Bean
	Binding bindingFilaTesteRpcQueue() {
		return BindingBuilder.bind(filaTesteRpcQueue()).to(exchange()).with("filaTesteRpcQueue");
	}
	
	@Bean
	Binding bindingfilaPerfilRPCQueue() {
		return BindingBuilder.bind(filaPerfilRPCQueue()).to(exchange()).with("filaPerfilRPCQueue");
	}

  @Bean
	Binding bindingFilaOperacaoRpcQueue() {
		return BindingBuilder.bind(filaOperacaoRpcQueue()).to(exchange()).with("filaOperacaoRpcQueue");
	}
	
	@Bean
	Binding bindingFilaUsuarioRpcQueue() {
		return BindingBuilder.bind(filaUsuarioRpcQueue()).to(exchange()).with("filaUsuarioRpcQueue");
	}	

}