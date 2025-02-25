package com.sdm.invoicing.repository;

import com.sdm.invoicing.entity.User;
import com.sdm.invoicing.enums.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByActivationCode(String activationCode);
    boolean existsByEmail(String email);
    User findByRole(UserRole userRole);
}
