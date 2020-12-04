package br.com.diego.whereis.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.diego.whereis.LocationProducer;

@RestController
@RequestMapping("api/kafka")
public class KafkaResource {
	
	@Autowired
	private LocationProducer locationProducer;

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
}
