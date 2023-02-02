
package com.rodriguez.wsi;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.rodriguez.entity.Person;

public interface IPersonWebService {
	@PostMapping("/api/noauth/login")
	public ResponseEntity<?> createAuthenticationToken(@RequestBody Person authenticationRequest) throws Exception;
}
