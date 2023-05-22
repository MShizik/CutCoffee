package com.cutcoffee.CutCoffee.repositories;

import com.cutcoffee.CutCoffee.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepo extends JpaRepository<Customer, Integer> {

    Customer findByIdCustomer(Integer idCustomer);

}
