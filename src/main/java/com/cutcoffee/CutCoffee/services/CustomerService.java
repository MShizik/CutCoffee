package com.cutcoffee.CutCoffee.services;

import com.cutcoffee.CutCoffee.models.Customer;
import com.cutcoffee.CutCoffee.repositories.CustomerRepo;
import com.cutcoffee.CutCoffee.repositories.PersonRepo;
import com.cutcoffee.CutCoffee.repositories.SalesPointRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class CustomerService {

    private final CustomerRepo customerRepo;

    private final PersonRepo personRepo;

    public CustomerService(CustomerRepo customerRepo, PersonRepo personRepo) {
        this.customerRepo = customerRepo;
        this.personRepo = personRepo;
    }

    public List<Customer> findAll(){
        return customerRepo.findAll();
    }

    public Customer findById(Integer idCustomer){
        return customerRepo.findByIdCustomer(idCustomer);
    }

    public String addNewCustomer(String features, Integer idPerson){
        if (personRepo.findByIdPerson(idPerson) == null)
            return "Person doesn't exist";
        Customer customer = new Customer();
        customer.setFeatures(features);
        customer.setIdPerson(idPerson);
        customerRepo.save(customer);
        return "OK";
    }

    public String changeCustomer(Integer idCustomer, String features, Integer idPerson){
        Customer savedCustomer = customerRepo.findByIdCustomer(idCustomer);
        if (savedCustomer == null)
            return "Customer with such id doesn't exist";
        if (!features.equals("-"))
            savedCustomer.setFeatures(features);
        if (idPerson != -1){
            if (personRepo.findByIdPerson(idPerson) != null)
                savedCustomer.setIdPerson(idPerson);
            else
                return "Person with such id doesn't exist";
        }
        customerRepo.save(savedCustomer);
        return "OK";
    }
}