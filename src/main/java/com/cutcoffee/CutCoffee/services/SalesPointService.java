package com.cutcoffee.CutCoffee.services;

import com.cutcoffee.CutCoffee.models.SalesPoint;
import com.cutcoffee.CutCoffee.repositories.OrganizationRepo;
import com.cutcoffee.CutCoffee.repositories.PersonRepo;
import com.cutcoffee.CutCoffee.repositories.SalesPointRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class SalesPointService {

    private final SalesPointRepo salesPointRepo;

    private final OrganizationRepo organizationRepo;

    private final PersonRepo personRepo;

    public SalesPointService(SalesPointRepo salesPointRepo, OrganizationRepo organizationRepo, PersonRepo personRepo) {
        this.salesPointRepo = salesPointRepo;
        this.organizationRepo = organizationRepo;
        this.personRepo = personRepo;
    }

    public List<SalesPoint> findAll(){
        return salesPointRepo.findAll();
    }

    public SalesPoint findById(Integer idPoint){
        return salesPointRepo.findByIdPoint(idPoint);
    }

    public String addSalesPoint(SalesPoint salesPoint){
        if (organizationRepo.findByIdOrganization(salesPoint.getIdOrganization()) == null)
            return "Organization with such id doesn't exist";
        if (personRepo.findByIdPerson(salesPoint.getIdAdministrator()) == null)
            return "Person with such id doesn't exist";

        salesPointRepo.save(salesPoint);
        return "OK";
    }

    public String changeSalesPoint(SalesPoint salesPoint){
        SalesPoint savedSalesPoint = salesPointRepo.findByIdPoint(salesPoint.getIdPoint());
        if (savedSalesPoint == null)
            return "Sales point with such id doesn't exist";
        if (!salesPoint.getAddress().equals("-"))
            savedSalesPoint.setAddress(salesPoint.getAddress());
        if (salesPoint.getIdAdministrator() != -1){
            if (personRepo.findByIdPerson(salesPoint.getIdAdministrator()) == null)
                return "Person with such id doesn't exist";
            else
                savedSalesPoint.setIdAdministrator(salesPoint.getIdAdministrator());
        }

        if (salesPoint.getIdOrganization() != -1){
            if (organizationRepo.findByIdOrganization(salesPoint.getIdOrganization()) == null)
                return "Organization with such id doesn't exist";
            else
                savedSalesPoint.setIdOrganization(salesPoint.getIdPoint());
        }

        salesPointRepo.save(salesPoint);
        return "OK";
    }
}
