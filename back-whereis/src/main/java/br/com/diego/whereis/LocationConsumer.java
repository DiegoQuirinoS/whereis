package br.com.diego.whereis;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class LocationConsumer {

	@KafkaListener(topics = "${location.topic}", groupId = "${spring.kafka.consumer.group-id}")
    public void consumer(String order) {
	  System.out.println("Order: " + order);
    }
}
