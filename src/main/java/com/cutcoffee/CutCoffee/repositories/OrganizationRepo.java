package com.cutcoffee.CutCoffee.repositories;

import com.cutcoffee.CutCoffee.models.Organization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrganizationRepo extends JpaRepository<Organization, Integer> {

    public Organization findByIdOrganization(Integer idOrganization);

}
