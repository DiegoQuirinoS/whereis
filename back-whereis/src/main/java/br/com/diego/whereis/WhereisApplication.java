package br.com.diego.whereis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@SpringBootApplication
public class WhereisApplication implements WebSocketMessageBrokerConfigurer{
	
	public static void main(String[] args) {
		SpringApplication.run(WhereisApplication.class, args);
	}

	
}
