package com.rabbitmq.dmo.demorabbitmq.config;

import com.blade.ioc.annotation.Bean;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.MessageListener;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;


public class ConnectionFactoryConfig {

    @Bean
    DirectExchange rubeExchange() {
        return new DirectExchange("rmq.rube.exchange", true, false);
    }

    @Bean
    public Queue rubeQueue() {
        return new Queue("rmq.rube.queue");
    }

    @Bean
    Binding rubeExchangeBinding(DirectExchange rubeExchange, Queue rubeQueue) {
        return BindingBuilder.bind(rubeQueue).to(rubeExchange).with("rube.key");
    }

    @Bean
    public MappingJackson2MessageConverter consumerJackson2MessageConverter() {
        return new MappingJackson2MessageConverter();
    }

    @Bean
    public AmqpTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    MessageListener listenerAdapter(RabbitHandler listener) {
       return new MessageListenerAdapter(listener, "listen");
    }   
        
}