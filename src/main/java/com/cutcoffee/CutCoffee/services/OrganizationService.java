package com.cutcoffee.CutCoffee.services;


import com.cutcoffee.CutCoffee.models.Organization;
import com.cutcoffee.CutCoffee.repositories.OrganizationRepo;
import com.cutcoffee.CutCoffee.repositories.PersonRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class OrganizationService {
    private final OrganizationRepo organizationRepo;
    private final PersonRepo personRepo;

    @Autowired
    public OrganizationService(OrganizationRepo organizationRepo, PersonRepo personRepo) {
        this.organizationRepo = organizationRepo;
        this.personRepo = personRepo;
    }

    public List<Organization> findAll(){
        return organizationRepo.findAll();
    }

    public Organization findById(Integer idOrganization){
        return organizationRepo.findByIdOrganization(idOrganization);
    }

    public String addOrganization(Organization organization){
        if (personRepo.findByIdPerson(organization.getCeo()) != null)
            return "CEO person doesn't exist";
        organizationRepo.save(organization);
        return "OK";
    }

    public String changeOrganization(Organization organization){
        Organization savedOrganization = organizationRepo.findByIdOrganization(organization.getIdOrganization());
        if (savedOrganization == null)
            return "Organization with such id doesn't exist";

        if (!organization.getUrAddress().equals("-"))
            savedOrganization.setUrAddress(organization.getUrAddress());
        if (!organization.getFisAddress().equals("-"))
            savedOrganization.setFisAddress(organization.getFisAddress());
        if (!organization.getTaxId().equals("-"))
            savedOrganization.setTaxId(organization.getTaxId());
        if (organization.getCeo() != -1){
            if (personRepo.findByIdPerson(organization.getCeo()) == null)
                return "CEO with such person id doesn't exist";
            else
                savedOrganization.setCeo(organization.getCeo());
        }
        if (!organization.getOrgEmail().equals("-"))
            savedOrganization.setOrgEmail(organization.getOrgEmail());
        if (!organization.getOrgPhone().equals("-"))
            savedOrganization.setOrgPhone(organization.getOrgEmail());
        organizationRepo.save(organization);
        return "OK";
    }
}
