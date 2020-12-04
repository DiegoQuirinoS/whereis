package br.com.diego.whereis;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.FailureCallback;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.SuccessCallback;

@Component
public class LocationProducer {
	
	private static final Logger LOG = LoggerFactory.getLogger(LocationProducer.class);

	@Value("${location.topic}")
    private String locationTopic;
 
    private final KafkaTemplate kafkaTemplate;
 
    public LocationProducer(final KafkaTemplate kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }
 
    public void send(final String location) {
        final String mensageKey = UUID.randomUUID().toString();
        ListenableFuture listenableFuture = kafkaTemplate.send(locationTopic, mensageKey, location);
        listenableFuture.addCallback(result -> LOG.info("CALLBACK :" + result.toString()), (ex) ->  ex.printStackTrace());
    }
    
}
