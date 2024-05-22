package com.example.FinalProject.repository;

import com.example.FinalProject.entity.User2;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository2 extends JpaRepository<User2, Long> {
    User2 findByUserEmailIgnoreCase(String emailId);

    Boolean existsByUserEmail(String email);
}
