package com.sdm.invoicing.repository;

import com.sdm.invoicing.entity.Detail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DetailRepository extends JpaRepository<Detail, Long> {

    Optional<Detail> findByName(String name);

    List<Detail> findAllByNameContaining(String name);
}
