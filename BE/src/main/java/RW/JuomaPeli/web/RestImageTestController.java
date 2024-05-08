package RW.JuomaPeli.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestImageTestController {

	@GetMapping("/api/test")
	public ResponseEntity<String> testi() {
		return new ResponseEntity<>("Testipestilesti", HttpStatus.OK);
	}
}
