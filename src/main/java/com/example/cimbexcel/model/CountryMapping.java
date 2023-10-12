package com.example.cimbexcel.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name="COUNTRY_MAPPING")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class CountryMapping {
    
    @Column(name = "COUNTRY_MAPPING_ID")
    private String id;
    @Column(name = "BANK_NAME")
    private String bankName;
    @Column(name = "CORRIDOR_CODE")
    private String corridorCode;
    @Column(name = "COUNTRY_NAME")
    private String countryName;
    @Column(name = "CURRENCY")
    private String currency;
    
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int identity;
}

