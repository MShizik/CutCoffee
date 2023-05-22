package com.cutcoffee.CutCoffee.repositories;

import com.cutcoffee.CutCoffee.models.Service;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceRepo extends JpaRepository<Service, Integer> {

}
