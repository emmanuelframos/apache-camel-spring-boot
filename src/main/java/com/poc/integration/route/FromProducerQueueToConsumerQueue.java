package com.poc.integration.route;

import com.poc.config.rabbit.RabbitConfig;
import org.apache.camel.builder.RouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class FromProducerQueueToConsumerQueue extends RouteBuilder {

    @Autowired
    @Qualifier(value="producerQueueConfig")
    private RabbitConfig rabbitProducerConfig;

    @Autowired
    @Qualifier(value="consumerQueueConfig")
    private RabbitConfig rabbitConsumerConfig;


    private static Logger logger = LoggerFactory.getLogger(FromJSONFileToProducerQueue.class);

    @Override
    public void configure() throws Exception {

        from(rabbitProducerConfig.build())

            .routeId("2-STEP_".concat(this.getClass().getSimpleName()))

            .routeDescription("This route get JSON from producer queue and send to consumer queue.")

            .setHeader("rabbitmq.DELIVERY_MODE", simple("2"))

            .to(rabbitConsumerConfig.build());
    }
}