package com.sdm.invoicing.repository;

import com.sdm.invoicing.entity.PaymentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentTypeRepository extends JpaRepository<PaymentType, Long> {
    Optional<PaymentType> findByName(String name);

    List<PaymentType> findAllByNameContaining(String name);
}
