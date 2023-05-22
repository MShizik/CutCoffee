package com.cutcoffee.CutCoffee.services;

import com.cutcoffee.CutCoffee.models.Person;
import com.cutcoffee.CutCoffee.repositories.PersonRepo;
import com.cutcoffee.CutCoffee.repositories.SalesPointRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.AbstractMap.*;

import java.util.List;

@Service
@Slf4j
public class PersonService {

    private final PersonRepo personRepo;

    private final SalesPointRepo salesPointRepo;

    @Autowired
    public PersonService(PersonRepo personRepo, SalesPointRepo salesPointRepo){
        this.personRepo = personRepo;
        this.salesPointRepo = salesPointRepo;
    }

    public List<Person> findAll(){
        return personRepo.findAll();
    }

    public Person findPersonByContactEmail(String contact_email){
        return personRepo.findByContactEmail(contact_email);
    }

    public Person findPersonByContactPhone(String contact_phone){
        return personRepo.findByContactPhone(contact_phone);
    }

    public Person findPersonByIdPerson(Integer id_person){
        return personRepo.findByIdPerson(id_person);
    }

    public Person findPersonByUsername(String username){
        return personRepo.findByUsername(username);
    }

    public String changePerson(Person person){
        Person savedPerson = findPersonByIdPerson(person.getIdPerson());

        if (savedPerson == null)
            return "User doesn't exist";

        if (!person.getUsername().equals("-")){
            if (findPersonByUsername(person.getUsername()) == null)
                savedPerson.setUsername(person.getUsername());
            else
                return "User with such username already exist";
        }

        if(!person.getPassword().equals("-")){
            savedPerson.setPassword(person.getPassword());
        }

        if(!person.getRole().equals("-")){
            savedPerson.setRole(person.getRole());
        }

        if(!person.getLastName().equals("-")){
            savedPerson.setLastName(person.getLastName());
        }

        if(!person.getFirstName().equals("-")){
            savedPerson.setFirstName(person.getFirstName());
        }

        if(!person.getMiddleName().equals("-")){
            savedPerson.setMiddleName(person.getMiddleName());
        }

        if(!person.getContactEmail().equals("-")){
            if (findPersonByContactEmail(person.getContactEmail()) == null)
                savedPerson.setContactEmail(person.getContactEmail());
            else
                return "Person with such email already exist";
        }

        if(!person.getContactPhone().equals("-")) {
            if (findPersonByContactPhone(person.getContactPhone()) == null)
                savedPerson.setContactPhone(person.getContactPhone());
            else
                return "Person with such phone already exist";
        }

        if(!person.getBirthDate().equals("-"))
            savedPerson.setBirthDate(person.getBirthDate());

        if(!person.getGender().equals("-"))
            savedPerson.setGender(person.getGender());

        if(person.getIdPoint() != -1){
            if (salesPointRepo.findByIdPoint(person.getIdPoint()) != null)
                savedPerson.setIdPoint(person.getIdPoint());
            else
                return "Sales point with such id doesn't exist";
        }
        personRepo.save(savedPerson);
        return "OK";
    }

    public String addPerson(Person person){
        SimpleEntry<Boolean, String> checkIfUSerExist = checkIfUserAlreadyExist(person);
        if (!checkIfUSerExist.getKey()){
           personRepo.save(person);
        }
        return checkIfUSerExist.getValue();
    }

    public SimpleEntry<Boolean, String> checkIfUserAlreadyExist(Person person){
        Boolean check = false;
        String message = "OK";

        if (personRepo.findByContactEmail(person.getContactEmail()) != null){
            check = true;
            message = "User with such email already exist";
        }
        else if (personRepo.findByContactEmail(person.getUsername()) != null)
        {
            check = true;
            message = "User with such username already exist";
        }
        else if (personRepo.findByContactPhone(person.getContactPhone()) != null){
            check = true;
            message = "User with such contact phone exist";
        }
        return new SimpleEntry<>(check, message);
    }

}
