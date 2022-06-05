package br.com.api.config;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class AmqpConfiguration {

	@Value("${amqp.username}")
	private String username;

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

	@Value("${thanos.direct.exchange.estudos.operacao}")
	private String directExchangeOperacao;

	@Bean
	public ConnectionFactory jmsConnectionFactory() {
		CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
		connectionFactory.setUsername(username);
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
		rabbitTemplate.setReplyTimeout(600000);
		return rabbitTemplate;
	}

	@Bean
	@Primary
	public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(
			SimpleRabbitListenerContainerFactoryConfigurer configurer, ConnectionFactory connectionFactory) {
		SimpleRabbitListenerContainerFactory f = new SimpleRabbitListenerContainerFactory();
		configurer.configure(f, connectionFactory);
		return f;
	}

	@Bean("tenantFactory")
	public SimpleRabbitListenerContainerFactory tenantRabbitListenerContainerFactory(
			SimpleRabbitListenerContainerFactoryConfigurer configurer, ConnectionFactory connectionFactory) {
		SimpleRabbitListenerContainerFactory f = new SimpleRabbitListenerContainerFactory();
		configurer.configure(f, connectionFactory);
		return f;
	}


	@Bean
	public DirectExchange exchangeOperacao() {
		return new DirectExchange(directExchangeOperacao);
	}

}