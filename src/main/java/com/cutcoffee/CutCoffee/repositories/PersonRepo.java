package com.cutcoffee.CutCoffee.repositories;

import com.cutcoffee.CutCoffee.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepo extends JpaRepository<Person, Integer> {

    Person findByContactEmail(String contact_email);

    Person findByIdPerson(Integer idPerson);

    Person findByUsername(String username);

    Person findByContactPhone(String contact_phone);
}
