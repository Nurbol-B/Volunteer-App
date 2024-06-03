package com.example.FinalProject.repository;

import com.example.FinalProject.entity.CodeConfirm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CodeConfirmRepository extends JpaRepository<CodeConfirm, Long> {
    CodeConfirm findByCode(Long code);
}
