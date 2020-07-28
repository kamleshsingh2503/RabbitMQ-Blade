package com.rabbitmq.dmo.demorabbitmq;

import com.blade.ioc.annotation.Bean;
import com.blade.ioc.annotation.Inject;
import com.rabbitmq.dmo.demorabbitmq.config.ConnectionFactoryConf;
import com.rabbitmq.dmo.demorabbitmq.config.PropertyFileReaderConfig;
import com.rabbitmq.dmo.demorabbitmq.model.Employee;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;

@Bean
public class RabbitMQSender {

	RabbitTemplate rabbitTemplate = new RabbitTemplate();

	@Inject
	PropertyFileReaderConfig pconfig;

	@Inject
	private ConnectionFactoryConf conf;

	public void send(Employee employee, String queryType) {

		rabbitTemplate.setConnectionFactory(conf.connectionFactory());
		rabbitTemplate.setExchange(pconfig.getExchangeMap().get(2));
		rabbitTemplate.setRoutingKey(pconfig.getExchangeMap().get(4));
		rabbitTemplate.setMessageConverter(jsonMessageConverter());
		rabbitTemplate.convertAndSend(queryType + " " + employee);
		System.out.println("Send msg = " + employee);

	}

	public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

	Binding binding(FanoutExchange directExchange, Queue queue){
		return BindingBuilder.bind(queue).to(directExchange);
	}

}
