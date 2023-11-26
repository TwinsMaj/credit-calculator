package com.proxima.creditcalculator.LoanAPI;

import java.util.HashMap;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.proxima.creditcalculator.person.Person;

@Repository
public class LoanRepository {
	private HashMap<String, Person> personDB = new HashMap<>();

	public LoanRepository() {
		personDB.put("49002010965", new Person("debt", 0));
		personDB.put("49002010976", new Person("segment", 100));
		personDB.put("49002010987", new Person("segment", 300));
		personDB.put("49002010998", new Person("segment", 1000));
	}

	public Optional<Person> findPersonById(String id) {
		Person person = personDB.get(id);
		Optional<Person> opt = Optional.ofNullable(person);
		return opt;
	}
}
