package com.example.cimbexcel.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.cimbexcel.model.MasterOverseasBank;

public interface MasterOverseasBankRepository extends JpaRepository<MasterOverseasBank, Integer> {
    boolean existsBySpeedsendCode(String speedsendCode);
}
