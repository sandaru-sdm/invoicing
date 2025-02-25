package com.sdm.invoicing.repository;

import com.sdm.invoicing.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findByMobile(String mobile);
    Optional<Customer> findByEmail(String mobile);
    List<Customer> findAllByNameContaining(String name);
}
