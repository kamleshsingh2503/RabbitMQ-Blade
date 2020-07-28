package com.rabbitmq.dmo.demorabbitmq.config;

import javax.management.Notification;

import com.blade.ioc.annotation.Bean;

import org.springframework.amqp.rabbit.core.RabbitTemplate;

@Bean
public class AMQPProducer {

    RabbitTemplate rabbitTemplate;

    public void sendMessage(Notification msg){
        System.out.println("Send msg = " + msg.toString());
        rabbitTemplate.convertAndSend("rmq.rube.exchange", "rube.key", msg);
    }
}