package br.com.diego.whereis.view;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/geo")
public class GeoResource {
	
	@GetMapping("/")
	public ResponseEntity<String> get(){
		return new ResponseEntity<String>("hello diego3", HttpStatus.OK);
	}
}
