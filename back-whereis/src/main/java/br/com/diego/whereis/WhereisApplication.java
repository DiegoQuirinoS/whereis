package br.com.diego.whereis;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@SpringBootApplication
@RestController
@RequestMapping("sample")
@EnableWebSocketMessageBroker
public class WhereisApplication implements WebSocketMessageBrokerConfigurer{

	private static final Logger LOG = LoggerFactory.getLogger(WhereisApplication.class);
	
	@Autowired
	private LocationProducer locationProducer;
	
	@Autowired
	private SimpMessagingTemplate webSocket;
	
	public static void main(String[] args) {
		SpringApplication.run(WhereisApplication.class, args);
	}

	@GetMapping("/")
	public ResponseEntity<String> get(){
		return new ResponseEntity<String>("hello diego2", HttpStatus.OK);
	}
	
	@PostMapping("/")
	public ResponseEntity<Void> post(@RequestBody String location){
		try {
			locationProducer.send(location);
			return ResponseEntity.ok().build();
		}catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().build();
		}
	}
	
	@Override
	public void configureMessageBroker(MessageBrokerRegistry config) {
		config.setApplicationDestinationPrefixes("/app");
		config.enableSimpleBroker("/topic");
	}

	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		registry.addEndpoint("/websocket").setAllowedOrigins("*");
	}
	
	@KafkaListener(topics = "${location.topic}", groupId = "${spring.kafka.consumer.group-id}")
	public void processMessage(ConsumerRecord<String, Model> cr, @Payload Model content) {
		LOG.info("Received content from Kafka: {}", content);

		this.webSocket.convertAndSend("/topic/messages", content.getMessage());
	}
	
}

class Model {
	
	private String message;

	public Model() {

	}

	public Model(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return String.format("Model [message=%s]", message);
	}
	

}
