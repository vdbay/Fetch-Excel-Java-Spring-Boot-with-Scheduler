package com.example.cimbexcel.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.cimbexcel.model.MapNonSwift;

public interface MapNonSwiftRepository extends JpaRepository<MapNonSwift, Integer> {
    boolean existsByCountryCodeAndCurrency(String countryCode, String currency);
}
