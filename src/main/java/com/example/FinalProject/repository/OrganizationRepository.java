package com.example.FinalProject.repository;

import com.example.FinalProject.entity.Organization;
import com.example.FinalProject.entity.SocialTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrganizationRepository extends JpaRepository <Organization, Long > {
    Optional<Organization> findByIdAndRemoveDateIsNull(Long id);
    List<Organization> findAllByRemoveDateIsNull();
}
