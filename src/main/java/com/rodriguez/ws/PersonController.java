
package com.rodriguez.ws;

import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.rodriguez.entity.Person;
import com.rodriguez.entity.TokenJwt;
import com.rodriguez.repository.PersonRepository;
import com.rodriguez.security.JwtTokenUtil;
import com.rodriguez.wsi.IPersonWebService;

@RestController
@CrossOrigin
public class PersonController implements IPersonWebService {
	
	@Autowired
	PersonRepository personRepository;
	
	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@RequestMapping(value="/api/noauth/login", method = RequestMethod.POST )
	public ResponseEntity<?> createAuthenticationToken(@RequestBody Person person) throws Exception {

		
		try {
		authenticate(
				person.getUserName(),
				person.getPassword()
		);
		}catch(Exception e) {
			return ResponseEntity.ok(new String("Usuario o password invalido"));
		}
		

		final UserDetails userDetails = userDetailsService
				.loadUserByUsername(person.getUserName());

		final String token = jwtTokenUtil.generateToken(userDetails);

		return ResponseEntity.ok(new TokenJwt(token));
	}

	
	
	private void authenticate(String userName, String password) throws Exception {
		Objects.requireNonNull(userName);
		Objects.requireNonNull(password);
		
		if(
			!(userName.length() > 0)
			|| !(password.length() > 0)
			|| userName == null
			|| password == null
		) {
			throw new Exception();
		}
		
		Person pperson = personRepository.findByUserName(userName);
		if( pperson == null ) {
			throw new Exception();
		}
		if( pperson.getPassword().equals( password )){
			try {
				authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(
							userName, password
				));
			} catch ( Exception e) {
				throw new Exception();
			}
		}else {
			throw new Exception("Usuario o password invalido");
		}
	}
}

