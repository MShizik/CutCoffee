package com.cutcoffee.CutCoffee.services;


import com.cutcoffee.CutCoffee.models.Barber;
import com.cutcoffee.CutCoffee.repositories.BarberRepo;
import com.cutcoffee.CutCoffee.repositories.PersonRepo;
import com.cutcoffee.CutCoffee.repositories.SalesPointRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class BarberService {

    private final BarberRepo barberRepo;

    private final PersonRepo personRepo;

    public BarberService(BarberRepo barberRepo, PersonRepo personRepo) {
        this.barberRepo = barberRepo;
        this.personRepo = personRepo;
    }

    public List<Barber> findAll(){
        return barberRepo.findAll();
    }

    public Barber findById(Integer idBarber){
        return barberRepo.findByIdBarber(idBarber);
    }

    public String addNewBarber(String taxId, Integer idPerson){
        if (personRepo.findByIdPerson(idPerson) == null)
            return "Person doesn't exist";
        Barber barber = new Barber();
        barber.setTaxId(taxId);
        barber.setIdPerson(idPerson);
        barberRepo.save(barber);
        return "OK";
    }

    public String changeBarber(Integer idBarber, String taxId, Integer idPerson){
        Barber savedBarber = barberRepo.findByIdBarber(idBarber);
        if (savedBarber == null)
            return "Barber with such id doesn't exist";
        if (!taxId.equals("-"))
            savedBarber.setTaxId(taxId);
        if (idPerson != -1){
            if (personRepo.findByIdPerson(idPerson) != null)
                savedBarber.setIdPerson(idPerson);
            else
                return "Person with such id doesn't exist";
        }
        barberRepo.save(savedBarber);
        return "OK";
    }
}
