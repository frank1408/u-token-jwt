
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
			System.err.println("Usuario o password invalido");
			System.err.println("");
			System.err.println("! >0");
			System.err.println("");
			System.err.println("Usuario o password invalido");
			throw new Exception();
		}
		
		Person pperson = personRepository.findByUserName(userName);
		if( pperson == null ) {
			System.err.println("Usuario o password invalido");
			System.err.println("");
			System.err.println("pperson is null");
			System.err.println("");
			System.err.println("Usuario o password invalido");
			throw new Exception();
		}
		if( pperson.getPassword().equals( password )){
			//
			// usuario y password coinciden a la perfeccion
			//
			try {
				authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(
							userName, password
				));
			} catch ( Exception e) {
				
				System.err.println("authenticationManager.authenticate");
				System.err.println("");
				System.err.println("\t\tUsernamePasswordAuthenticationToken");
				System.err.println("");
				System.err.println("authenticationManager.authenticate");
				throw new Exception();
			}
			//
			// usuario y password coinciden a la perfeccion
			//
		}else {
			 System.err.println("Usuario o password invalido");
			 System.err.println("");
			 System.err.println("userInput");
			 System.err.println("user:password " +userName+":"+ password);
			 System.err.println("");
			 System.err.println("userDb");
			 System.err.println("user:password "+pperson.getUserName()+":"+ pperson.getPassword());
			throw new Exception("Usuario o password invalido");
		}
	}
}

