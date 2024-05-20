package com.example.FinalProject.repository;

import com.example.FinalProject.entity.Organization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrganizationRepository extends JpaRepository <Organization, Long > {
}
