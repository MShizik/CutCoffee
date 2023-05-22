package com.cutcoffee.CutCoffee.repositories;

import com.cutcoffee.CutCoffee.models.SalesPoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SalesPointRepo extends JpaRepository<SalesPoint, Integer> {

    SalesPoint findByIdPoint(Integer id_point);

}
