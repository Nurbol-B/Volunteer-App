package com.example.FinalProject.repository;

import com.example.FinalProject.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {
//    @Query("SELECT u.balance FROM User u WHERE u.username = :username")
//    BigDecimal findBalanceByUserName(String userName);
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);

}
