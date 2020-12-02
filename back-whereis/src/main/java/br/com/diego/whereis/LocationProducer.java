package br.com.diego.whereis;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

@Component
public class LocationProducer {

	@Value("${location.topic}")
    private String locationTopic;
 
    private final KafkaTemplate kafkaTemplate;
 
    public LocationProducer(final KafkaTemplate kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }
 
    public void send(final @RequestBody String location) {
        final String mensageKey = UUID.randomUUID().toString();
        kafkaTemplate.send(locationTopic, mensageKey, location);
    }
}
