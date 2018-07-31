package com.feazesa.kafka.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.feazesa.kafka.producer.entity.Event;
import com.feazesa.kafka.producer.entity.Payload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Component
public class EventProducer {

    private static final Logger LOGGER = LoggerFactory.getLogger(EventProducer.class);
    private static final Random rand = new Random();

    @Value("${kafka.topic}")
    private String topic;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    //send message (event) at a fixed rate of 5 seconds
    @Scheduled(fixedRate = 5000)
    public void run() throws JsonProcessingException {
        String event = objectMapper.writeValueAsString(generateEvent());
        this.send(topic, event);
    }

    public void send(String topic, String event) {
        try {
            kafkaTemplate.send(topic, event);
            LOGGER.info("sending event='{}' to topic='{}'", event, topic);
        } catch (Exception e) {
            LOGGER.error("not able to sent event='{}' to topic='{}'", event, topic);
        }

    }


    //generate message
    public static Event generateEvent() {
        //randomize event name
        List<String> eventNames = Arrays.asList("EVENT_CREATED", "EVENT_SUBMITTED", "EVENT_UPDATED", "EVENT_ERROR");
        String name = eventNames.get(rand.nextInt(eventNames.size()));
        String version = "1.0";
        LocalDateTime timestamp = LocalDateTime.now();
        String source = "unloader-service";

        Payload payload = new Payload(8L , "HELLO TEAM8");

        //created event object for test
        Event<Payload> event = new Event<>(name, UUID.randomUUID(), version, timestamp, source, payload);

        return event;

    }
}