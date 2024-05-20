package com.example.FinalProject.repository;

import com.example.FinalProject.entity.SocialTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SocialTaskRepository extends JpaRepository<SocialTask, Long> {
    List<SocialTask> findByOrganizationId(Long organizationId);


}
