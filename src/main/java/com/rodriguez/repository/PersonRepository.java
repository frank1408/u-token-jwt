
package com.rodriguez.repository;

import java.io.Serializable;
import org.springframework.data.jpa.repository.JpaRepository;
import com.rodriguez.entity.Person;

public interface PersonRepository extends JpaRepository<Person, Serializable> {
	public Person findByUserName( String userName );
}
