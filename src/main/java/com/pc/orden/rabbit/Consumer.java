package com.pc.orden.rabbit;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class Consumer {

    @RabbitListener(queues = "rabbitQueue")
    public void receive(String message)  {
        System.out.println("Message " + message + "  " + LocalDateTime.now());
    }
}
