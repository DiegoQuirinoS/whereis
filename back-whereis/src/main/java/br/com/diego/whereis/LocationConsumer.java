package br.com.diego.whereis;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import br.com.diego.whereis.model.ModelMessage;

@Component
public class LocationConsumer {

	private static final Logger LOG = LoggerFactory.getLogger(LocationConsumer.class);
	
	@Autowired
	private SimpMessagingTemplate webSocket;
	
	@KafkaListener(topics = "${location.topic}", groupId = "CONSUMER-GROUP-1")
	public void processMessage(ConsumerRecord<String, ModelMessage> cr, @Payload ModelMessage content) {
		LOG.info("Received content from Kafka CONSUMER 1: {}", content);

		this.webSocket.convertAndSend("/topic/messages", content.getMessage());
	}
	
	@KafkaListener(topics = "${location.topic}", groupId = "CONSUMER-GROUP-2")
    public void consumer(ConsumerRecord<String, ModelMessage> cr, @Payload ModelMessage content) {
		LOG.info("Received content from Kafka CONSUMER 2: {}", content);
    }
	
}
