package com.example.FinalProject.repository;

import com.example.FinalProject.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {
//    @Query("SELECT u.balance FROM User u WHERE u.username = :username")
//    BigDecimal findBalanceByUserName(String userName);
    Optional<User> findByUsernameAndRemoveDateIsNull(String username);
    Optional<User> findByEmailAndRemoveDateIsNull(String email);
    Optional<User> findByIdAndRemoveDateIsNull(Long id);
    List<User> findAllByRemoveDateIsNull();

    List<User> findByBalanceGreaterThan(BigDecimal amount);
    Optional<User> findByUsernameAndId(String username, Long id);
}
