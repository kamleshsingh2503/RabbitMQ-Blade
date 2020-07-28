package com.rabbitmq.dmo.demorabbitmq.config;
import com.blade.ioc.annotation.Bean;
import com.blade.ioc.annotation.Inject;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;

@Bean
public class ConnectionFactoryConf {
	
	@Inject
	PropertyFileReaderConfig propConfig;
	
	public ConnectionFactory connectionFactory() {
		CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
		connectionFactory.setAddresses(propConfig.getConf().get(1));
		connectionFactory.setUsername(propConfig.getConf().get(3));
		connectionFactory.setPassword(propConfig.getConf().get(4));
		return connectionFactory;
	}
}