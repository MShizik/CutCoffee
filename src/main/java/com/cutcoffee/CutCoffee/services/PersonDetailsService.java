package com.cutcoffee.CutCoffee.services;

import com.cutcoffee.CutCoffee.models.Person;
import com.cutcoffee.CutCoffee.repositories.PersonRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@Slf4j
public class PersonDetailsService implements UserDetailsService{

    @Autowired
    private final PersonRepo personRepo;

    public PersonDetailsService(PersonRepo personRepo) {
        this.personRepo = personRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String contact_email) throws UsernameNotFoundException {
        Person user = personRepo.findByContactEmail(contact_email);

        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                Collections.singleton(new SimpleGrantedAuthority("ROLE_" + user.getRole())));
    }

}
