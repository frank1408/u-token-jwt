
package com.rodriguez.security;

import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.rodriguez.entity.Person;
import com.rodriguez.repository.PersonRepository;

@Service
public class JwtUserDetailsService implements UserDetailsService {
	
	@Autowired
	PersonRepository personRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Person personFromDatabase =
			personRepository.findByUserName(
				username
			);
		
		if( personFromDatabase == null ) {
			throw new UsernameNotFoundException("UsernameNotFound");
		}
		if(personFromDatabase.getUserName().equals(username)) {
			return new User(
				personFromDatabase.getUserName(),
				personFromDatabase.getPassword(),
				new ArrayList<>()
			);
		}
		throw new UsernameNotFoundException("Usuario o password invalido2");

	}

}
