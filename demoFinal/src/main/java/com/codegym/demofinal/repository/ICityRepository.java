package com.codegym.demofinal.repository;

import com.codegym.demofinal.model.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ICityRepository extends JpaRepository<City, Long> {
    Iterable<City> findAllByNameContaining(String name);
}
