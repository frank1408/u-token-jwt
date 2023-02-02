
package com.rodriguez.ws;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin()
public class HelloWorldController {

	@GetMapping("/api/auth/hola")
	public String hello() throws Exception {
		return "holaMundo";
	}

}
