package com.poc.integration.route;

import com.poc.config.rabbit.RabbitConfig;
import org.apache.camel.builder.RouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class FromJSONFileToProducerQueue extends RouteBuilder {

    @Autowired
    @Qualifier(value="producerQueueConfig")
    private RabbitConfig rabbitProducerConfig;

    private static Logger logger = LoggerFactory.getLogger(FromJSONFileToProducerQueue.class);

    @Override
    public void configure() throws Exception {

        from("file://src/main/resources/files?noop=true")

            .routeId("1-STEP_".concat(this.getClass().getSimpleName()))

            .routeDescription("This route reads a file and send to producer queue.")

            .convertBodyTo(String.class)

            .log("${body}")

            .setHeader("rabbitmq.DELIVERY_MODE", simple("2"))

            .to(rabbitProducerConfig.build());
    }
}