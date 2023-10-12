package com.example.cimbexcel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.cimbexcel.model.CountryMapping;

@Repository
public interface CountryMappingRepository extends JpaRepository<CountryMapping, Integer> {

}
